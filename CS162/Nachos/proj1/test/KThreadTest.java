package nachos.proj1.test;

import nachos.machine.Machine;
import nachos.threads.KThread;
import nachos.threads.Lock;
import nachos.threads.ThreadedKernel;

public class KThreadTest {
	public KThreadTest() {
	}
	
	// output:
	// Done
	// [Running] Thread-One
	// [Running] Thread-Two
	public static void testOne() {
		KThread threadOne = new KThread(new ThreadRun("Thread-One"));
		KThread threadTwo = new KThread(new ThreadRun("Thread-Two"));
		threadOne.fork();
		threadTwo.fork();
//		KThread.yield();
		
		Machine.interrupt().disable();
		ThreadedKernel.scheduler.setPriority(threadOne, 2);
		Machine.interrupt().enable();
		
		threadOne.join();
		threadTwo.join();
		System.out.println("testing");
	}
	
    static class ThreadRun implements Runnable{
    	private String name;
    	public ThreadRun(String name) {
    		this.name = name;
    	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("[Running] " + name);
		}
    }
    
	/*
	 * output
	 * [Running] Thread-One
	 * Done
	 */
    public static void testTwo() {
		KThread threadOne = new KThread(new ThreadRun("Thread-One"));
		ThreadedKernel.scheduler.setPriority(threadOne, 4);
		threadOne.join();
		threadOne.fork();
		System.out.println("Done");
    }

	public static void testScheduler() {
		// TODO Auto-generated method stub
		KThread threadA = new KThread(new SchedulerRunA("Scheduler-A"));
		
		Machine.interrupt().disable();
		ThreadedKernel.scheduler.setPriority(threadA, 5);
		Machine.interrupt().enable();
		
		threadA.setName("ntdo-A").fork();
		
		lock.acquire();
		KThread.yield();
		lock.release();
		
		Machine.interrupt().disable();
		System.out.println(ThreadedKernel.scheduler.getEffectivePriority(KThread.currentThread()));
		System.out.println(ThreadedKernel.scheduler.getPriority(KThread.currentThread()));
		
		System.out.println(ThreadedKernel.scheduler.getEffectivePriority(threadA));
		System.out.println(ThreadedKernel.scheduler.getPriority(threadA));
		Machine.interrupt().enable();
		
		threadA.join();
	}
	
	static Lock lock = new Lock();
	
	static class SchedulerRunA implements Runnable {
		
		private String name;
		public SchedulerRunA(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			lock.acquire();
			System.out.println(name + " before sleep().");
			KThread.yield();
			System.out.println(name + " after sleep().");
			lock.release();
		}
		
	}
	
	static class SchedulerRunB implements Runnable {
		
		private String name;
		public SchedulerRunB(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			lock.acquire();
			System.out.println(name + " running");
			lock.release();
		}
		
	}
	
	static class SchedulerRunC implements Runnable {
		private String name;
		public SchedulerRunC(String name) {
			this.name = name;
		}
		
		public void run() {
		}
	}
}
