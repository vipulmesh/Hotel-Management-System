

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

class Admin {
    int[] roomStatus = new int[60];
    Scanner sc = new Scanner(System.in);
    String Name, PName, Name2, Name3, ADDHAR, MobileNo, CheckIN, CheckOUT, UserID;
    int Password;
    private final String AUserID = "vipulmeshram";
    private final int APassword = 1234;
    StringBuilder uniqueCodeStr = new StringBuilder();

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
        panel.add(new JLabel("👤 User ID:"), gbc);
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
            ADDHAR = JOptionPane.showInputDialog("Enter Your Aadhar Number:");
            MobileNo = JOptionPane.showInputDialog("Enter Your Mobile Number:");
            CheckIN = JOptionPane.showInputDialog("Check-IN Date:");
            CheckOUT = JOptionPane.showInputDialog("Check-OUT Date:");

            String allocatedRoom = allocateRoom();
            uniqueCode();
            JOptionPane.showMessageDialog(null, 
                "<html><h2 style='color:green;'>Room Allocated: " + allocatedRoom +
                "</h2><p>Unique Code: <b>" + uniqueCodeStr + "</b></p></html>", 
                "Booking Success ✅", JOptionPane.INFORMATION_MESSAGE);
            FileHandling_SingleRoom(allocatedRoom);

        } else if (Rchoose == 1) {
            PName = JOptionPane.showInputDialog("Enter Primary Person Name:");
            Name2 = JOptionPane.showInputDialog("Enter 2nd Person Name:");
            Name3 = JOptionPane.showInputDialog("Enter Minor Name:");
            ADDHAR = JOptionPane.showInputDialog("Enter Primary Aadhar Number:");
            MobileNo = JOptionPane.showInputDialog("Enter Your Mobile Number:");
            CheckIN = JOptionPane.showInputDialog("Check-IN Date:");
            CheckOUT = JOptionPane.showInputDialog("Check-OUT Date:");

            String allocatedRoom = allocateRoom();
            uniqueCode();
            JOptionPane.showMessageDialog(null, 
                "<html><h2 style='color:green;'>Room Allocated: " + allocatedRoom +
                "</h2><p>Unique Code: <b>" + uniqueCodeStr + "</b></p></html>", 
                "Booking Success ✅", JOptionPane.INFORMATION_MESSAGE);
            FileHandling_SingleRoom2(allocatedRoom);
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
            bw.write("Name: " + Name + ", Aadhar: " + ADDHAR + ", Mobile: " + MobileNo + ", CheckIN: " + CheckIN + ", CheckOUT: " + CheckOUT + ", Room: " + allocatedRoom + ", UniqueCode: " + uniqueCodeStr);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Error saving booking.");
        }
    }

    public void FileHandling_SingleRoom2(String allocatedRoom) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
            bw.write("Name: " + PName + ", 2nd Name: " + Name2 + ", Minor: " + Name3 + ", Aadhar: " + ADDHAR + ", Mobile: " + MobileNo + ", CheckIN: " + CheckIN + ", CheckOUT: " + CheckOUT + ", Room: " + allocatedRoom + ", UniqueCode: " + uniqueCodeStr);
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
}

public class HotelManagement {
    public static void main(String[] args) {
        // Set Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Default LookAndFeel used.");
        }

        Admin a1 = new Admin();
        boolean loggedIn = a1.LoginPortalAdmin();

        if (!loggedIn) return;

        while (true) {
            String[] options = {"🆕 New Booking", "🔍 Existing Booking", "🏨 View Rooms", "🚪 Exit"};
            int choice = JOptionPane.showOptionDialog(null, 
                    "<html><center><h1 style='color:#0a74da;'>🏨 NIVASAM</h1>" +
                    "<h3>Hotel Management System</h3><br><small>By Vipul Meshram</small></center></html>",
                    "Welcome", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, options, options[0]);

            if (choice == 0) {
                a1.NewBooking();
            } else if (choice == 1) {
                a1.checkExistingBooking();
            } else if (choice == 2) {
                a1.viewAvailableRooms();
            } else {
                JOptionPane.showMessageDialog(null, " Thank you for using NIVASAM.");
                break;
            }
        }
    }
}
