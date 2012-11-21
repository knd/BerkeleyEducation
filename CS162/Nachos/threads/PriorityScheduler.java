package nachos.threads;

import java.util.LinkedList;

import nachos.machine.Kernel;
import nachos.machine.Lib;
import nachos.machine.Machine;

/**
 * A scheduler that chooses threads based on their priorities.
 *
 * <p>
 * A priority scheduler associates a priority with each thread. The next thread
 * to be dequeued is always a thread with priority no less than any other
 * waiting thread's priority. Like a round-robin scheduler, the thread that is
 * dequeued is, among all the threads of the same (highest) priority, the
 * thread that has been waiting longest.
 *
 * <p>
 * Essentially, a priority scheduler gives access in a round-robin fassion to
 * all the highest-priority threads, and ignores all other threads. This has
 * the potential to
 * starve a thread if there's always a thread waiting with higher priority.
 *
 * <p>
 * A priority scheduler must partially solve the priority inversion problem; in
 * particular, priority must be donated through locks, and through joins.
 */
public class PriorityScheduler extends Scheduler {
    /**
     * Allocate a new priority scheduler.
     */
    public PriorityScheduler() {
    }
    
    /**
     * Allocate a new priority thread queue.
     *
     * @param transferPriority  <tt>true</tt> if this queue should
     *          transfer priority from waiting threads
     *          to the owning thread.
     * @return  a new priority thread queue.
     */
    public ThreadQueue newThreadQueue(boolean transferPriority) {
  return new PriorityQueue(transferPriority);
    }

    public int getPriority(KThread thread) {
  Lib.assertTrue(Machine.interrupt().disabled());
           
  return getThreadState(thread).getPriority();
    }

    public int getEffectivePriority(KThread thread) {
  Lib.assertTrue(Machine.interrupt().disabled());
           
  return getThreadState(thread).getEffectivePriority();
    }

    public void setPriority(KThread thread, int priority) {
  Lib.assertTrue(Machine.interrupt().disabled());
           
  Lib.assertTrue(priority >= priorityMinimum &&
       priority <= priorityMaximum);
  
  getThreadState(thread).setPriority(priority);
    }

    public boolean increasePriority() {
  boolean intStatus = Machine.interrupt().disable();
           
  KThread thread = KThread.currentThread();

  int priority = getPriority(thread);
  if (priority == priorityMaximum)
      return false;

  setPriority(thread, priority+1);

  Machine.interrupt().restore(intStatus);
  return true;
    }

    public boolean decreasePriority() {
  boolean intStatus = Machine.interrupt().disable();
           
  KThread thread = KThread.currentThread();

  int priority = getPriority(thread);
  if (priority == priorityMinimum)
      return false;

  setPriority(thread, priority-1);

  Machine.interrupt().restore(intStatus);
  return true;
    }

    /**
     * The default priority for a new thread. Do not change this value.
     */
    public static final int priorityDefault = 1;
    /**
     * The minimum priority that a thread can have. Do not change this value.
     */
    public static final int priorityMinimum = 0;
    /**
     * The maximum priority that a thread can have. Do not change this value.
     */
    public static final int priorityMaximum = 7;    

    /**
     * Return the scheduling state of the specified thread.
     *
     * @param thread  the thread whose scheduling state to return.
     * @return  the scheduling state of the specified thread.
     */
    protected ThreadState getThreadState(KThread thread) {
  if (thread.schedulingState == null)
      thread.schedulingState = new ThreadState(thread);

  return (ThreadState) thread.schedulingState;
    }

    /**
     * A <tt>ThreadQueue</tt> that sorts threads by priority.
     */
    protected class PriorityQueue extends ThreadQueue {
  PriorityQueue(boolean transferPriority) {
      this.transferPriority = transferPriority;
  }

  public void waitForAccess(KThread thread) {
      Lib.assertTrue(Machine.interrupt().disabled());
      getThreadState(thread).waitForAccess(this);
  }

  public void acquire(KThread thread) {
      Lib.assertTrue(Machine.interrupt().disabled());
      getThreadState(thread).acquire(this);
  }

  public KThread nextThread() {
      Lib.assertTrue(Machine.interrupt().disabled());
      // implement me
      // release resource since not an owner of this resource any more
      ThreadState nextThreadState = queue.poll(); 
      if (nextThreadState == null) {
        return null;
      }
      KThread nextThread = nextThreadState.thread;
      // remove "this queue" in nextThread.listQueue
      nextThreadState.waitingInQueue = null;
      
      // change back to its priority
      if (owner != null)
        owner.effectivePriority = owner.priority;
      owner = nextThreadState;
      return nextThread;
  }

