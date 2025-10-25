// Handle booking form submission
document.addEventListener("DOMContentLoaded", () => {
  const bookingForm = document.getElementById("bookingForm");
  const cancelForm = document.getElementById("cancelForm");

  if (bookingForm) {
    bookingForm.addEventListener("submit", (e) => {
      e.preventDefault();

      // Capture form data
      const data = {
        name: document.getElementById("guestName").value,
        phone: document.getElementById("phone").value,
        idProof: document.getElementById("idProof").value,
        roomType: document.getElementById("roomType").value,
        checkIn: document.getElementById("checkIn").value,
        checkOut: document.getElementById("checkOut").value
      };

      console.log("Booking Submitted:", data);
      alert("Booking confirmed! (Mock response)");
      window.location.href = "confirmation.html";
    });
  }

  if (cancelForm) {
    cancelForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const bookingId = document.getElementById("bookingId").value;
      console.log("Cancel Booking:", bookingId);
      alert(`Booking ${bookingId} cancelled (Mock response)`);
    });
  }
});
