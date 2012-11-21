package nachos.threads;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import nachos.machine.*;

/**
 * A scheduler that chooses threads using a lottery.
 *
 * <p>
 * A lottery scheduler associates a number of tickets with each thread. When a
 * thread needs to be dequeued, a random lottery is held, among all the tickets
 * of all the threads waiting to be dequeued. The thread that holds the winning
 * ticket is chosen.
 *
 * <p>
 * Note that a lottery scheduler must be able to handle a lot of tickets
 * (sometimes billions), so it is not acceptable to maintain state for every
 * ticket.
 *
 * <p>
 * A lottery scheduler must partially solve the priority inversion problem; in
 * particular, tickets must be transferred through locks, and through joins.
 * Unlike a priority scheduler, these tickets add (as opposed to just taking
 * the maximum).
 */
public class LotteryScheduler extends PriorityScheduler {
    /**
     * Allocate a new lottery scheduler.
     */
    public LotteryScheduler() {
    }
    
    /**
     * Allocate a new lottery thread queue.
     *
     * @param transferPriority  <tt>true</tt> if this queue should
     *          transfer tickets from waiting threads
     *          to the owning thread.
     * @return  a new lottery thread queue.
     */
    public ThreadQueue newThreadQueue(boolean transferPriority) {
      return new LotteryQueue(transferPriority);
    }
    //The minimum ticket that a thread can have.
    public static final int priorityMinimum = 1;
    //The maximum ticket that a thread can have.
    public static final int priorityMaximum = Integer.MAX_VALUE;    
    
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
    
    protected ThreadState getThreadState(KThread thread) {
      if (thread.schedulingState == null)
          thread.schedulingState = new LotteryThreadState(thread);

      return (ThreadState) thread.schedulingState;
    }
    
    protected class LotteryQueue extends PriorityQueue {
      
    LotteryQueue(boolean transferPriority) {
      super(transferPriority);
    }
    
    public KThread nextThread() {
      Lib.assertTrue(Machine.interrupt().disabled());
      
      // Find the number of total ticket in the queue.
      int totalTickets = 0;
      for (ThreadState currentThreadState : this.queue) {
        totalTickets += currentThreadState.effectivePriority;
      }
      
      // Randomly generate a lottery 
      Random generator = new Random();
      int lottery = (totalTickets==0) ? 0 : Math.abs(generator.nextInt()) % totalTickets + 1;
      
      // Pick the next thread according to the lottery number
      List<ThreadState> polledThreadStates = new LinkedList<ThreadState>(); 
      ThreadState nextThreadState = null;
      while (lottery > 0) {
        nextThreadState = this.queue.poll();
        if (lottery > nextThreadState.effectivePriority) {
          polledThreadStates.add(nextThreadState);
        }
        lottery -= nextThreadState.effectivePriority;
      }
      if (nextThreadState == null) {
        return null;
      }
      KThread nextThread = nextThreadState.thread;
      nextThreadState.waitingInQueue = null;
      this.queue.addAll(polledThreadStates);
      
      // Change back to its priority
      if (owner != null) {
          owner.effectivePriority = owner.priority;
      }
      this.owner = nextThreadState;
      return nextThread;    
    }
    }  
    
    protected class LotteryThreadState extends ThreadState {
      // treat priority as number of tickets are assigned for each thread
      // treat effectivePriority as number of tickets after making donation
      // if transferPriority is false, effectivePrioirity is equal to priority
      // else effectivePriority is the sum of tickets of threads in the queue where 
      // it is the owner
            
      public LotteryThreadState(KThread thread) {
      super(thread);
    }
      
      public void waitForAccess(PriorityQueue waitQueue) {
        waitQueue.queue.add(this);
        this.waitingInQueue = waitQueue;
        update(this, waitQueue);
      }
      
