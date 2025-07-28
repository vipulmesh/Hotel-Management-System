package HotelManagement.admin;

import HotelManagement.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Admin {
    int[] roomStatus = new int[60];
    Scanner sc = new Scanner(System.in);
    String Name, PName, Name2, Name3, ADDHAR, MobileNo, CheckIN, CheckOUT;
    StringBuilder uniqueCodeStr = new StringBuilder();
    private final String AUserID = "vipulmeshram";
    private final int APassword = 1234;

    public boolean LoginPortalAdmin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("🕤 User ID:"), gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("🔐 Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "🔒 Admin Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String enteredUser = userField.getText();
            int enteredPass = Integer.parseInt(new String(passField.getPassword()));
            if (enteredUser.equals(AUserID) && enteredPass == APassword) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "❌ Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public String allocateRoom() {
        for (int i = 0; i < 60; i++) {
            if (roomStatus[i] == 0) {
                roomStatus[i] = 1;
                return "Room" + (101 + i);
            }
        }
        return "No rooms available";
    }

    public void releaseRoom(String roomNumber) {
        int roomNum = Integer.parseInt(roomNumber.replace("Room", ""));
        int index = roomNum - 101;
        if (index >= 0 && index < 60) {
            roomStatus[index] = 0;
        }
    }

    public void NewBooking() {
        String[] options = {"🛏️ Single Room (1 Person)", "👨‍👩‍👧 Single Room (2P + Minor)"};
        int Rchoose = JOptionPane.showOptionDialog(null,
                "<html><h2>Select Room Type</h2></html>",
                "Room Booking",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (Rchoose == 0) {
            Name = JOptionPane.showInputDialog("Enter Your Name:");
            ADDHAR = ValidationUtils.getValidatedAadhaar();
            MobileNo = ValidationUtils.getValidatedMobile();
            String[] dates = ValidationUtils.getValidatedDates();
            CheckIN = dates[0];
            CheckOUT = dates[1];

            String allocatedRoom = allocateRoom();
            uniqueCode();
            JOptionPane.showMessageDialog(null,
                    "<html><h2 style='color:green;'>Room Allocated: " + allocatedRoom +
                            "</h2><p>Unique Code: <b>" + uniqueCodeStr + "</b></p></html>",
                    "Booking Success ✅", JOptionPane.INFORMATION_MESSAGE);
            FileHandling_SingleRoom(allocatedRoom);
            logBookingToCSV(Name, ADDHAR, MobileNo, CheckIN, CheckOUT, Integer.parseInt(allocatedRoom.replace("Room", "")));
        } else if (Rchoose == 1) {
            PName = JOptionPane.showInputDialog("Enter Primary Person Name:");
            Name2 = JOptionPane.showInputDialog("Enter 2nd Person Name:");
            Name3 = JOptionPane.showInputDialog("Enter Minor Name:");
            ADDHAR = ValidationUtils.getValidatedAadhaar();
            MobileNo = ValidationUtils.getValidatedMobile();
            String[] dates = ValidationUtils.getValidatedDates();
            CheckIN = dates[0];
            CheckOUT = dates[1];

            String allocatedRoom = allocateRoom();
            uniqueCode();
            JOptionPane.showMessageDialog(null,
                    "<html><h2 style='color:green;'>Room Allocated: " + allocatedRoom +
                            "</h2><p>Unique Code: <b>" + uniqueCodeStr + "</b></p></html>",
                    "Booking Success ✅", JOptionPane.INFORMATION_MESSAGE);
            FileHandling_SingleRoom2(allocatedRoom);
            logBookingToCSV(PName + " & " + Name2, ADDHAR, MobileNo, CheckIN, CheckOUT, Integer.parseInt(allocatedRoom.replace("Room", "")));
        }
    }

    public void uniqueCode() {
        Random rand = new Random();
        uniqueCodeStr.setLength(0);
        for (int i = 0; i < 6; i++) {
            uniqueCodeStr.append(rand.nextInt(10));
        }
    }

    public void FileHandling_SingleRoom(String allocatedRoom) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
            bw.write("Name: " + Name + ", Aadhar: " + ADDHAR + ", Mobile: " + MobileNo +
                    ", CheckIN: " + CheckIN + ", CheckOUT: " + CheckOUT +
                    ", Room: " + allocatedRoom + ", UniqueCode: " + uniqueCodeStr);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error saving booking.");
        }
    }

    public void FileHandling_SingleRoom2(String allocatedRoom) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
            bw.write("Name: " + PName + ", 2nd Name: " + Name2 + ", Minor: " + Name3 +
                    ", Aadhar: " + ADDHAR + ", Mobile: " + MobileNo +
                    ", CheckIN: " + CheckIN + ", CheckOUT: " + CheckOUT +
                    ", Room: " + allocatedRoom + ", UniqueCode: " + uniqueCodeStr);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error saving booking.");
        }
    }

    public void checkExistingBooking() {
        String codeInput = JOptionPane.showInputDialog("Enter your 6-digit Unique Code:");
        boolean found = false;
        String matchedLine = null;

        try (BufferedReader br = new BufferedReader(new FileReader("log.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("UniqueCode: " + codeInput)) {
                    matchedLine = line;
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error reading booking data.");
            return;
        }

        if (found) {
            int choice = JOptionPane.showOptionDialog(null,
                    "<html><p>" + matchedLine.replaceAll(",", "<br>") + "</p></html>\nChoose an action:",
                    "Booking Found",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"🗑️ Cancel Booking", "🛠️ Update (Coming Soon)"},
                    "Cancel Booking");

            if (choice == 0) {
                String roomNumber = matchedLine.split("Room: ")[1].split(",")[0];
                releaseRoom(roomNumber);
                JOptionPane.showMessageDialog(null, "✅ Booking Cancelled Successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Booking not found.");
        }
    }

    public void viewAvailableRooms() {
        StringBuilder sb = new StringBuilder("<html><h3>🟢 Available Rooms:</h3><ul>");
        for (int i = 0; i < roomStatus.length; i++) {
            if (roomStatus[i] == 0) {
                sb.append("<li>Room ").append(101 + i).append("</li>");
            }
        }
        sb.append("</ul></html>");
        JOptionPane.showMessageDialog(null, sb.toString(), "Rooms", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logBookingToCSV(String name, String aadhaar, String mobile, String checkIn, String checkOut, int roomNo) {
        String filePath = "bookings.csv";
        boolean fileExists = new java.io.File(filePath).exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            if (!fileExists) {
                pw.println("Name,Aadhaar,Mobile,CheckIN,CheckOUT,RoomNumber");
            }
            pw.printf("%s,%s,%s,%s,%s,%d%n", name, aadhaar, mobile, checkIn, checkOut, roomNo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error writing to bookings.csv");
        }
    }
}
