package HealthCare;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Appointment extends JFrame {
    JTextField patientIdField, doctorIdField, dateField, timeField;
    JButton scheduleButton, viewButton;
    Conn conn;
    JTable appointmentTable;
    DefaultTableModel tableModel;

    public Appointment() {
        setTitle("Appointment Scheduling");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JLabel doctorIdLabel = new JLabel("Doctor ID:");
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JLabel timeLabel = new JLabel("Time (HH:MM):");

        patientIdField = new JTextField();
        doctorIdField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();

        scheduleButton = new JButton("Schedule Appointment");
        viewButton = new JButton("View Appointments");

        inputPanel.add(patientIdLabel);
        inputPanel.add(patientIdField);
        inputPanel.add(doctorIdLabel);
        inputPanel.add(doctorIdField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);
        inputPanel.add(scheduleButton);
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display appointments
        tableModel = new DefaultTableModel(new String[]{"ID", "Patient ID", "Doctor ID", "Date", "Time"}, 0);
        appointmentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        add(tableScrollPane, BorderLayout.CENTER);

        conn = new Conn();

        // Schedule appointment action
        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientId = patientIdField.getText();
                String doctorId = doctorIdField.getText();
                String date = dateField.getText();
                String time = timeField.getText();

                try {
                    String query = "INSERT INTO appointments(patient_id, doctor_id, date, time) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, patientId);
                    pst.setString(2, doctorId);
                    pst.setString(3, date);
                    pst.setString(4, time);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Appointment scheduled successfully.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // View all appointments action
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT * FROM appointments";
                    Statement stmt = conn.c.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    // Clear existing rows
                    tableModel.setRowCount(0);

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int patientId = rs.getInt("patient_id");
                        int doctorId = rs.getInt("doctor_id");
                        String date = rs.getString("date");
                        String time = rs.getString("time");
                        tableModel.addRow(new Object[]{id, patientId, doctorId, date, time});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Appointment();
    }
}