      private void update (ThreadState thread, PriorityQueue currentQueue) {
        if (currentQueue != null && currentQueue.queue != null) {
          currentQueue.queue.remove(thread);
          currentQueue.queue.add(thread);
        }
        
        if (currentQueue.transferPriority) {
          if (currentQueue.owner != null) {
            currentQueue.owner.effectivePriority += thread.effectivePriority;
            if (currentQueue.owner.waitingInQueue != null) {
              update(thread, currentQueue.owner.waitingInQueue);
            }
          }
        }
      }   
    }
    
    public static void selfTest() {
      test_Effective();
      testDonation();
      testMoreDonation();
      testMoreDonation2();
      testMoreDonation3();
    }
    
    //check that effective priority is working correctly
    public static void test_Effective()
    {
      System.out.println("*********TEST EFFECTIVE*********");
      final Lock sharedResource = new Lock();
      final Semaphore acquired = new Semaphore(0);
      final Semaphore done = new Semaphore(0);
      final Semaphore released = new Semaphore(0);
      final Semaphore waiting = new Semaphore(0);
      LotteryScheduler sch = (LotteryScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
      
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
      sch.setPriority(low, 1);
      sch.setPriority(high, 8);
      Machine.interrupt().restore(intStatus);
      low.fork();
      high.fork();
      
      //wait until the low priority thread has the resource, and the high one is waiting for it
      waiting.P();
      intStatus = Machine.interrupt().disable();
      System.out.println("Low Thread Priority: " + sch.getPriority(low));
      System.out.println("High Thread Priority: " + sch.getPriority(high));
      System.out.println("Low Thread Effective Priority When Receiving Donation: " + sch.getEffectivePriority(low));
      Lib.assertTrue(sch.getEffectivePriority(low) == 9, 
          "Low priority thread did not receive a donation from high priority lottery queue");
      Machine.interrupt().restore(intStatus);
      //tell the low priority thread to release the resource
      done.V();
      //wait until it has been released
      released.P();
      //check that the priority is low again
      intStatus = Machine.interrupt().disable();
      System.out.println("Low Thread Effective Priority after Donation: " + sch.getEffectivePriority(low));
      Lib.assertTrue(sch.getEffectivePriority(low) == 1, 
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
      System.out.println("*********TEST DONATION*********");
      LotteryScheduler scheduler = (LotteryScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
    
    //create a thread which loops until it's told not to
      
    ThreadQueue Q = scheduler.newThreadQueue(true);
    ThreadQueue Q2 = scheduler.newThreadQueue(true);
    
      KThread tFirst = new KThread(new Donator());
      tFirst.setName("Busy test thread to test donation");

      boolean intStatus = Machine.interrupt().disable();
      scheduler.setPriority(tFirst, 1);
      Q.acquire(tFirst);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 1, "expected priority: 1, result: " + scheduler.getEffectivePriority(tFirst));
      KThread t = new KThread(new Donator());
      scheduler.setPriority(t, 2);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 3, "expected priority: 3, result: " + scheduler.getEffectivePriority(tFirst));
      t = new KThread(new Donator());
      scheduler.setPriority(t, 3);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 6, "expected priority: 6, result: " + scheduler.getEffectivePriority(tFirst));
      t = new KThread(new Donator());
      scheduler.setPriority(t, 4);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 10, "expected priority: 10, result: " + scheduler.getEffectivePriority(tFirst));
      t = new KThread(new Donator());
      scheduler.setPriority(t, 5);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 15, "expected priority: 15, result: " + scheduler.getEffectivePriority(tFirst));
      t = new KThread(new Donator());
      scheduler.setPriority(t, 6);
      Q.waitForAccess(t);
      Lib.assertTrue(scheduler.getEffectivePriority(tFirst) == 21, "expected priority: 21, result: " + scheduler.getEffectivePriority(tFirst));
      t = new KThread(new Donator());
      scheduler.setPriority(t, 2);
      Q2.acquire(t);
      Q.waitForAccess(t);
      System.out.println("Effective Priority: " + scheduler.getEffectivePriority(tFirst));
      Lib.assertTrue(scheduler.getPriority(tFirst) == 1);     
      Machine.interrupt().restore(intStatus);
  }
    
    public static void testMoreDonation() {
    // test chains of queues and ensure donation is working
      System.out.println("*********TEST MORE DONATION*********");
      LotteryScheduler scheduler = (LotteryScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
    
    ThreadQueue[] qs = new ThreadQueue[10];
      KThread[] threads = new KThread[10];
      boolean intStatus = Machine.interrupt().disable();
      for(int i = 0;i< 10; i++) {
        qs[i] = scheduler.newThreadQueue(true);
        threads[i] = new KThread(new Donator());
        scheduler.setPriority(threads[i], i+1);
        qs[i].acquire(threads[i]);
      }
      
      // test basic chain
      int pSum = 1;
      for(int i = 1;i<10;i++) {
        System.out.println("Loop " + i);
        pSum += i+1;
        qs[i-1].waitForAccess(threads[i]);
        System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0]));
        System.out.println("Effective Priority of Thread " + (i-1) + ": " + scheduler.getEffectivePriority(threads[i-1]));
        Lib.assertTrue(scheduler.getEffectivePriority(threads[0]) == pSum,
            "priority of thread incorrect, expected: " + pSum + ", received: " + scheduler.getEffectivePriority(threads[0]));
      }
      Machine.interrupt().restore(intStatus);
  }
    
    public static void testMoreDonation2() {
    // test chains of queues and ensure donation is working
      System.out.println("*********TEST MORE DONATION 2*********");
      LotteryScheduler scheduler = (LotteryScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
    
    ThreadQueue[] qs = new ThreadQueue[10];
      KThread[] threads = new KThread[10];
      boolean intStatus = Machine.interrupt().disable();
      for(int i = 0;i< 10; i++) {
        qs[i] = scheduler.newThreadQueue(true);
        threads[i] = new KThread(new Donator());
        scheduler.setPriority(threads[i], i+1);
      }
      qs[0].acquire(threads[0]);
      qs[0].waitForAccess(threads[1]); 
      qs[0].waitForAccess(threads[2]); 
      // Print 6
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0]));    

      qs[1].acquire(threads[3]);
      qs[1].waitForAccess(threads[4]); 
      qs[1].waitForAccess(threads[5]);
      // Print 15
      System.out.println("Effective Priority of Thread " + (3) + ": " + scheduler.getEffectivePriority(threads[3]));    

      qs[0].waitForAccess(threads[3]);
      // Print 21
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0]));  
      
      qs[1].waitForAccess(threads[6]); 
      // Print 22 
      System.out.println("Effective Priority of Thread " + (3) + ": " + scheduler.getEffectivePriority(threads[3]));    
      // Print 28
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0])); 
      
      qs[2].acquire(threads[7]);
      qs[0].waitForAccess(threads[7]);
      // Print 36
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0])); 
      
      qs[2].waitForAccess(threads[8]);
      //Print 45
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0])); 
      Machine.interrupt().restore(intStatus);
  }
    public static void testMoreDonation3() {
      System.out.println("*********TEST MORE DONATION 3*********");
      LotteryScheduler scheduler = (LotteryScheduler)((ThreadedKernel)Kernel.kernel).scheduler;
      ThreadQueue[] qs = new ThreadQueue[3];
      KThread[] threads = new KThread[5];
      boolean intStatus = Machine.interrupt().disable();
      for(int i = 0;i< 3; i++) {
        qs[i] = scheduler.newThreadQueue(true);
      }
      for(int i = 0;i< 5; i++) {
        threads[i] = new KThread(new Donator());
        scheduler.setPriority(threads[i], i+1);
      }
      
      qs[0].acquire(threads[0]);
      qs[1].acquire(threads[0]);
      
      qs[0].waitForAccess(threads[1]); 
      qs[1].waitForAccess(threads[2]); 
      
      // Print 6
      System.out.println("Effective Priority of Thread " + (0) + ": " + scheduler.getEffectivePriority(threads[0])); 
      Machine.interrupt().restore(intStatus);
    }
}