  /**
   * Return the next thread that <tt>nextThread()</tt> would return,
   * without modifying the state of this queue.
   *
   * @return  the next thread that <tt>nextThread()</tt> would
   *    return.
   */
  protected ThreadState pickNextThread() {
    ThreadState nextThreadState = queue.peek();
    return nextThreadState;
  }
  
  public void print() {
      Lib.assertTrue(Machine.interrupt().disabled());
      // implement me (if you want)
  }

  /**
   * <tt>true</tt> if this queue should transfer priority from waiting
   * threads to the owning thread.
   */
  public boolean transferPriority;
  // ntdo
  public ThreadState owner;
  public java.util.Queue<ThreadState> queue = new java.util.PriorityQueue<ThreadState>();
    }

    /**
     * The scheduling state of a thread. This should include the thread's
     * priority, its effective priority, any objects it owns, and the queue
     * it's waiting for, if any.
     *
     * @see nachos.threads.KThread#schedulingState
     */
    protected class ThreadState implements Comparable<ThreadState>{
  /**
   * Allocate a new <tt>ThreadState</tt> object and associate it with the
   * specified thread.
   *
   * @param thread  the thread this state belongs to.
   */
  public ThreadState(KThread thread) {
      this.thread = thread;
      setPriority(priorityDefault);
  }

  /**
   * Return the priority of the associated thread.
   *
   * @return  the priority of the associated thread.
   */
  public int getPriority() {
      return priority;
  }

  /**
   * Return the effective priority of the associated thread.
   *
   * @return  the effective priority of the associated thread.
   */
  public int getEffectivePriority() {
      // implement me
    return effectivePriority;
  }

  /**
   * Set the priority of the associated thread to the specified value.
   *
   * @param priority  the new priority.
   */
  public void setPriority(int priority) {
      if (this.priority == priority)
    return;
      
      this.priority = priority;
      // implement me
      this.effectivePriority = priority;
  }

  /**
   * Called when <tt>waitForAccess(thread)</tt> (where <tt>thread</tt> is
   * the associated thread) is invoked on the specified priority queue.
   * The associated thread is therefore waiting for access to the
   * resource guarded by <tt>waitQueue</tt>. This method is only called
   * if the associated thread cannot immediately obtain access.
   *
   * @param waitQueue the queue that the associated thread is
   *        now waiting on.
   *
   * @see nachos.threads.ThreadQueue#waitForAccess
   */
  public void waitForAccess(PriorityQueue waitQueue) {
      // implement me
    waitQueue.queue.add(this);
    this.waitingInQueue = waitQueue;
    
    // ntdo: add waiQueue in listQueue of this threadState
    update(this, waitQueue);
  }
  
  // ntdo
  /*
   * Need update the owner of this "queue" when adding this "thread"
   */
  private void update(ThreadState thread, PriorityQueue currentQueue) {
    if (currentQueue != null && currentQueue.queue != null) {
      currentQueue.queue.remove(thread);
      currentQueue.queue.add(thread);
    }
    
    if (currentQueue.transferPriority) {
      if (currentQueue.owner != null) {
        if (currentQueue.owner.effectivePriority  < thread.effectivePriority) {
          currentQueue.owner.effectivePriority = thread.effectivePriority;
          if (currentQueue.owner.waitingInQueue != null) {
            update(currentQueue.owner, currentQueue.owner.waitingInQueue);
          }
        }
      }
    }
  }

  /**
   * Called when the associated thread has acquired access to whatever is
   * guarded by <tt>waitQueue</tt>. This can occur either as a result of
   * <tt>acquire(thread)</tt> being invoked on <tt>waitQueue</tt> (where
   * <tt>thread</tt> is the associated thread), or as a result of
   * <tt>nextThread()</tt> being invoked on <tt>waitQueue</tt>.
   *
   * @see nachos.threads.ThreadQueue#acquire
   * @see nachos.threads.ThreadQueue#nextThread
   */
  public void acquire(PriorityQueue waitQueue) {
      // implement me
    // ntdo
    waitQueue.owner = this;
  } 

  /** The thread with which this object is associated. */    
  protected KThread thread;
  /** The priority of the associated thread. */
  protected int priority;
  protected int effectivePriority;
  
  // ntdo
  protected PriorityQueue waitingInQueue = null;
  @Override
  public int compareTo(ThreadState other) {
    // TODO Auto-generated method stub
    if (this.getEffectivePriority() > other.getEffectivePriority()) 
      return -1;
    
    if (this.getEffectivePriority() <= other.getEffectivePriority())
      return 1;
    
    return 0;
  }
  
    }
    
