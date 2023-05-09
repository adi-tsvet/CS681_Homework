Design of Airport Runway Application:

The Airport Runway application manages the take-off and landing of airplanes on a single runway.
The application includes two types of threads: TakeOffThread and LandingThread.
Each thread represents an airplane that wants to either take off or land on the runway.
The application should allow only one plane to land or take off at a time,
as multiple planes on the runway at the same time could lead to a collision.

The original code for the Airport Runway application is not thread-safe because multiple threads can access the
same resources (i.e., runway(Boolean variable free) simultaneously, leading to a race condition.
Two or more airplanes can try to land or take off at the same time, resulting in collisions and other unsafe conditions.
Here, two threads (TakeOffThread and LandingThread) are competing for a single shared resource (i.e., isFree).
When a thread lands or takes off, it sets the isFree flag to false, executes the landing or take-off operation, and sets the flag to true again.
However, if two threads try to land or take off simultaneously, they may both read the flag as true, leading to unsafe conditions.

To make the Airport Runway application thread-safe, we can use a lock to ensure that only one thread
can access the runway at a time.

In this application, we create an instance of AirportRunway and 5 threads.
Each thread represents an airplane trying to either land or take off.
We alternate between creating threads for landing and taking off by checking if i is even or odd.
Finally, we start each thread and join them all to wait for their completion.
