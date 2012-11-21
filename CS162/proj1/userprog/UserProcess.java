package nachos.userprog;

import java.io.EOFException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nachos.machine.Coff;
import nachos.machine.CoffSection;
import nachos.machine.Lib;
import nachos.machine.Machine;
import nachos.machine.OpenFile;
import nachos.machine.Processor;
import nachos.machine.TranslationEntry;
import nachos.threads.KThread;
import nachos.threads.Lock;
import nachos.threads.ThreadedKernel;

/**
 * Encapsulates the state of a user process that is not contained in its
 * user thread (or threads). This includes its address translation state, a
 * file table, and information about the program being executed.
 *
 * <p>
 * This class is extended by other classes to support additional functionality
 * (such as additional syscalls).
 *
 * @see	nachos.vm.VMProcess
 * @see	nachos.network.NetProcess
 */
public class UserProcess {
  private static final int STRING_READ = 256;
  private static final int MAX_DESCRIPTORS = 16;

  /**
   * Allocate a new process.
   */
  public UserProcess() {
    // ntdo
    // cread PID, not in created before
    Random rand = new Random();
    int pid;
    while (true) {
      pid = rand.nextInt(9999);
      if (!pids.keySet().contains(pid)) {
        pids.put(pid, this);
        this.pid = pid;
        break;
      }
    }
    if (root == null)
      root = this;

    //	opennedFile = new Hashtable<Integer, OpenFile>();
    //	for (int i = 0; i < MAX_DESCRIPTORS; i++) {
    //		System.out.println(i);
    //		opennedFile.put(i, null);
    //	}
    OpenFile stdin = UserKernel.console.openForReading();
    OpenFile stdout = UserKernel.console.openForWriting();
    opennedFile.put(0, stdin);
    opennedFile.put(1, stdout);

    System.out.println("New Process pid = " + pid);

    int numPhysPages = Machine.processor().getNumPhysPages();
    pageTable = new TranslationEntry[numPhysPages];
//    for (int i=0; i<numPhysPages; i++)
//      pageTable[i] = new TranslationEntry(i,i, true,false,false,false);
  }

  /**
   * Allocate and return a new process of the correct class. The class name
   * is specified by the <tt>nachos.conf</tt> key
   * <tt>Kernel.processClassName</tt>.
   *
   * @return	a new process of the correct class.
   */
  public static UserProcess newUserProcess() {
    return (UserProcess)Lib.constructObject(Machine.getProcessClassName());
  }

  /**
   * Execute the specified program with the specified arguments. Attempts to
   * load the program, and then forks a thread to run it.
   *
   * @param	name	the name of the file containing the executable.
   * @param	args	the arguments to pass to the executable.
   * @return	<tt>true</tt> if the program was successfully executed.
   */
  public boolean execute(String name, String[] args) {
    if (!load(name, args))
      return false;

    new UThread(this).setName(name).fork();

    return true;
  }

  /**
   * Save the state of this process in preparation for a context switch.
   * Called by <tt>UThread.saveState()</tt>.
   */
  public void saveState() {
  }

  /**
   * Restore the state of this process after a context switch. Called by
   * <tt>UThread.restoreState()</tt>.
   */
  public void restoreState() {
    Machine.processor().setPageTable(pageTable);
  }

  /**
   * Read a null-terminated string from this process's virtual memory. Read
   * at most <tt>maxLength + 1</tt> bytes from the specified address, search
   * for the null terminator, and convert it to a <tt>java.lang.String</tt>,
   * without including the null terminator. If no null terminator is found,
   * returns <tt>null</tt>.
   *
   * @param	vaddr	the starting virtual address of the null-terminated
   *			string.
   * @param	maxLength	the maximum number of characters in the string,
   *				not including the null terminator.
   * @return	the string read, or <tt>null</tt> if no null terminator was
   *		found.
   */
  public String readVirtualMemoryString(int vaddr, int maxLength) {
    Lib.assertTrue(maxLength >= 0);

    byte[] bytes = new byte[maxLength+1];

    int bytesRead = readVirtualMemory(vaddr, bytes);

    for (int length=0; length<bytesRead; length++) {
      if (bytes[length] == 0)
        return new String(bytes, 0, length);
    }

    return null;
  }

