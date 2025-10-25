import java.time.LocalDateTime;

class Booking {
    private final String bookingId;
    private final String roomId;
    private final RoomType roomType;
    private final Guest guest;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final double amount;
    private BookingStatus status;

    public Booking(String bookingId, String roomId, RoomType roomType, Guest guest,
                   LocalDateTime checkIn, LocalDateTime checkOut, double amount, BookingStatus status) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.roomType = roomType;
        this.guest = guest;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amount = amount;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public String getRoomId() { return roomId; }
    public RoomType getRoomType() { return roomType; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus s) { this.status = s; }
}

enum BookingStatus { ACTIVE, CANCELLED }
