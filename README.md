# 🏨 Hotel Booking Management System (Java + Swing)

Built using **Java** and **Swing**, this project simulates a hotel reception desk with admin login, room booking, allocation, and unique code verification.

---

## 💡 Features

- 🔐 **Admin Login** (only once per session)
- 🛏️ **Room Booking** (Single or Shared room)
- 🎫 **6-digit Unique Code Generator**
- 🧾 **Booking Records** stored in `log.txt`
- 📋 **Search Existing Booking** by Code
- ❌ **Cancel Booking** & release room
- 💻 **Modern GUI with JOptionPane**
- 🧩 Ready for **QR Code Integration** and future JavaFX upgrade

---

## 📂 Project Structure

```
HotelManagement/
├── HotelManagement.java       # Main source file
├── myqr.jpg                   # Optional QR image for future payment
├── bookings.csv                    # Booking log file
```

---

## 🛠️ How to Run

1. Save the code as `HotelManagement.java`
2. Compile:
   ```bash
   javac HotelManagement.java
   ```
3. Run:
   ```bash
   java HotelManagement
   ```

---

## 📝 Sample Booking Log

```
Name: John, Aadhar: 123456789012, Mobile: 9876543210, CheckIN: 2025-06-23, CheckOUT: 2025-06-24, Room: Room101, UniqueCode: 728194
```

---

## 🔧 Future Enhancements

- [ ] QR Code popup for payment (`PaymentPortal()` integration)
- [ ] Upgrade to JavaFX UI
- [ ] Export logs to `.csv` format
- [ ] Room price calculation & billing
- [ ] Search bookings by Mobile Number

---

## 🤝 Author

👨‍💻 **Vipul Meshram**  
🛠 Java | Swing | Backend Logic Design  
🔗 [Vipul Meshram](https://www.linkedin.com/in/vipul-meshram-83645732a)

> This is a beginner-to-intermediate level project aimed at learning real-world problem-solving in Java.

---

## 📃 License

MIT License – Free to use, modify and share.