  /**
   * Transfer data from this process's virtual memory to all of the specified
   * array. Same as <tt>readVirtualMemory(vaddr, data, 0, data.length)</tt>.
   *
   * @param	vaddr	the first byte of virtual memory to read.
   * @param	data	the array where the data will be stored.
   * @return	the number of bytes successfully transferred.
   */
  public int readVirtualMemory(int vaddr, byte[] data) {
    return readVirtualMemory(vaddr, data, 0, data.length);
  }

  /**
   * Transfer data from this process's virtual memory to the specified array.
   * This method handles address translation details. This method must
   * <i>not</i> destroy the current process if an error occurs, but instead
   * should return the number of bytes successfully copied (or zero if no
   * data could be copied).
   *
   * @param	vaddr	the first byte of virtual memory to read.
   * @param	data	the array where the data will be stored.
   * @param	offset	the first byte to write in the array.
   * @param	length	the number of bytes to transfer from virtual memory to
   *			the array.
   * @return	the number of bytes successfully transferred.
   */
  public int readVirtualMemory(int vaddr, byte[] data, int offset,
      int length) {
    if (!(offset >= 0 && length >= 0 && offset+length <= data.length)) {
    	return 0;
    }

    byte[] memory = Machine.processor().getMemory();

    // ntdo, handle read pages from phyMem
    if (vaddr < 0 || vaddr >= memory.length) {
    	if (parent != null) 
    		parent.status = 0;
      return 0;
    }

    int pageStart = vaddr / pageSize;
    int limit = Math.min(Machine.processor().getNumPhysPages() * pageSize - 1, vaddr + length);
    int amount = 0;
    int pageEnd = limit / pageSize;

    for (int i = pageStart; i <= pageEnd; i++) {
      if (pageTable[i] == null)
    	  break;
      int ppn = pageTable[i].ppn;
      pageTable[i].used = true;
      int startPhyMem = ppn * pageSize;

      if (i == pageStart) {
        startPhyMem += vaddr % pageSize;
        int leftAmount = Math.min(pageSize - vaddr % pageSize, length);
        System.arraycopy(memory, startPhyMem, data, offset, leftAmount);
        //			System.out.println("read vm : " + i + " " + pageTable[i].ppn + " " + vaddr + " " + startPhyMem + " " + leftAmount);
        offset += leftAmount;
        length -= leftAmount;
        amount += leftAmount;
      } else if (i == pageEnd && pageEnd != pageStart) {
        length = Math.min(length, pageSize);
        System.arraycopy(memory, startPhyMem, data, offset, length);
        amount += length;
      } else {
        System.arraycopy(memory, startPhyMem, data, offset, pageSize);
        offset += pageSize;
        length -= pageSize;
        amount += pageSize;
      }
    }
    return amount;
    //	if (vaddr < 0 || vaddr >= memory.length) 
    //		return 0; 
    //	
    //	vaddr += pageSize * 15 * (pidSet.size() - 1);
    //	int amount = Math.min(length, memory.length - vaddr);
    //	System.arraycopy(memory, vaddr, data, offset, amount);
    //	return amount;
  }

  /**
   * Transfer all data from the specified array to this process's virtual
   * memory.
   * Same as <tt>writeVirtualMemory(vaddr, data, 0, data.length)</tt>.
   *
   * @param	vaddr	the first byte of virtual memory to write.
   * @param	data	the array containing the data to transfer.
   * @return	the number of bytes successfully transferred.
   */
  public int writeVirtualMemory(int vaddr, byte[] data) {
    return writeVirtualMemory(vaddr, data, 0, data.length);
  }

