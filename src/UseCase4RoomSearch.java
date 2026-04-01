import java.util.HashMap;

// Inventory Class (READ-ONLY access used in search)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // Not available
        inventory.put("Suite Room", 2);
    }

    // Get availability (READ ONLY)
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Get all room types
    public HashMap<String, Integer> getAllRooms() {
        return inventory;
    }
}

// Room class (Domain Model)
class Room {
    String type;
    double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
    }
}

// Search Service (READ ONLY LOGIC)
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("=== Available Rooms ===\n");

        HashMap<String, Integer> rooms = inventory.getAllRooms();

        for (String type : rooms.keySet()) {

            int available = inventory.getAvailability(type);

            // Filter unavailable rooms
            if (available > 0) {

                Room room = createRoom(type);

                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }

    // Create Room object (Domain usage)
    private Room createRoom(String type) {

        if (type.equals("Single Room")) {
            return new Room(type, 2000);
        } else if (type.equals("Double Room")) {
            return new Room(type, 3500);
        } else {
            return new Room(type, 7000);
        }
    }
}

// Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App (Search v4.0) ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (READ ONLY)
        searchService.searchAvailableRooms(inventory);

        System.out.println("Search completed. No changes made to inventory.");
    }
}