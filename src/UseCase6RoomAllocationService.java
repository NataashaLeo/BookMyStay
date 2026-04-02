import java.util.*;

/**
 * Use Case 6 - Room Allocation Service
 */


class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    // ✅ FIX: Getters
    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomsInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomsInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> allocations = new HashMap<>();

    public void processBookings(Queue<BookingRequest> queue, RoomsInventory inventory) {

        System.out.println("=== Processing Bookings ===\n");

        while (!queue.isEmpty()) {

            BookingRequest r = queue.poll();

            String type = r.getRoomType();   // ✅ FIX
            String guest = r.getGuestName(); // ✅ FIX

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type);

                allocatedRoomIds.add(roomId);

                allocations.putIfAbsent(type, new HashSet<>());
                allocations.get(type).add(roomId);

                inventory.reduceAvailability(type);

                System.out.println("Booking CONFIRMED for " + guest +
                        " | Room: " + type +
                        " | ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + guest +
                        " | No " + type + " available");
            }
        }
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 2).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(id));

        return id;
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Use Case 6) ===\n");

        Queue<BookingRequest> queue = new LinkedList<>();

        queue.add(new BookingRequest("Alice", "Single Room"));
        queue.add(new BookingRequest("Bob", "Single Room"));
        queue.add(new BookingRequest("Charlie", "Single Room")); // fail
        queue.add(new BookingRequest("David", "Suite Room"));

        RoomsInventory inventory = new RoomsInventory();

        BookingService service = new BookingService();

        service.processBookings(queue, inventory);
    }
}