  /**
   * Transfer data from the specified array to this process's virtual memory.
   * This method handles address translation details. This method must
   * <i>not</i> destroy the current process if an error occurs, but instead
   * should return the number of bytes successfully copied (or zero if no
   * data could be copied).
   *
   * @param	vaddr	the first byte of virtual memory to write.
   * @param	data	the array containing the data to transfer.
   * @param	offset	the first byte to transfer from the array.
   * @param	length	the number of bytes to transfer from the array to
   *			virtual memory.
   * @return	the number of bytes successfully transferred.
   */
  public int writeVirtualMemory(int vaddr, byte[] data, int offset, int length) {
    if (!(offset >= 0 && length >= 0 && offset+length <= data.length)) {
    	return 0;
    }
    byte[] memory = Machine.processor().getMemory();

    // ntdo 
    if (vaddr < 0 || vaddr >= memory.length) {
    	if (parent != null) 
    		parent.status = 0;
      return 0;
    }

    int pageStart = vaddr / pageSize;
    int limit = Math.min(Machine.processor().getNumPhysPages()* pageSize - 1, vaddr + length);
    int amount = 0;
    int pageEnd = limit / pageSize;
    
    for (int i = pageStart; i <= pageEnd; i++) {
    	if (pageTable[i] == null) {
    		int vpn = i; 
    		int ppn = 0; 
    		UserProcess.lock.acquire();
    			ppn = UserKernel.phyMem.removeFirst();
    		UserProcess.lock.release();
    		pageTable[vpn] = new TranslationEntry(vpn, ppn, true, false, false, false); 
//    		System.out.println("create new page");
    	}
    }

    for (int i = pageStart; i <= pageEnd; i++) {
      if (pageTable[i] == null || pageTable[i].readOnly) {
        break;
      }
      int ppn = pageTable[i].ppn;
      pageTable[i].used = true;
      pageTable[i].dirty = true;
      int startPhyMem = ppn * pageSize;

      if (i == pageStart) {
        startPhyMem += vaddr % pageSize;
        int leftAmount = Math.min(pageSize - vaddr % pageSize, length);
        System.arraycopy(data, offset, memory, startPhyMem, leftAmount);
//        System.out.println("read vm : " + i + " " + pageTable[i].ppn + " " + vaddr + " " + startPhyMem + " " + leftAmount);
        offset += leftAmount;
        length -= leftAmount;
        amount += leftAmount;
      } else if (i == pageEnd && pageEnd != pageStart) {
        length = Math.min(length, pageSize);
        System.arraycopy(data, offset, memory, startPhyMem, length);
        amount += length;
      } else {
        System.arraycopy(data, offset, memory, startPhyMem, pageSize);
        offset += pageSize;
        length -= pageSize;
        amount += pageSize;
      }
    }

    return amount;
    //	if (vaddr < 0 || vaddr >= memory.length) 
    //		return 0;
    //	
    //	vaddr += 15 * pageSize * (pids.keySet().size() - 1);
    //	int amount = Math.min(length, memory.length - vaddr);
    //	System.arraycopy(data, offset, memory, vaddr, amount);
    //	return amount;
  }

