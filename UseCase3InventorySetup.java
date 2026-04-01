import java.util.HashMap;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management
 * using HashMap as a single source of truth.
 *
 * @author Roxx
 * @version 3.1
 */

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("=== Room Inventory ===");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " → Available: " + inventory.get(roomType));
        }
    }
}

// Main Class (ENTRY POINT)
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Book My Stay App - Inventory v3.1");
        System.out.println("====================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example: update availability
        System.out.println("\nUpdating availability...\n");

        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}