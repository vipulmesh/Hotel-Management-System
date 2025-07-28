package HotelManagement.utils;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    public static String getValidatedAadhaar() {
        while (true) {
            String input = JOptionPane.showInputDialog("Enter 12-digit Aadhaar Number:");
            if (input != null && input.matches("\\d{12}")) return input;
            JOptionPane.showMessageDialog(null, "❌ Invalid Aadhaar. Enter exactly 12 digits.");
        }
    }

    public static String getValidatedMobile() {
        while (true) {
            String input = JOptionPane.showInputDialog("Enter 10-digit Mobile Number:");
            if (input != null && input.matches("\\d{10}")) return input;
            JOptionPane.showMessageDialog(null, "❌ Invalid Mobile Number. Enter exactly 10 digits.");
        }
    }

    public static String[] getValidatedDates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate checkInDate, checkOutDate;

        while (true) {
            try {
                String in = JOptionPane.showInputDialog("Enter Check-IN Date (dd-MM-yyyy):");
                checkInDate = LocalDate.parse(in, formatter);

                String out = JOptionPane.showInputDialog("Enter Check-OUT Date (dd-MM-yyyy):");
                checkOutDate = LocalDate.parse(out, formatter);

                if (checkOutDate.isAfter(checkInDate)) {
                    return new String[]{in, out};
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Check-OUT must be after Check-IN.");
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "❌ Invalid date format. Use dd-MM-yyyy.");
            }
        }
    }
}
