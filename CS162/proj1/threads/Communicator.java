package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {

    /* instance variables */
    private int word;
    private boolean hasWord;
    private Lock lock;
    private Condition2 speaker;
    private Condition2 listener;

    /**
     *  Constructor
     */
    public Communicator() {
        lock = new Lock();
        speaker = new Condition2( lock );
        listener = new Condition2( lock );
        hasWord = false;
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
        lock.acquire();
        while( hasWord ) {        // if some speaker has sent a message
            speaker.sleep();      // then go to sleep
        }
        this.word = word;         // put word into shared heap
        hasWord = true;           // now has word and ready for listener to take it
        listener.wakeAll();       
        speaker.sleep();          // make sure the current speaker wait until some listener receives message
        lock.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
        lock.acquire();
        while( !hasWord ) {         // if no speaker has sent message
            listener.sleep();       // then go to sleep
        }
        hasWord = false;            // now ready to return the word
        int word = this.word;       // carefully save shared word into temporary var to return
        speaker.wakeAll();
        lock.release();
        return word;
    }

    public static void selfTest() {
        System.out.println(" COMMUNICATOR TESTS ");

        /* suite 1: one speaker and one listener: speaker calls first */
        System.out.println( "----- Suite 1 -----" );
        Communicator comm1 = new Communicator();
        KThread t1 = new KThread( new Speaker( "speaker 1", comm1, 1 ) );
        KThread t2 = new KThread( new Listener( "listener 1", comm1 ) );

        t1.fork();
        t2.fork();

        t1.join();
        t2.join();

        KThread.yield();

        /* suite 2: one speaker and one listener: listener calls first */
        System.out.println( "----- Suite 2 -----" );
        Communicator comm2 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm2, 1 ) );
        t2 = new KThread( new Listener( "listener 1", comm2 ) );

        t2.fork();
        t1.fork();

        t1.join();
        t2.join();

        KThread.yield();

        /* suite 3: more speakers and more listeners: all speakers first */
        System.out.println( "----- Suite 3 -----" );
        Communicator comm3 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm3, 1 ) );
        t2 = new KThread( new Speaker( "speaker 2", comm3, 2 ) );
        KThread t3 = new KThread( new Speaker( "speaker 3", comm3, 3 ) );
        KThread t4 = new KThread( new Listener( "listener 1", comm3 ) );
        KThread t5 = new KThread( new Listener( "listener 2", comm3 ) );
        KThread t6 = new KThread( new Listener( "listener 3", comm3 ) );

        t1.fork(); t2.fork(); t3.fork();
        t4.fork(); t5.fork(); t6.fork();

        t1.join(); t2.join(); t3.join();
        t4.join(); t5.join(); t6.join();

        KThread.yield();

        /* suite 4: more speakers and more listeners: all listeners first */
        System.out.println( "----- Suite 4 -----" );
        Communicator comm4 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm4, 1 ) );
        t2 = new KThread( new Speaker( "speaker 2", comm4, 2 ) );
        t3 = new KThread( new Speaker( "speaker 3", comm4, 3 ) );
        t4 = new KThread( new Listener( "listener 1", comm4 ) );
        t5 = new KThread( new Listener( "listener 2", comm4 ) );
        t6 = new KThread( new Listener( "listener 3", comm4 ) );

        t4.fork(); t5.fork(); t6.fork();
        t1.fork(); t2.fork(); t3.fork();

        t1.join(); t2.join(); t3.join();
        t4.join(); t5.join(); t6.join();

        KThread.yield();
       
        /* suite 5: more speakers and more listeners: mixed */
        System.out.println( "----- Suite 5 -----" );
        Communicator comm5 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm5, 1 ) );
        t2 = new KThread( new Speaker( "speaker 2", comm5, 2 ) );
        t3 = new KThread( new Speaker( "speaker 3", comm5, 3 ) );
        t4 = new KThread( new Listener( "listener 1", comm5 ) );
        t5 = new KThread( new Listener( "listener 2", comm5 ) );
        t6 = new KThread( new Listener( "listener 3", comm5 ) );

        t1.fork(); t4.fork(); 
        t2.fork(); t5.fork(); 
        t3.fork(); t6.fork();

        t1.join(); t2.join(); t3.join();
        t4.join(); t5.join(); t6.join();

        KThread.yield();
       
        /* suite 6: more speakers and more listeners: mixed v2 */
        System.out.println( "----- Suite 6 -----" );
        Communicator comm6 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm6, 1 ) );
        t2 = new KThread( new Speaker( "speaker 2", comm6, 2 ) );
        t3 = new KThread( new Speaker( "speaker 3", comm6, 3 ) );
        t4 = new KThread( new Listener( "listener 1", comm6 ) );
        t5 = new KThread( new Listener( "listener 2", comm6 ) );
        t6 = new KThread( new Listener( "listener 3", comm6 ) );

        t1.fork(); t4.fork(); t5.fork();
        t2.fork(); t6.fork(); t3.fork();

        t1.join(); t2.join(); t3.join();
        t4.join(); t5.join(); t6.join();

        KThread.yield();

        /* suite 7: more speakers and more listeners: mixed v3 */
        System.out.println( "----- Suite 7 -----" );
        Communicator comm7 = new Communicator();
        t1 = new KThread( new Speaker( "speaker 1", comm7, 1 ) );
        t2 = new KThread( new Speaker( "speaker 2", comm7, 2 ) );
        t3 = new KThread( new Speaker( "speaker 3", comm7, 3 ) );
        t4 = new KThread( new Listener( "listener 1", comm7 ) );
        t5 = new KThread( new Listener( "listener 2", comm7 ) );
        t6 = new KThread( new Listener( "listener 3", comm7 ) );

        t4.fork(); t1.fork(); t2.fork();
        t5.fork(); t3.fork(); t6.fork();

        t1.join(); t2.join(); t3.join();
        t4.join(); t5.join(); t6.join();

        KThread.yield();

        
    }

    private static class Speaker implements Runnable {
        private String name;        // name of this speaker
        private Communicator comm;  // the communicator object
        private int word;           // the message to speak

        Speaker( String name, Communicator comm, int word ) {
            this.name = name;
            this.comm = comm;
            this.word = word;
        }

        public void run() {
            System.out.println( name + " => speaking " + word );
            comm.speak( word );
            System.out.println( name + " => spoke " + word );
        }
    }

    private static class Listener implements Runnable {
        private String name;        // name of this listener
        private Communicator comm;  // the communicator

        Listener( String name, Communicator comm ) {
            this.name = name;
            this.comm = comm;
        }

        public void run() {
            System.out.println( name + " => listening" );
            int word = comm.listen();
            System.out.println( name + " => listened " + word );
        }
    }
}
