package nachos.threads;

import java.util.PriorityQueue;

import nachos.machine.*;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {
	Machine.timer().setInterruptHandler(new Runnable() {
		public void run() { timerInterrupt(); }
	    });
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {
	Lib.assertTrue(Machine.interrupt().disabled());
	WaitingThread waitingThread;
	while ((waitingThread = waitingThreadsQueue.peek()) != null && waitingThread.getWakeTime() <= Machine.timer().getTime()) {
		waitingThread.getWaitingThread().ready();
		waitingThreadsQueue.poll();
	}
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
	long wakeTime = Machine.timer().getTime() + x;
	boolean initStatus = Machine.interrupt().disable();
	WaitingThread currThread = new WaitingThread(KThread.currentThread(), wakeTime);
	waitingThreadsQueue.add(currThread);
	KThread.sleep();
	Machine.interrupt().restore(initStatus);
	}
    
    private PriorityQueue<WaitingThread> waitingThreadsQueue = new PriorityQueue<WaitingThread>();
    
    public static void selfTest() {
    	Alarm alarm = new Alarm();
    	alarm.waitUntil(100000);
    }
    
    private class WaitingThread implements Comparable<WaitingThread> {
    	private KThread thread;
    	private long wakeTime; 
    	
    	public WaitingThread (KThread currentThread, long wakeTime) {
    		this.thread = currentThread;
    		this.wakeTime = wakeTime;
    	}
    	
    	public KThread getWaitingThread() {
    		return this.thread;
    	}
    	
    	public long getWakeTime() {
    		return this.wakeTime;
    	}
    	
		public int compareTo(WaitingThread otherThread) {
			if (this.getWakeTime() >= otherThread.getWakeTime())
				return 1;
			else
				return -1;
		}	
	}
}