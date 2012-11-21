package nachos.threads;
import nachos.ag.BoatGrader;

public class Boat
{
    static BoatGrader bg;
    static enum Location {OAHU, MOLOKAI};
    static Lock lock = new Lock();
    static Condition condition = new Condition(lock);
    static Semaphore simOver = new Semaphore(0);
    static Alarm alarm = new Alarm();
    static int childrenOnOahu = 0;
    static int childrenOnMolokai = 0;
    static int adultsOnMolokai = 0;
    static int adultsOnOahu = 0;
    static int childrenOnBoat = 0;
    static int lastReportedChildrenOnOahu = 0;
    static int lastReportedChildrenOnMolokai = 0;
    static int lastReportedAdultsOnOahu = 0;
    static Location boatLocation = Location.OAHU;

    
    public static void selfTest()
    {
	BoatGrader b = new BoatGrader();
	
//	System.out.println("\n ***Testing Boats with only 2 children***");
//	begin(0, 2, b);

//	System.out.println("\n ***Testing Boats with 2 children, 1 adult***");
//	begin(1, 2, b);

	System.out.println("\n ***Testing Boats with 102 children, 128 adults***");
	begin(128, 101, b);
	System.out.println("Adults on Molokai: "+adultsOnMolokai);
	System.out.println("Children on Molokai: "+childrenOnMolokai);
    }

    public static void begin( int adults, int children, BoatGrader b )
    {
	// Store the externally generated autograder in a class
	// variable to be accessible by children.
	bg = b;
	// Instantiate global variables here
	
	// Create threads here. See section 3.4 of the Nachos for Java
	// Walkthrough linked from the projects page.

	Runnable r1 = new Runnable() {
	    public void run() {
	    	AdultItinerary();
	    	}
	    };
	Runnable r2 = new Runnable() {
		public void run() {
			ChildItinerary();
			}
		};
        for (int i = 0; i < adults; i++){
        	new KThread(r1).setName("Adult Thread " + i).fork();
        }
        for (int j = 0; j < children; j++){
        	new KThread(r2).setName("Child Thread " + j).fork();
        }
        while (adultsOnMolokai != adults || childrenOnMolokai != children) { // if simulation hasn't ended
        	simOver.P(); //wait for simulation to communicate that it has finished
        }
    }
    

    static void AdultItinerary()
    {
	/* This is where you should put your solutions. Make calls
	   to the BoatGrader to show that it is synchronized. For
	   example:
	       bg.AdultRowToMolokai();
	   indicates that an adult has rowed the boat across to Molokai
	*/
    	adultsOnOahu++;
    	lastReportedAdultsOnOahu = adultsOnOahu;
    	lock.acquire();
    	Location currentLocation = Location.OAHU;
    	while (true) {
    		/* if the adult is in Oahu, the boat is in Oahu and available,
    		 * and there is at least one child in Molokai to row the boat back,
    		 * row to Molokai
    		 * if not, sleep and wait for the above conditions to be true
    		 */
    		if (currentLocation == Location.OAHU && boatLocation == Location.OAHU && childrenOnBoat == 0 && lastReportedChildrenOnMolokai > 0) {
    			adultsOnOahu--;
    			lastReportedChildrenOnOahu = childrenOnOahu;
    			lastReportedAdultsOnOahu = adultsOnOahu;
    			bg.AdultRowToMolokai();
    			adultsOnMolokai++;
    			boatLocation = Location.MOLOKAI;
    			currentLocation = Location.MOLOKAI;
    		}
    		condition.wake();
    		condition.sleep();
    	}
    }

    static void ChildItinerary()
    {
    	childrenOnOahu++;
    	lastReportedChildrenOnOahu = childrenOnOahu;
    	lock.acquire();
    	Location currentLocation = Location.OAHU;
    	while (true) {
    		/* if child is in Oahu and boat is in Oahu, get boat fully occupied by 2 children
    		 * then go to Molokai
    		 */
    		if (currentLocation == Location.OAHU && boatLocation == Location.OAHU) {
    			if (childrenOnBoat == 0) {
    				childrenOnOahu--;
    				childrenOnBoat++;
    				while (boatLocation == Location.OAHU) { //wait for another child to get on the boat
    					condition.wake();
    					condition.sleep();
    				}
    				lastReportedChildrenOnOahu = childrenOnOahu;
    				lastReportedAdultsOnOahu = adultsOnOahu;
    				bg.ChildRideToMolokai();
    				childrenOnMolokai++;
    				currentLocation = Location.MOLOKAI;
    				childrenOnBoat--;
    				if (lastReportedChildrenOnOahu == 0 && lastReportedAdultsOnOahu == 0) {
    					bg.ChildRowToOahu(); //row back to make sure there's really no one
    					alarm.waitUntil(500); //wait to see if anyone appears
    					lastReportedAdultsOnOahu = adultsOnOahu;
    					lastReportedChildrenOnOahu = childrenOnOahu;
    					bg.ChildRowToMolokai();
    					simOver.V(); //tell parent thread that simulation is done (might be wrong, but parent will know)
    				}
    			}
    			else if (childrenOnBoat == 1) {
    				childrenOnOahu--;
    				childrenOnBoat++;
    				bg.ChildRowToMolokai();
        			childrenOnMolokai++;
        			currentLocation = Location.MOLOKAI;
    				boatLocation = Location.MOLOKAI;
    				childrenOnBoat--;
    			}
    		}
    		/* if child is in Molokai and boat is in Molokai, and there are still people in Oahu
    		 * row the boat to Oahu
    		 */
    		else if (currentLocation == Location.MOLOKAI && boatLocation == Location.MOLOKAI && childrenOnBoat == 0 && (lastReportedChildrenOnOahu > 0 || lastReportedAdultsOnOahu > 0)) {
    			childrenOnMolokai--;
    			lastReportedChildrenOnMolokai = childrenOnMolokai;
    			bg.ChildRowToOahu();
    			childrenOnOahu++;
    			boatLocation = Location.OAHU;
    			currentLocation = Location.OAHU;
    		}
    		condition.wake();
    		condition.sleep();
    	}
    }

    static void SampleItinerary()
    {
	// Please note that this isn't a valid solution (you can't fit
	// all of them on the boat). Please also note that you may not
	// have a single thread calculate a solution and then just play
	// it back at the autograder -- you will be caught.
	System.out.println("\n ***Everyone piles on the boat and goes to Molokai***");
	bg.AdultRowToMolokai();
	bg.ChildRideToMolokai();
	bg.AdultRideToMolokai();
	bg.ChildRideToMolokai();
    }
    
}
