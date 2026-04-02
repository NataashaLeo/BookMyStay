import java.io.*;
import java.util.*;

/**
 * Use Case 12 - Data Persistence & System Recovery
 * (Reuses existing Reservation class)
 */

// Wrapper class to hold system state
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> reservations;

    public SystemState(Map<String, Integer> inventory, List<Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // SAVE DATA
    public void saveState(Map<String, Integer> inventory, List<Reservation> reservations) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            SystemState state = new SystemState(inventory, reservations);
            oos.writeObject(state);

            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving system state.");
        }
    }

    // LOAD DATA
    public SystemState loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();

            System.out.println("✅ System state restored successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("❌ Error loading system state. Starting fresh.");
        }

        return null;
    }
}

// Main Class
public class UseCase12PersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Try to load existing state
        SystemState state = service.loadState();

        Map<String, Integer> inventory;
        List<Reservation> reservations;

        if (state != null) {
            inventory = state.inventory;
            reservations = state.reservations;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);

            reservations = new ArrayList<>();
        }

        // Simulate booking (using your existing Reservation class)
        Reservation r1 = new Reservation("John", "Deluxe");
        Reservation r2 = new Reservation("Alice", "Standard");

        reservations.add(r1);
        reservations.add(r2);

        // Update inventory
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);
        inventory.put("Standard", inventory.get("Standard") - 1);

        // Display current state
        System.out.println("\n--- Current Bookings ---");
        for (Reservation r : reservations) {
            System.out.println(r.getGuestName() + " → " + r.getRoomType());
        }

        System.out.println("\n--- Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " → " + inventory.get(type));
        }

        // Save state before shutdown
        service.saveState(inventory, reservations);
    }
}