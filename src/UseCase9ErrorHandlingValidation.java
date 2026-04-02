import java.util.*;

/**
 * Use Case 9 - Error Handling & Validation
 * (Reuses existing Reservation class)
 */

// ✅ Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ✅ Validator Class
class BookingValidator {

    // Allowed room types
    private static final List<String> validRoomTypes =
            Arrays.asList("Standard", "Deluxe", "Suite");

    // Simulated inventory
    private static Map<String, Integer> inventory = new HashMap<>();

    static {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Validate booking request
    public static void validate(String roomType) throws InvalidBookingException {

        // 🔹 Check valid room type
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected: " + roomType);
        }

        // 🔹 Check availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    // Reduce inventory AFTER validation
    public static void allocateRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    // Display inventory
    public static void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " → " + inventory.get(type));
        }
    }
}

// ✅ Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        // Simulated booking requests
        String[][] requests = {
                {"R001", "Alice", "Deluxe"},
                {"R002", "Bob", "Suite"},
                {"R003", "Charlie", "Premium"}, // ❌ invalid
                {"R004", "David", "Deluxe"}     // ❌ may be unavailable
        };

        for (String[] req : requests) {

            String id = req[0];
            String name = req[1];
            String roomType = req[2];

            try {
                // ✅ Validation (Fail Fast)
                BookingValidator.validate(roomType);

                // ✅ Create Reservation (using existing constructor)
                Reservation reservation = new Reservation(id, name);

                // ✅ Allocate room AFTER validation
                BookingValidator.allocateRoom(roomType);

                System.out.println("Booking Successful → " + id + " | " + name + " | " + roomType);

            } catch (InvalidBookingException e) {
                // ✅ Graceful failure
                System.out.println("Booking Failed → " + id + " | Reason: " + e.getMessage());
            }
        }

        // Show final inventory
        BookingValidator.displayInventory();
    }
}