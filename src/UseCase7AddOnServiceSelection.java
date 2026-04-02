import java.util.*;

/**
 * Use Case 7 - Add-On Service Selection
 * (Compatible with your existing Reservation class)
 */

// Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager
class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;

        for (AddOnService s : getServices(reservationId)) {
            total += s.getCost();
        }

        return total;
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nAdd-On Services for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // ✅ Store ID separately (IMPORTANT FIX)
        String reservationId = "R001";

        // Your existing Reservation (only 2 parameters)
        Reservation r1 = new Reservation(reservationId, "Alice");

        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1200);
        AddOnService pickup = new AddOnService("Airport Pickup", 800);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, pickup);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + r1.getGuestName());

        manager.displayServices(reservationId);
    }
}