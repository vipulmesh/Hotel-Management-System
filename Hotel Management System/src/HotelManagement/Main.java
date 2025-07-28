package HotelManagement;

import HotelManagement.admin.Admin;

import javax.swing.*;

public class Main {
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

          switch (choice) {
    case 0:
        a1.NewBooking();
        break;
    case 1:
        a1.checkExistingBooking();
        break;
    case 2:
        a1.viewAvailableRooms();
        break;
    default:
        JOptionPane.showMessageDialog(null, " Thank you for using NIVASAM.");
        return;
}

            }
        }
    }

