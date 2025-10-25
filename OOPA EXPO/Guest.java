import java.time.LocalDateTime;

class Guest {
    private String name;
    private String phone;
    private String idProof;
    private LocalDateTime arrivalTime;
    private LocalDateTime leavingTime;

    public Guest(String name, String phone, String idProof, LocalDateTime arrivalTime) {
        this.name = name;
        this.phone = phone;
        this.idProof = idProof;
        this.arrivalTime = arrivalTime;
    }

    public void setLeavingTime(LocalDateTime leavingTime) {
        this.leavingTime = leavingTime;
    }
}
