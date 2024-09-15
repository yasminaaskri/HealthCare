package HealthCare;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.*;
public class Login extends JFrame implements ActionListener {

    
     private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton b1, b2;

    public Login() {

        
        setBounds(600, 250, 700, 406);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
        contentPane.setBackground(Color.WHITE);
	contentPane.setLayout(null);
        
        
        

        // Username label
        JLabel l1 = new JLabel("Username :");
        l1.setForeground(Color.DARK_GRAY);
	l1.setFont(new Font("Tahoma", Font.BOLD, 14));
	l1.setBounds(99, 86, 92, 26);
	contentPane.add(l1);
        
        // Password label
        
        JLabel l2 = new JLabel("Password :");
	l2.setForeground(Color.DARK_GRAY);
	l2.setFont(new Font("Tahoma", Font.BOLD, 14));
	l2.setBounds(99, 160, 92, 26);
	contentPane.add(l2);
        
        

        // TextField for username
       
         textField = new JTextField();
	textField.setBounds(265, 91, 148, 20);
	contentPane.add(textField);
	textField.setColumns(10);
        
        // PasswordField for password
        passwordField = new JPasswordField();
	passwordField.setColumns(10);
	passwordField.setBounds(265, 165, 148, 20);
	contentPane.add(passwordField);
        
        // Empty labels for spacing
     
        
        b1 = new JButton("Login");
	b1.addActionListener(this);
	b1.setFont(new Font("Tahoma", Font.BOLD, 13));
	b1.setBounds(140, 289, 100, 30);
        b1.setBackground(Color.blue);
        b1.setForeground(Color.WHITE);
	contentPane.add(b1);
        
        b2 = new JButton("SignUp");
	b2.addActionListener(this);
	b2.setFont(new Font("Tahoma", Font.BOLD, 13));
	b2.setBounds(300, 289, 100, 30);
	b2.setBackground(Color.green);
        b2.setForeground(Color.WHITE);
	contentPane.add(b2);

        // Image icon for login
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("images/login.png"));
        Image i1 = c1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i1);

        JLabel l6 = new JLabel(i2);
        l6.setBounds(480, 70, 150, 150);
        contentPane.add(l6);

       JPanel panel = new JPanel();
	panel.setForeground(new Color(34, 139, 34));
	panel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 0), 2), "Enter-Account",
			TitledBorder.LEADING, TitledBorder.TOP, null, new Color(34, 139, 34)));
	panel.setBounds(31, 30, 640, 310);
        panel.setBackground(Color.WHITE);
	contentPane.add(panel);
        
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            Boolean status = false;
            try {
                Conn con = new Conn();
                String sql = "select * from account where username=? and password=?";
                PreparedStatement st = con.c.prepareStatement(sql);

                st.setString(1, textField.getText());
                st.setString(2, passwordField.getText());

                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    this.setVisible(false); // Close the login frame
                    new Home().setVisible(true); // Open the home page
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login or Password!");
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (ae.getSource() == b2) {
            setVisible(false);
            Signup su = new Signup();
            su.setVisible(true); // Open the SignUp page
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