  /**
   * Load the executable with the specified name into this process, and
   * prepare to pass it the specified arguments. Opens the executable, reads
   * its header information, and copies sections and arguments into this
   * process's virtual memory.
   *
   * @param	name	the name of the file containing the executable.
   * @param	args	the arguments to pass to the executable.
   * @return	<tt>true</tt> if the executable was successfully loaded.
   */
  private boolean load(String name, String[] args) {
    Lib.debug(dbgProcess, "UserProcess.load(\"" + name + "\")");

    OpenFile executable = ThreadedKernel.fileSystem.open(name, false);
    if (executable == null) {
      Lib.debug(dbgProcess, "\topen failed");
      return false;
    }

    try {
      coff = new Coff(executable);
    }
    catch (EOFException e) {
      executable.close();
      Lib.debug(dbgProcess, "\tcoff load failed");
      return false;
    }

    // make sure the sections are contiguous and start at page 0
    numPages = 0;
    for (int s=0; s<coff.getNumSections(); s++) {
      CoffSection section = coff.getSection(s);
      if (section.getFirstVPN() != numPages) {
        coff.close();
        Lib.debug(dbgProcess, "\tfragmented executable");
        return false;
      }
      numPages += section.getLength();
    }

    // make sure the argv array will fit in one page
    byte[][] argv = new byte[args.length][];
    int argsSize = 0;
    for (int i=0; i<args.length; i++) {
      argv[i] = args[i].getBytes();
      // 4 bytes for argv[] pointer; then string plus one for null byte
      argsSize += 4 + argv[i].length + 1;
    }
    if (argsSize > pageSize) {
      coff.close();
      Lib.debug(dbgProcess, "\targuments too long");
      return false;
    }

    // program counter initially points at the program entry point
    initialPC = coff.getEntryPoint();	

    // next comes the stack; stack pointer initially points to top of it

    numPages += stackPages;
    // ntdo, the initial SP should from the top
    initialSP = (Machine.processor().getNumPhysPages() - 1) *pageSize - 4;

    // and finally reserve 1 page for arguments
    numPages++;

    if (!loadSections())
      return false;

    // store arguments in last page
    int entryOffset = (Machine.processor().getNumPhysPages() - 1)* pageSize;
    int stringOffset = entryOffset + args.length*4;

    this.argc = args.length;
    this.argv = entryOffset;

    for (int i=0; i<argv.length; i++) {
      byte[] stringOffsetBytes = Lib.bytesFromInt(stringOffset);
      Lib.assertTrue(writeVirtualMemory(entryOffset,stringOffsetBytes) == 4);
      entryOffset += 4;
      Lib.assertTrue(writeVirtualMemory(stringOffset, argv[i]) ==
          argv[i].length);
      stringOffset += argv[i].length;
      Lib.assertTrue(writeVirtualMemory(stringOffset,new byte[] { 0 }) == 1);
      stringOffset += 1;
    }

    return true;
  }

  /**
   * Allocates memory for this process, and loads the COFF sections into
   * memory. If this returns successfully, the process will definitely be
   * run (this is the last step in process initialization that can fail).
   *
   * @return	<tt>true</tt> if the sections were successfully loaded.
   */
  protected boolean loadSections() {
    if (numPages > UserKernel.phyMem.size()) {
      coff.close();
      Lib.debug(dbgProcess, "\tinsufficient physical memory");
      return false;
    }

    // load sections
    int count = 0;
    for (int s=0; s<coff.getNumSections(); s++) {
      CoffSection section = coff.getSection(s);
      count += section.getLength();

      Lib.debug(dbgProcess, "\tinitializing " + section.getName()
          + " section (" + section.getLength() + " pages)");

      for (int i=0; i<section.getLength(); i++) {
        // ntdo, remove the first available page 
        int vpn = section.getFirstVPN() + i;
        int ppn = 0;
        UserProcess.lock.acquire();
          ppn = UserKernel.phyMem.removeFirst();
        UserProcess.lock.release();
//        System.out.println("vm = "  + vpn + ", pm =  " + ppn + ", readOnly = " + section.isReadOnly());
	    pageTable[vpn] = new TranslationEntry(vpn,ppn, true,false,false,false);
        if (section.isReadOnly()) {
          pageTable[vpn].readOnly = true;
        }
        section.loadPage(i, ppn);
      }
    }

    // ntdo - add pages from stack to 
    int maxPage = Machine.processor().getNumPhysPages() - 1;
    for (int i = 0; i < stackPages; i++) {
      int ppn = 0; 
      UserProcess.lock.acquire();
        ppn = UserKernel.phyMem.removeFirst();
      UserProcess.lock.release();
      int vpn = maxPage - i - 1;
//          	System.out.println("vm = "  + vpn + ", pm =  " + ppn);
//      pageTable[vpn].ppn = ppn;
	    pageTable[vpn] = new TranslationEntry(vpn,ppn, true,false,false,false);
    }

    // ntdo - last page for argument
    int ppn = 0;
    UserProcess.lock.acquire();
      ppn = UserKernel.phyMem.removeFirst();
    UserProcess.lock.release();
    int vpn = maxPage; 
//    	System.out.println("vm = "  + vpn + ", pm =  " + ppn);
//    pageTable[vpn].ppn = ppn;
    pageTable[vpn] = new TranslationEntry(vpn,ppn, true,false,false,false);
    return true;
  }