    public static void selfTest() {
      test_Effective();
    test_PriorityLevels();
    test_Effective();
    testDonation();
    testNextThread();
    testMoreDonation();
    }
    
    public static void test_Effective()
  {
    final Lock sharedResource = new Lock();
    final Semaphore acquired = new Semaphore(0);
    final Semaphore done = new Semaphore(0);
    final Semaphore released = new Semaphore(0);
    final Semaphore waiting = new Semaphore(0);
    PriorityScheduler sch = (PriorityScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
    
    //create a low priority thread that has a resource
    Runnable r1 = new Runnable() {
      public void run(){
        sharedResource.acquire();
        //signal the high priority thread that it can try to acquire now
        acquired.V();
        done.P();
        sharedResource.release();
        released.V();
      }
    };
    Runnable r2 = new Runnable(){
      public void run(){
        acquired.P();
        waiting.V();
        sharedResource.acquire();
      }
    };
    KThread low = new KThread(r1), high = new KThread(r2);
    //disable interrupts and set the priorities of our threads
    boolean intStatus = Machine.interrupt().disable();
    sch.setPriority(low, 0);
    sch.setPriority(high, 5);
    Machine.interrupt().restore(intStatus);
    low.fork();
    high.fork();
    
    //wait until the low priority thread has the resource, and the high one is waiting for it
    waiting.P();
    intStatus = Machine.interrupt().disable();
    Lib.assertTrue(sch.getEffectivePriority(low) == 5, 
        "Low priority thread did not receive a donation from high priority queue");
    Machine.interrupt().restore(intStatus);
    //tell the low priority thread to release the resource
    done.V();
    //wait until it has been released
    released.P();
    //check that the priority is low again
    intStatus = Machine.interrupt().disable();
    Lib.assertTrue(sch.getEffectivePriority(low) == 0, 
        "Low priority thread kept it's high priority after releasing the resource");
    Machine.interrupt().restore(intStatus);
  }
    
    public static class Donator implements Runnable {
    public boolean runOn;
    public KThread joiner;
    public Lock locker;
    public Condition2 cond;
    public Donator() {
      runOn = true;
      joiner = null;
      locker = null;
      cond = null;
    }
    public void run() {
      while(runOn) {
        if(joiner != null)
          joiner.join();
        if(locker != null)
          locker.acquire();
      }
    }
  }
    
    public static void testDonation() {
    Scheduler scheduler = ((ThreadedKernel)Kernel.kernel).scheduler;
    
    //create a thread which loops until it's told not to
      
    ThreadQueue Q = scheduler.newThreadQueue(true);
    ThreadQueue Q2 = scheduler.newThreadQueue(true);
    
      KThread tFirst = new KThread(new Donator());
      tFirst.setName("Busy test thread to test donation");

      boolean intStatus = Machine.interrupt().disable();
      scheduler.setPriority(tFirst, 0);
      Q.acquire(tFirst);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 0);
      KThread t = new KThread(new Donator());
      t = new KThread(new Donator());
      scheduler.setPriority(t, 1);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 1);
      t = new KThread(new Donator());
      scheduler.setPriority(t, 2);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 2);
      t = new KThread(new Donator());
      scheduler.setPriority(t, 3);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 3);
      t = new KThread(new Donator());
      scheduler.setPriority(t, 4);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 4);
      t = new KThread(new Donator());
      scheduler.setPriority(t, 5);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 5);
      t = new KThread(new Donator());
      scheduler.setPriority(t, 2);
      Q2.acquire(t);
      Q.waitForAccess(t);
      // testing circular donation -- no stack overflows!
      Q2.waitForAccess(tFirst);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 5);
      Lib.assertTrue(scheduler.getEffectivePriority(t) == 5);
      Lib.assertTrue(scheduler.getPriority(tFirst) == 0);
      
      Machine.interrupt().restore(intStatus);
  }
  
  public static void testMoreDonation() {
    // test chains of queues and ensure donation is working
    
    Scheduler scheduler = ((ThreadedKernel)Kernel.kernel).scheduler;
    
    ThreadQueue[] qs = new ThreadQueue[10];
      KThread[] threads = new KThread[10];
      for(int i = 0;i< 10; i++) {
        qs[i] = scheduler.newThreadQueue(true);
        threads[i] = new KThread(new Donator());
        qs[i].acquire(threads[i]);
        scheduler.setPriority(threads[i], i%8);
      }
      
      // test basic chain
      for(int i = 1;i<10;i++) {
        qs[i-1].waitForAccess(threads[i]);
        if(i<8)
          Lib.assertTrue(scheduler.getEffectivePriority(threads[0]) == i);
        else
          Lib.assertTrue(scheduler.getEffectivePriority(threads[0]) == 7);
      }
      KThread t = qs[5].nextThread();
      Lib.assertTrue(scheduler.getEffectivePriority(t)== 7);
      Lib.assertTrue(scheduler.getEffectivePriority(threads[0]) == 5,
          "effective priority of threads[0] is " + scheduler.getEffectivePriority(threads[0])
          + " when it should be 5");
      qs[6].nextThread();
      Lib.assertTrue(scheduler.getEffectivePriority(t)== 6);
      qs[5].waitForAccess(threads[7]);
      Lib.assertTrue(scheduler.getEffectivePriority(t)== 7);
  }
  
  public static void testNextThread() {

    Scheduler scheduler = ((ThreadedKernel)Kernel.kernel).scheduler;
    
    //create a thread which loops until it's told not to
      
    ThreadQueue Q = scheduler.newThreadQueue(true);
    ThreadQueue Q2 = scheduler.newThreadQueue(true);
    ThreadQueue Q3 = scheduler.newThreadQueue(true);

      boolean intStatus = Machine.interrupt().disable();
      
      KThread[] threads = new KThread[10];
      for(int i = 0;i< 10; i++) {
        threads[i] = new KThread(new Donator());
        scheduler.setPriority(threads[i], i%8);
      }
      
      Q.acquire(threads[1]);
      Q.waitForAccess(threads[0]);
      Q.waitForAccess(threads[2]);
      Q.waitForAccess(threads[3]);
      Q.waitForAccess(threads[4]);
      Q.waitForAccess(threads[5]);
      Q.waitForAccess(threads[7]);
      Q.waitForAccess(threads[6]);
      Q.waitForAccess(threads[8]);
      Q.waitForAccess(threads[9]);
      KThread t = null;
      for(int i = 0;i<5;i++) {
        t = Q.nextThread();
        Lib.assertTrue(t == threads[7-i]);
      }
      Lib.assertTrue(scheduler.getPriority(t)==3);
      Lib.assertTrue(scheduler.getEffectivePriority(t)==3);
      Q2.acquire(threads[1]);
      Q2.waitForAccess(threads[4]);
      Lib.assertTrue(scheduler.getEffectivePriority(threads[1])==4);
      Q3.acquire(threads[7]);
      Q3.waitForAccess(threads[1]);
      Lib.assertTrue(scheduler.getEffectivePriority(threads[1])==4);
      Q3.nextThread();
      Q3.waitForAccess(threads[5]);
      Lib.assertTrue(scheduler.getEffectivePriority(threads[1])==5);
  }
  
  //fairly basic test to make sure that high priority threads run before
  //low priority threads
  public static void test_PriorityLevels()
    {
      Lock sharedResource = new Lock();
      Scheduler sch = ((ThreadedKernel)Kernel.kernel).scheduler;
      
      //acquire the lock so that all the threads have to wait
      sharedResource.acquire();
      
      //create threads and set their priority
      PriorityLevelTest r1 = new PriorityLevelTest(sharedResource),
              r2 = new PriorityLevelTest(sharedResource),
              r3 = new PriorityLevelTest(sharedResource);
      KThread t1 = new KThread(r1),
          t2 = new KThread(r2),
          t3 = new KThread(r3);
      boolean intStatus = Machine.interrupt().disable();
      sch.setPriority(t1, 1);
      sch.setPriority(t2, 2);
      sch.setPriority(t3, 3);
      Machine.interrupt().restore(intStatus);
      t1.fork();
      t2.fork();
      t3.fork();
      
      //wait for a few ticks to make sure the threads have queued up
      ((ThreadedKernel)Kernel.kernel).alarm.waitUntil(100);
      //release the resource so that the threads can run
      sharedResource.release();
      //Check that they ran in the right order
      Lib.assertTrue(r3.finishTime() < r2.finishTime(), 
          "A thread with priority 2 was given preference over a thread with priority 3!");
      Lib.assertTrue(r2.finishTime() < r1.finishTime(), "A thread with priority 1 got the resource before one with priority 2");
    }
    
    private static class PriorityLevelTest implements Runnable
    {
      Lock m_waitFor; //resource that all threads will wait for
      long m_finishTime = 0;
      boolean m_done = false;
      Semaphore m_doneFlag = new Semaphore(0);
      PriorityLevelTest(Lock lock)
      {
        m_waitFor = lock;
      }

      public void run() {
        m_waitFor.acquire();
        m_done = true;
        m_finishTime = Machine.timer().getTime();
        m_waitFor.release();
        m_doneFlag.V();
      }
      public long finishTime()
      {
        if(!m_done)
        {
          m_doneFlag.P();
        }
        return m_finishTime;
      }
    }
}
