package HealthCare;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Messaging extends JFrame {
    JTextField senderIdField, receiverIdField, messageField;
    JButton sendButton, viewButton;
    Conn conn;
    JTable messageTable;
    DefaultTableModel tableModel;

    public Messaging() {
        setTitle("Messaging System");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JLabel senderIdLabel = new JLabel("Sender ID:");
        JLabel receiverIdLabel = new JLabel("Receiver ID:");
        JLabel messageLabel = new JLabel("Message:");

        senderIdField = new JTextField();
        receiverIdField = new JTextField();
        messageField = new JTextField();

        sendButton = new JButton("Send Message");
        viewButton = new JButton("View Messages");

        inputPanel.add(senderIdLabel);
        inputPanel.add(senderIdField);
        inputPanel.add(receiverIdLabel);
        inputPanel.add(receiverIdField);
        inputPanel.add(messageLabel);
        inputPanel.add(messageField);
        inputPanel.add(sendButton);
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display messages
        tableModel = new DefaultTableModel(new String[]{"ID", "Sender ID", "Receiver ID", "Message"}, 0);
        messageTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(messageTable);
        add(tableScrollPane, BorderLayout.CENTER);

        conn = new Conn();

        // Send message action
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String senderId = senderIdField.getText();
                String receiverId = receiverIdField.getText();
                String message = messageField.getText();

                try {
                    String query = "INSERT INTO messages(sender_id, receiver_id, message_text) VALUES (?, ?, ?)";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, senderId);
                    pst.setString(2, receiverId);
                    pst.setString(3, message);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Message sent successfully.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // View all messages action
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT * FROM messages";
                    Statement stmt = conn.c.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    // Clear existing rows
                    tableModel.setRowCount(0);

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int senderId = rs.getInt("sender_id");
                        int receiverId = rs.getInt("receiver_id");
                        String message = rs.getString("message_text");
                        tableModel.addRow(new Object[]{id, senderId, receiverId, message});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Messaging();
    }
}