  /**
   * Release any resources allocated by <tt>loadSections()</tt>.
   */
  protected void unloadSections() {
	  for (int i = 0; i < Machine.processor().getNumPhysPages(); i++) {
		  if (pageTable[i] != null) {
			  UserProcess.lock.acquire();
				  UserKernel.phyMem.add(pageTable[i].ppn);
			  UserProcess.lock.release();
		  }
	  }
  }    

  /**
   * Initialize the processor's registers in preparation for running the
   * program loaded into this process. Set the PC register to point at the
   * start function, set the stack pointer register to point at the top of
   * the stack, set the A0 and A1 registers to argc and argv, respectively,
   * and initialize all other registers to 0.
   */
  public void initRegisters() {
    Processor processor = Machine.processor();

    // by default, everything's 0
    for (int i=0; i<processor.numUserRegisters; i++)
      processor.writeRegister(i, 0);

    // initialize PC and SP according
    processor.writeRegister(Processor.regPC, initialPC);
    processor.writeRegister(Processor.regSP, initialSP);

    // initialize the first two argument registers to argc and argv
    processor.writeRegister(Processor.regA0, argc);
    processor.writeRegister(Processor.regA1, argv);
  }

  /**
   * Handle the halt() system call. 
   */
  private int handleHalt() {

    // make sure only root can called halt (the first process in the system)
    if (root == this)  {
      Machine.halt();
      Lib.assertNotReached("Machine.halt() did not halt machine!");
    }

    return 0;
  }

  /*
   * ntdo: handle exec system
   * return pid for child process
   */
  private int handleExec(int a0, int a1, int a2) {
    String fileName = readVirtualMemoryString(a0, STRING_READ);
    if (fileName == null)
    	return -1;
    Lib.debug(dbgProcess, "[exec] file = " + fileName);

    // read argv from memory
    byte[] bytesRead = new byte[a1 * 4];
    readVirtualMemory(a2, bytesRead);

    if (a1 < 0)
    	return -1;
    String[] argv = new String[a1];
    
    for (int i = 0; i < a1; i++) {
      int address = Lib.bytesToInt(bytesRead, i * 4);
      String text = readVirtualMemoryString(address, STRING_READ);
      argv[i] = text;
    }

    // return random number, as PID for child process
    UserProcess process = UserProcess.newUserProcess();
    process.parent = this;
    this.children.add(process);
    
    if (!process.execute(fileName, argv)) {
    	this.children.remove(process);
    	pids.remove(process.pid);
    	parent.status = 0;
    	return -1;
    }
    
//    System.out.println(process.pid);
    return process.pid;
  }

  private int handleExit(int a0) {
    Lib.debug(dbgProcess, "[syscall - exit] pid = " + pid + ", code = " + a0);
    System.out.println("exit code = " + a0);
    
    pids.remove(this.pid);
    // close all descriptor 0, 1
    for (Iterator<Integer> iter = opennedFile.keySet().iterator(); iter.hasNext();) {
      OpenFile file = opennedFile.get(iter.next());
      if (file != null) 
        file.close();
    }
    
    for (int i = 0; i < children.size(); i++) {
    	if (children.get(i) != null)
	    	children.get(i).parent = null;
    }
    children = null;
    if (parent != null) {
	    this.parent.children.remove(this);
    
	    // write status back to memory
	    byte[] writeData = Lib.bytesFromInt(a0);
	    parent.writeVirtualMemory(returnCodeExit, writeData);
    }

    // remove all pages and add back to phyMem
    unloadSections();
    if (pids.keySet().size() == 0)
      Machine.terminate();

    // handle join
    if (threadParent != null) {
      Machine.interrupt().disable();
      threadParent.ready();
      Machine.interrupt().enable();
    }
    KThread.finish();
    // never reach here
//    System.out.println("never reach here");
    return 0;
  }

