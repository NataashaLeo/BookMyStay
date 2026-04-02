import java.util.*;

/**
 * Use Case 8 - Booking History & Reporting
 */

// Booking History
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

// Reporting Service
class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummaryReport(List<Reservation> reservations) {

        double totalRevenue = 0; // not used (since no amount field)

        System.out.println("\n--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + reservations.size());
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();

        // ✅ FIXED: Using correct constructor (2 parameters)
        Reservation r1 = new Reservation("R001", "Alice");
        Reservation r2 = new Reservation("R002", "Bob");
        Reservation r3 = new Reservation("R003", "Charlie");

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        BookingReportService reportService = new BookingReportService();

        List<Reservation> allReservations = bookingHistory.getReservations();

        reportService.displayAllBookings(allReservations);
        reportService.generateSummaryReport(allReservations);
    }
}