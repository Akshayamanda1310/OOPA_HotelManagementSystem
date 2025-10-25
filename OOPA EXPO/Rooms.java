abstract class Room {
    private final String roomId;
    private final String roomType;
    private final double basePrice;
    private boolean occupied;

    public Room(String roomId, String roomType, double basePrice) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.basePrice = basePrice;
        this.occupied = false;
    }

    public String getRoomId() { return roomId; }
    public String getRoomType() { return roomType; }
    public double getBasePrice() { return basePrice; }
    public boolean isAvailable() { return !occupied; }
    public void setOccupied(boolean occ) { this.occupied = occ; }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String id, double basePrice) { super(id, "Deluxe", basePrice); }
}

class SuiteRoom extends Room {
    public SuiteRoom(String id, double basePrice) { super(id, "Suite", basePrice); }
}

class StandardRoom extends Room {
    public StandardRoom(String id, double basePrice) { super(id, "Standard", basePrice); }
}

enum RoomType { STANDARD, DELUXE, SUITE }
