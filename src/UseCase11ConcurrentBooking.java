import java.util.*;

/**
 * Use Case 11 - Concurrent Booking Simulation (Thread Safety)
 * (Reuses existing BookingRequest class)
 */

class ConcurrentBookingProcessor {

    // Shared Queue
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Shared Inventory
    private Map<String, Integer> inventory = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Add request to queue
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);

        System.out.println("Request Added → "
                + request.getGuestName() + " (" + request.getRoomType() + ")");
    }

    // Process bookings
    public void processBooking() {

        while (true) {

            BookingRequest request;

            // ✅ Critical Section - Queue
            synchronized (this) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            // ✅ Critical Section - Inventory
            synchronized (this) {

                int available = inventory.getOrDefault(request.getRoomType(), 0);

                if (available > 0) {
                    inventory.put(request.getRoomType(), available - 1);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " SUCCESS → "
                                    + request.getGuestName()
                                    + " (" + request.getRoomType() + ")"
                    );

                } else {
                    System.out.println(
                            Thread.currentThread().getName()
                                    + " FAILED → No "
                                    + request.getRoomType()
                                    + " for "
                                    + request.getGuestName()
                    );
                }
            }

            // simulate delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " → " + inventory.get(type));
        }
    }
}

// Thread Class
class BookingThread extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingThread(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }

    public void run() {
        processor.processBooking();
    }
}

// Main Class
public class UseCase11ConcurrentBooking {

    public static void main(String[] args) {

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // ✅ Using YOUR existing BookingRequest constructor
        processor.addRequest(new BookingRequest("John", "Deluxe"));
        processor.addRequest(new BookingRequest("Alice", "Deluxe")); // conflict
        processor.addRequest(new BookingRequest("Bob", "Suite"));
        processor.addRequest(new BookingRequest("Emma", "Standard"));
        processor.addRequest(new BookingRequest("Raj", "Standard"));
        processor.addRequest(new BookingRequest("Mike", "Standard")); // conflict

        // Threads
        Thread t1 = new BookingThread(processor, "Guest-1");
        Thread t2 = new BookingThread(processor, "Guest-2");
        Thread t3 = new BookingThread(processor, "Guest-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processor.displayInventory();
    }
}