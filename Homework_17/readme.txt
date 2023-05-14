Design of a Traffic Signal App with Deadlock:
In this Traffic Signal app, there are four signals - North, South, East, and West.
Each signal has a lock associated with it.
A car approaching the intersection must acquire a lock on both signals before proceeding.

Thread 1: Represents a car approaching the intersection from the North, trying to turn left towards the West.
Thread 2: Represents a car approaching the intersection from the South, trying to go straight towards the North.
Thread 3: Represents a car approaching the intersection from the East, trying to go straight towards the West.
Thread 4: Represents a car approaching the intersection from the West, trying to turn right towards the North.

After starting all threads, the main thread waits for all threads to finish using the join() method.
However, in case of a deadlock, the join() method would not return and the program would hang indefinitely.
Deadlock can occur when multiple threads acquire locks in different orders, and end up waiting for
each other to release locks.

To avoid this situation, the code uses a while loop that periodically checks whether each thread is still alive.
The loop sleeps for 1 second and increments a counter each time it executes.
If the counter exceeds a threshold of 10 (indicating that the program has been waiting for more than 10 seconds),
the code prints a message indicating that a deadlock has been detected and terminates the program
using the System.exit() method with an exit status of 0.

The TrafficSignal_Deadlock_Safe class is deadlock-free. The locks are acquired and released in a consistent and
predetermined order, preventing any circular wait and potential deadlocks.
The acquireLocks method acquires the locks in the locks array in a specific order.
It first acquires lock1 and lock2 locks and then acquires all other locks in the array.
The additional locks are acquired temporarily but immediately released using lock.unlock() to avoid potential deadlocks.
The releaseLocks method releases the locks in the reverse order of acquisition.
It releases lock1 and lock2 locks and skips the release of other locks in the array since they were acquired
temporarily and immediately released in the acquireLocks method.

By ensuring a consistent order of lock acquisition and release,
the code guarantees that there won't be any circular wait and eliminates the possibility of deadlocks.