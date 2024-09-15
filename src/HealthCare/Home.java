package HealthCare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    JButton appointmentButton, messagingButton;
    JLabel imageLabel;

    public Home() {
        setTitle("Home Page");
        setSize(600, 400); // Increase the size to accommodate the image
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400, 200);
        setLayout(new BorderLayout());

        // Create Buttons
        appointmentButton = new JButton("Appointment Scheduling");
        messagingButton = new JButton("Messaging System");

        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(appointmentButton);
        buttonPanel.add(messagingButton);

        // Add buttons panel to the left
        add(buttonPanel, BorderLayout.WEST);

        // Load and add image to the right
        imageLabel = new JLabel();
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("images/nurse.jfif"));
        Image i1 = c1.getImage().getScaledInstance(420, 400, Image.SCALE_SMOOTH);
        ImageIcon i2 = new ImageIcon(i1);
        imageLabel.setIcon(i2);
        add(imageLabel, BorderLayout.EAST);

        // Button actions to open respective pages
        appointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Appointment(); // Open the Appointment Scheduling page
            }
        });

        messagingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Messaging(); // Open the Messaging System page
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Home(); // Display the Home Page
    }
}
