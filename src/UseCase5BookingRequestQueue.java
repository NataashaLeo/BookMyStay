import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request handling using FIFO Queue
 * (First-Come-First-Served principle)
 *
 * @author Roxx
 * @version 5.0
 */

// Reservation class (represents booking request)
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}

// Booking Queue class
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\n=== Booking Requests (FIFO Order) ===");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Queue v5.0) ===\n");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.viewRequests();

        System.out.println("\nAll requests stored in arrival order. No booking processed yet.");
    }
}