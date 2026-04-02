import java.util.*;

/**
 * Use Case 10 - Booking Cancellation & Inventory Rollback
 * (Reuses existing Reservation class)
 */

// ✅ Cancellation Service
class CancellationService {

    // Track active reservations
    private Set<String> activeReservations = new HashSet<>();

    // Inventory (roomType → count)
    private Map<String, Integer> inventory = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> releasedRooms = new Stack<>();

    public CancellationService() {
        // Initial inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Simulate booking confirmation
    public void confirmBooking(String reservationId, String roomType) {

        activeReservations.add(reservationId);

        // reduce inventory
        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking Confirmed → " + reservationId + " | " + roomType);
    }

    // Cancel booking with rollback
    public void cancelBooking(String reservationId, String roomType) {

        // ✅ Validation
        if (!activeReservations.contains(reservationId)) {
            System.out.println("Cancellation Failed → " + reservationId + " not found or already cancelled");
            return;
        }

        // ✅ Remove reservation
        activeReservations.remove(reservationId);

        // ✅ Rollback (LIFO)
        releasedRooms.push(reservationId);

        // ✅ Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        System.out.println("Booking Cancelled → " + reservationId + " | " + roomType);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " → " + inventory.get(type));
        }
    }

    // Show rollback stack
    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + releasedRooms);
    }
}

// ✅ Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        // Simulated bookings
        String r1 = "R001";
        String r2 = "R002";

        // Confirm bookings
        service.confirmBooking(r1, "Deluxe");
        service.confirmBooking(r2, "Suite");

        // Cancel booking (valid)
        service.cancelBooking(r1, "Deluxe");

        // Cancel again (invalid)
        service.cancelBooking(r1, "Deluxe");

        // Show results
        service.displayInventory();
        service.showRollbackStack();
    }
}