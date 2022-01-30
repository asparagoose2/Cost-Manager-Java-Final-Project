package il.ac.shenkar.costManager;

import javax.swing.*;
import java.awt.*;

public class GUI {
    // swing gui

    // UI components
    private JFrame frame;
    private Font font = new Font("ariel", Font.PLAIN, 22);

    // Login
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    public static void main(String[] args) {
        GUI g = new GUI();
        g.init();
    }

    // login screen

    private void init() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Cost Manager");
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel subHeader = new JLabel("Login");
        subHeader.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        header.setFont(new Font("ariel", Font.BOLD, 40));
        subHeader.setFont(new Font("ariel", Font.BOLD, 30));
        headerPanel.add(header);
        headerPanel.add(subHeader);
        panel.add(headerPanel, BorderLayout.NORTH);

        // User Label
        JPanel userLabelPanel = new JPanel();
        userLabelPanel.setLayout(new GridLayout(1, 2));
        user_label = new JLabel();
        user_label.setText("User Name :");
        user_label.setFont(font);
        userName_text = new JTextField();
        userName_text.setFont(font);
        userName_text.setPreferredSize(new Dimension(400, 30));
        userLabelPanel.add(user_label);
        userLabelPanel.add(userName_text);
        userLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(1, 2));
        password_label = new JLabel();
        password_label.setText("Password :");
        password_label.setFont(font);
        password_text = new JPasswordField();
        password_text.setFont(font);
        password_text.setPreferredSize(new Dimension(400, 30));
        passwordPanel.add(password_label);
        passwordPanel.add(password_text);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // input section
        message = new JLabel();
        message.setFont(font);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(userLabelPanel);
        inputPanel.add(passwordPanel);
        inputPanel.add(message);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 150, 10));

        // buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        submit.setFont(font);
        submit.setPreferredSize(new Dimension(150, 30));
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        cancel.setPreferredSize(new Dimension(150, 30));
        buttonPanel.add(cancel);
        buttonPanel.add(submit);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));

        inputPanel.add(buttonPanel);

        panel.add(inputPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 600);
        frame.setResizable(false);
        frame.setVisible(true);



    }

}
