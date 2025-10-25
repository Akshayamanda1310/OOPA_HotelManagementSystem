import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class BookingService {

    public static DeluxeRoom[] deluxeRooms = new DeluxeRoom[10];
    public static SuiteRoom[] suiteRooms = new SuiteRoom[15];
    public static StandardRoom[] standardRooms = new StandardRoom[25];
    private static Booking[] bookings = new Booking[1000];
    private static int bookingCount = 0;

    static {
        for (int i = 0; i < deluxeRooms.length; i++)
            deluxeRooms[i] = new DeluxeRoom("D" + (i + 1), 5000.0);
        for (int i = 0; i < suiteRooms.length; i++)
            suiteRooms[i] = new SuiteRoom("SU" + (i + 1), 8000.0);
        for (int i = 0; i < standardRooms.length; i++)
            standardRooms[i] = new StandardRoom("ST" + (i + 1), 3000.0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hotel Management System ");
        int cont = 1;
        while (cont == 1) {
            System.out.println("\n1) Book Room\n2) Cancel Booking\n3) Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> handleBooking(sc);
                case 2 -> handleCancellation(sc);
                case 3 -> cont = 0;
            }
        }
        sc.close();
    }

    private static void handleBooking(Scanner sc) {
        System.out.println("\nChoose room type: 1) Standard  2) Deluxe  3) Suite");
        int typeChoice = sc.nextInt();
        RoomType selectedType = switch (typeChoice) {
            case 2 -> RoomType.DELUXE;
            case 3 -> RoomType.SUITE;
            default -> RoomType.STANDARD;
        };

        sc.nextLine(); // flush newline
        System.out.print("Guest Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("ID Proof: ");
        String idProof = sc.nextLine();

        System.out.println("Enter check-in (yyyy mm dd hh mm):");
        LocalDateTime in = LocalDateTime.of(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        System.out.println("Enter check-out (yyyy mm dd hh mm):");
        LocalDateTime out = LocalDateTime.of(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());

        if (!out.isAfter(in)) {
            System.out.println("Invalid: check-out must be after check-in");
            return;
        }

        Room room = findFirstAvailableRoom(selectedType);
        if (room == null) {
            System.out.println("No rooms available!");
            return;
        }

        Guest guest = new Guest(name, phone, idProof, in);
        guest.setLeavingTime(out);

        double total = calculateAmount(room.getBasePrice(), in, out);
        String id = UUID.randomUUID().toString().substring(0, 8);

        Booking booking = new Booking(id, room.getRoomId(), selectedType, guest, in, out, total, BookingStatus.ACTIVE);
        bookings[bookingCount++] = booking;
        room.setOccupied(true);

        Duration stay = Duration.between(in, out);
        System.out.println("\n✅ Booking confirmed!");
        System.out.println("Booking ID: " + id);
        System.out.println("Room: " + room.getRoomId());
        System.out.println("Stay: " + stay.toDays() + " days");
        System.out.println("Amount: ₹" + total);
    }

    private static void handleCancellation(Scanner sc) {
        System.out.print("Enter booking ID: ");
        String id = sc.next();
        for (int i = 0; i < bookingCount; i++) {
            Booking b = bookings[i];
            if (b != null && b.getBookingId().equals(id)) {
                if (b.getStatus() == BookingStatus.CANCELLED) {
                    System.out.println("Already cancelled.");
                    return;
                }
                b.setStatus(BookingStatus.CANCELLED);
                Room room = findRoomById(b.getRoomId(), b.getRoomType());
                if (room != null) room.setOccupied(false);
                System.out.println("✅ Booking cancelled!");
                return;
            }
        }
        System.out.println("Booking ID not found.");
    }

    private static Room findFirstAvailableRoom(RoomType type) {
        return switch (type) {
            case DELUXE -> Arrays.stream(deluxeRooms).filter(Room::isAvailable).findFirst().orElse(null);
            case SUITE -> Arrays.stream(suiteRooms).filter(Room::isAvailable).findFirst().orElse(null);
            default -> Arrays.stream(standardRooms).filter(Room::isAvailable).findFirst().orElse(null);
        };
    }

    private static Room findRoomById(String id, RoomType type) {
        return switch (type) {
            case DELUXE -> Arrays.stream(deluxeRooms).filter(r -> r.getRoomId().equals(id)).findFirst().orElse(null);
            case SUITE -> Arrays.stream(suiteRooms).filter(r -> r.getRoomId().equals(id)).findFirst().orElse(null);
            default -> Arrays.stream(standardRooms).filter(r -> r.getRoomId().equals(id)).findFirst().orElse(null);
        };
    }

    private static double calculateAmount(double base, LocalDateTime in, LocalDateTime out) {
        long nights = ChronoUnit.DAYS.between(in.toLocalDate(), out.toLocalDate());
        if (nights == 0) nights = 1;
        double total = 0;
        LocalDate date = in.toLocalDate();
        for (int i = 0; i < nights; i++) {
            double price = base;
            if (isWeekend(date)) price *= 1.2;
            if (isPeak(date)) price *= 1.5;
            total += price;
            date = date.plusDays(1);
        }
        return total;
    }

    private static boolean isWeekend(LocalDate d) {
        DayOfWeek day = d.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private static boolean isPeak(LocalDate d) {
        LocalDate start1 = LocalDate.of(d.getYear(), 6, 1);
        LocalDate end1 = LocalDate.of(d.getYear(), 7, 31);
        LocalDate start2 = LocalDate.of(d.getYear(), 12, 15);
        LocalDate end2 = LocalDate.of(d.getYear() + 1, 1, 5);
        return (!d.isBefore(start1) && !d.isAfter(end1)) ||
               (!d.isBefore(start2) && !d.isAfter(end2));
    }
}
