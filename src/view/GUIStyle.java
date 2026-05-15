package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUIStyle {

    public static final Color DARK_BG =
            new Color(24, 28, 35);

    public static final Color PANEL_BG =
            new Color(35, 41, 50);

    public static final Color CARD_BG =
            new Color(45, 52, 64);

    public static final Color PRIMARY =
            new Color(255, 152, 0);

    public static final Color SUCCESS =
            new Color(76, 175, 80);

    public static final Color TEXT =
            new Color(245, 245, 245);

    public static final Color MUTED_TEXT =
            new Color(190, 190, 190);

    public static Font titleFont() {
        return new Font("Arial", Font.BOLD, 26);
    }

    public static Font headingFont() {
        return new Font("Arial", Font.BOLD, 20);
    }

    public static Font normalFont() {
        return new Font("Arial", Font.PLAIN, 15);
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(DARK_BG);
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(PANEL_BG);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    public static void styleLabel(JLabel label) {
        label.setForeground(TEXT);
        label.setFont(normalFont());
    }

    public static void styleTitle(JLabel label) {
        label.setForeground(PRIMARY);
        label.setFont(titleFont());
    }

    public static void styleTextField(JTextField field) {
        field.setFont(normalFont());
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
    }

    public static void styleTextArea(JTextArea area) {
        area.setFont(normalFont());
        area.setBackground(CARD_BG);
        area.setForeground(TEXT);
        area.setCaretColor(TEXT);
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(PRIMARY);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
    }

    public static void styleSuccessButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(SUCCESS);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
    }

    public static void styleTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(DARK_BG);
        tabbedPane.setForeground(Color.BLACK);
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }
}