  private int handleJoin(int a0, int a1) {
    Lib.debug(dbgProcess, "handleJoin");
    status = 1;
//    System.out.println("returnAddress exit code = " + a1);
    // need to check can't find process
    if (!pids.keySet().contains(a0)) {
    	return -1;
    }
    UserProcess process = pids.get(a0);
    UserProcess tempProcess = process;

    // check parent, parent's parent
    boolean stop = true;
    while (tempProcess != null) {
    	if (tempProcess.parent == this) {
    		stop = false;
    		break;
    	}
    	tempProcess = tempProcess.parent;
    }
    
    if (stop) 
    	return -1;

    // need to check threadParent != null
    process.threadParent = KThread.currentThread();
    process.returnCodeExit = a1;
    Machine.interrupt().disable();
    KThread.sleep();
    Machine.interrupt().enable();
    
    byte[] bytesRead = new byte[4];
    readVirtualMemory(a1, bytesRead);
    System.out.println(Arrays.toString(bytesRead));
    System.out.println("Pids = " + pid + ", " + process.pid);
    
    return status;
  }

  private int handleCreate(int fileAddr) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleCreate]");
    String fileName = readVirtualMemoryString(fileAddr, STRING_READ);
    if (fileName == null)
      return -1;
    OpenFile fileCreated = ThreadedKernel.fileSystem.open(fileName, true);
    if (fileCreated == null) 
      return -1;

    descriptorCount += 1;
    opennedFile.put(descriptorCount, fileCreated);
    return descriptorCount;
  }
  //  private int handleCreate( int a0 ) {
  //    if ( freeFileDescriptors.isEmpty() ) return -1;
  //    String fileName = readVirtualMemoryString( a0, 256 );
  //    if ( fileName == null ) return -1;
  //    OpenFile file = UserKernel.fileSystem.open( fileName, true );
  //    if ( file == null ) return -1;
  //    int newFileDescriptor = freeFileDescriptors.poll();
  //    userFileDescriptorTable.put( newFileDescriptor, file );
  //    return newFileDescriptor;
  //  }

  private int handleOpen(int fileAddr) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleOpen]");
    String fileName = readVirtualMemoryString(fileAddr, STRING_READ);
    if (fileName == null)
    	return -1;
    OpenFile fileOpenned = ThreadedKernel.fileSystem.open(fileName, false);
    if (fileOpenned == null)
      return -1;
    descriptorCount += 1;
    opennedFile.put(descriptorCount, fileOpenned);
    return descriptorCount;
  }
  //  private int handleOpen( int a0 ) {
  //    if ( freeFileDescriptors.isEmpty() ) return -1;
  //    String fileName = readVirtualMemoryString( a0, 256 );
  //    if ( fileName == null ) return -1;
  //    OpenFile file = UserKernel.fileSystem.open( fileName, false );
  //    if ( file == null ) return -1;
  //    int newFileDescriptor = freeFileDescriptors.poll();
  //    userFileDescriptorTable.put( newFileDescriptor, file );
  //    return newFileDescriptor;
  //  }

  private int handleRead(int fileDescriptor, int bufferAddrs, int count) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0 || count < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleRead]" + bufferAddrs + " " + count);
    OpenFile file = opennedFile.get(fileDescriptor);
    if (file == null) 
      return -1;
    byte[] buffer = new byte[count];
    int bytesRead = file.read(buffer, 0, count);
    if (bytesRead < 0) 
      return -1;
    int bytesWrite = writeVirtualMemory(bufferAddrs, buffer, 0, bytesRead);
    if (bytesWrite != bytesRead)
      return -1;
    return bytesRead;
  }
  //  private int handleRead( int a0, int a1, int a2 ) {
  //    Lib.debug(dbgProcess, "handle Read");
  //    if ( a0 < 0 || a0 > 15 || freeFileDescriptors.contains( a0 ) ) return -1;
  //    OpenFile file = userFileDescriptorTable.get( a0 );
  //    if ( file == null ) return -1;
  //    byte[] data = new byte[a2];
  //    int bytesReadFromFile = file.read(data, 0, a2 );
  //    int bytesWriteToVM = writeVirtualMemory( a1, data, 0, bytesReadFromFile );
  //    return bytesWriteToVM >= bytesReadFromFile ? bytesWriteToVM : -1;
  //  }

  private int handleWrite(int fileDescriptor, int bufferAddrs, int count) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0 || count < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleWrite]" + bufferAddrs + " " + count);
    OpenFile file = opennedFile.get(fileDescriptor);
    if (file == null) 
      return -1;
    byte[] buffer = new byte[count];
    int bytesRead = readVirtualMemory(bufferAddrs, buffer);
    if (bytesRead != count) 
      return -1;
    int bytesWrite = file.write(buffer, 0, bytesRead);
    if (bytesWrite != bytesRead)
      return -1;
    return bytesWrite;
  }
  //  private int handleWrite( int a0, int a1, int a2 ) {
  //    Lib.debug(dbgProcess, "handle Write");
  //    if ( a0 < 0 || a0 > 15 || freeFileDescriptors.contains( a0 ) ) return -1;
  //    OpenFile file = userFileDescriptorTable.get( a0 );
  //    if ( file == null ) return -1;
  //    byte[] data = new byte[a2];
  //    int bytesReadFromVM = readVirtualMemory( a1, data );
  //    if ( bytesReadFromVM < a2 ) return -1;
  //    int bytesWriteToFile = file.write( data, 0, a2 );
  //    return bytesWriteToFile >= a2 ? bytesWriteToFile : -1;
  //  }

  private int handleClose(int fileDescriptor) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleClose]");
    OpenFile file = opennedFile.get(fileDescriptor);
    if (file == null) 
      return -1;
    file.close();
    opennedFile.remove(fileDescriptor);
    descriptorCount -= 1;
    return 0;
  }
  //  private int handleClose( int a0 ) {
  //    if ( a0 < 0 || a0 > 15 || freeFileDescriptors.contains( a0 ) ) return -1;
  //    OpenFile file = userFileDescriptorTable.get( a0 );
  //    if ( file == null ) return -1;
  //    file.close();
  //    userFileDescriptorTable.put( a0, null );
  //    freeFileDescriptors.add( a0 );
  //    return 0;
  //  }

  private int handleUnlink(int fileAddr) {
    if (descriptorCount >= MAX_DESCRIPTORS || descriptorCount < 0)
      return -1;
    Lib.debug(dbgProcess, "[handleUnlink]");
    String fileName = readVirtualMemoryString(fileAddr, STRING_READ);
    if (fileName == null)
    	return -1;
    boolean deleted = ThreadedKernel.fileSystem.remove(fileName);
    if (!deleted) 
      return -1;
    return 0;
  }
  //  private int handleUnlink( int a0 ) {
  //    String fileName = readVirtualMemoryString( a0, 256 );
  //    if ( fileName == null ) return -1;
  //    boolean removed = UserKernel.fileSystem.remove( fileName );
  //    return removed ? 0 : -1;
  //  }

  private static final int
    syscallHalt = 0,
                syscallExit = 1,
                syscallExec = 2,
                syscallJoin = 3,
                syscallCreate = 4,
                syscallOpen = 5,
                syscallRead = 6,
                syscallWrite = 7,
                syscallClose = 8,
                syscallUnlink = 9;

  /**
   * Handle a syscall exception. Called by <tt>handleException()</tt>. The
   * <i>syscall</i> argument identifies which syscall the user executed:
   *
   * <table>
   * <tr><td>syscall#</td><td>syscall prototype</td></tr>
   * <tr><td>0</td><td><tt>void halt();</tt></td></tr>
   * <tr><td>1</td><td><tt>void exit(int status);</tt></td></tr>
   * <tr><td>2</td><td><tt>int  exec(char *name, int argc, char **argv);
   * 								</tt></td></tr>
   * <tr><td>3</td><td><tt>int  join(int pid, int *status);</tt></td></tr>
   * <tr><td>4</td><td><tt>int  creat(char *name);</tt></td></tr>
   * <tr><td>5</td><td><tt>int  open(char *name);</tt></td></tr>
   * <tr><td>6</td><td><tt>int  read(int fd, char *buffer, int size);
   *								</tt></td></tr>
   * <tr><td>7</td><td><tt>int  write(int fd, char *buffer, int size);
   *								</tt></td></tr>
   * <tr><td>8</td><td><tt>int  close(int fd);</tt></td></tr>
   * <tr><td>9</td><td><tt>int  unlink(char *name);</tt></td></tr>
   * </table>
   * 
   * @param	syscall	the syscall number.
   * @param	a0	the first syscall argument.
   * @param	a1	the second syscall argument.
   * @param	a2	the third syscall argument.
   * @param	a3	the fourth syscall argument.
   * @return	the value to be returned to the user.
   */
  public int handleSyscall(int syscall, int a0, int a1, int a2, int a3) {
    switch (syscall) {
    case syscallHalt:
      return handleHalt();
    case syscallExec:
      return handleExec(a0, a1, a2);
    case syscallExit:
      return handleExit(a0);
    case syscallJoin:
      return handleJoin(a0, a1);
    case syscallCreate:
      return handleCreate(a0);
    case syscallOpen:
      return handleOpen(a0);
    case syscallRead:
      return handleRead(a0, a1, a2);
    case syscallWrite:
      return handleWrite(a0, a1, a2);
    case syscallClose:
      return handleClose(a0);
    case syscallUnlink:
      return handleUnlink(a0);

    default:
      Lib.debug(dbgProcess, "Unknown syscall " + syscall);
      Lib.assertNotReached("Unknown system call!");
    }
    return 0;
  }

  /**
   * Handle a user exception. Called by
   * <tt>UserKernel.exceptionHandler()</tt>. The
   * <i>cause</i> argument identifies which exception occurred; see the
   * <tt>Processor.exceptionZZZ</tt> constants.
   *
   * @param	cause	the user exception that occurred.
   */
  public void handleException(int cause) {
    Processor processor = Machine.processor();

    switch (cause) {
    case Processor.exceptionSyscall:
      int result = handleSyscall(processor.readRegister(Processor.regV0),
          processor.readRegister(Processor.regA0),
          processor.readRegister(Processor.regA1),
          processor.readRegister(Processor.regA2),
          processor.readRegister(Processor.regA3)
          );
      processor.writeRegister(Processor.regV0, result);
      processor.advancePC();
      break;				       
    case Processor.exceptionAddressError:
    case Processor.exceptionBusError:
    case Processor.exceptionIllegalInstruction:
    case Processor.exceptionOverflow:
    case Processor.exceptionPageFault:
    case Processor.exceptionReadOnly:
    case Processor.exceptionTLBMiss:
    	handleExit(1);
    	break;

    default:
      Lib.debug(dbgProcess, "Unexpected exception: " +
          Processor.exceptionNames[cause]);
      Lib.assertNotReached("Unexpected exception");
    }
  }

  /** The program being run by this process. */
  protected Coff coff;

  /** This process's page table. */
  protected TranslationEntry[] pageTable;
  /** The number of contiguous pages occupied by the program. */
  protected int numPages;

  /** The number of pages in the program's stack. */
  protected final int stackPages = 8;

  private int initialPC, initialSP;
  private int argc, argv;

  // ntdo
  private int pid;
  private int status = 1;
  private int returnCodeExit;
  private static Lock lock = new Lock();
  private UserProcess parent = null;
  private List<UserProcess> children = new LinkedList<UserProcess>();
  private KThread threadParent = null;
  private int descriptorCount = 1;
  private Hashtable<Integer, OpenFile> opennedFile = new Hashtable<Integer, OpenFile>();

  private static UserProcess root = null;
  //    private HashMap<Integer, OpenFile> userFileDescriptorTable = new HashMap<Integer, OpenFile>();
  //    private LinkedList<Integer> freeFileDescriptors = new LinkedList<Integer>();
  //    private static final int maxOpenFiles = 16;

  private static Hashtable<Integer, UserProcess> pids = new Hashtable<Integer, UserProcess>();
  private static final int pageSize = Processor.pageSize;
  private static final char dbgProcess = 'a';
}
