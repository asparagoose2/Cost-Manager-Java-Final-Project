package il.ac.shenkar.costManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {
    // control
    IViewModel viewModel;

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
        g.mainPage();
    }

    public GUI() {
        viewModel = new ViewModel();
    }

    // login screen

    private void loginPage() {
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
        message.setForeground(Color.RED);
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(1, 1));
        messagePanel.add(message);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(userLabelPanel);
        inputPanel.add(passwordPanel);
        inputPanel.add(messagePanel);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 150, 10));

        // buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        submit.setFont(font);
        submit.setPreferredSize(new Dimension(150, 30));
        submit.addActionListener(actionEvent -> {
            String userName = userName_text.getText();
            String password = password_text.getPassword().toString();
            if (userName.equals("") || password.equals("")) {
                message.setText("Please enter your user name and password");
            } else {
                viewModel.login(userName, password);
            }
        });

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

    public void mainPage() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Cost Manager");
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setFont(new Font("ariel", Font.BOLD, 40));
        JLabel subHeader = new JLabel("Welcome to Cost Manager");
        subHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        subHeader.setFont(new Font("ariel", Font.BOLD, 30));
        headerPanel.add(header);
        headerPanel.add(subHeader);
        panel.add(headerPanel, BorderLayout.NORTH);

        // body
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        JLabel body = new JLabel("Please select an option");
        body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        body.setFont(new Font("ariel", Font.BOLD, 30));
        bodyPanel.add(body);
        panel.add(bodyPanel, BorderLayout.EAST);

        // left side input new item form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel newItem = new JLabel("New Item");
        newItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItem.setFont(new Font("ariel", Font.BOLD, 30));

        JPanel newItemPanel = new JPanel();
        newItemPanel.setLayout(new GridLayout(1, 2));
        JLabel newItemName = new JLabel("Name :");
        newItemName.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemName.setFont(new Font("ariel", Font.BOLD, 20));
        JTextField newItemName_text = new JTextField();
        newItemName_text.setPreferredSize(new Dimension(200, 30));
        newItemName_text.setFont(new Font("ariel", Font.BOLD, 20));
        newItemPanel.add(newItemName);
        newItemPanel.add(newItemName_text);


        JPanel newItemPricePanel = new JPanel();
        newItemPricePanel.setLayout(new GridLayout(1, 2));
        JLabel newItemPrice = new JLabel("Price :");
        newItemPrice.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemPrice.setFont(new Font("ariel", Font.BOLD, 20));
        JTextField newItemPrice_text = new JTextField();
        newItemPrice_text.setPreferredSize(new Dimension(200, 30));
        newItemPrice_text.setFont(new Font("ariel", Font.BOLD, 20));
        newItemPricePanel.add(newItemPrice);
        newItemPricePanel.add(newItemPrice_text);

        JPanel newItemDescriptionPanel = new JPanel();
        newItemDescriptionPanel.setLayout(new GridLayout(1, 2));
        JLabel newItemDescription = new JLabel("Description :");
        newItemDescription.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemDescription.setFont(new Font("ariel", Font.BOLD, 20));
        JTextField newItemDescription_text = new JTextField();
        newItemDescription_text.setPreferredSize(new Dimension(200, 30));
        newItemDescription_text.setFont(new Font("ariel", Font.BOLD, 20));
        newItemDescriptionPanel.add(newItemDescription);
        newItemDescriptionPanel.add(newItemDescription_text);

        // select category
        JPanel newItemCategoryPanel = new JPanel();
        newItemCategoryPanel.setLayout(new GridLayout(1, 2));
        JLabel newItemCategory = new JLabel("Category :");
        newItemCategory.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemCategory.setFont(new Font("ariel", Font.BOLD, 20));
        JComboBox<String> newItemCategory_combo = new JComboBox<String>();
        newItemCategory_combo.setPreferredSize(new Dimension(200, 30));
        newItemCategory_combo.setFont(new Font("ariel", Font.BOLD, 20));
        newItemCategory_combo.addItem("Food");
        newItemCategory_combo.addItem("Clothes");
        newItemCategory_combo.addItem("Entertainment");
        newItemCategory_combo.addItem("Transportation");
        newItemCategory_combo.addItem("Other");
        newItemCategoryPanel.add(newItemCategory);
        newItemCategoryPanel.add(newItemCategory_combo);

        // currency type
        JPanel newItemCurrencyPanel = new JPanel();
        newItemCurrencyPanel.setLayout(new GridLayout(1, 2));
        JLabel newItemCurrency = new JLabel("Currency :");
        newItemCurrency.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemCurrency.setFont(new Font("ariel", Font.BOLD, 20));
        JComboBox<String> newItemCurrency_combo = new JComboBox<String>();
        newItemCurrency_combo.setPreferredSize(new Dimension(200, 30));
        newItemCurrency_combo.setFont(new Font("ariel", Font.BOLD, 20));
        newItemCurrency_combo.addItem("USD");
        newItemCurrency_combo.addItem("EUR");
        newItemCurrency_combo.addItem("NIS");

        newItemCurrencyPanel.add(newItemCurrency);
        newItemCurrencyPanel.add(newItemCurrency_combo);


        leftPanel.add(newItemPanel);
        leftPanel.add(newItemPricePanel);
        leftPanel.add(newItemDescriptionPanel);
        leftPanel.add(newItemCategoryPanel);
        leftPanel.add(newItemCurrencyPanel);
        panel.add(leftPanel, BorderLayout.WEST);


        //scrollable table
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel right = new JLabel("Your income");
        right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        right.setFont(new Font("ariel", Font.BOLD, 30));
        rightPanel.add(right);
        String[] columnNames = {"Date", "Name", "Amount", "Category"};
        JTable expensesTable = new JTable();
        expensesTable.setModel(new DefaultTableModel(columnNames, 0));
        expensesTable.setFont(new Font("ariel", Font.BOLD, 24));
        expensesTable.getTableHeader().setFont(new Font("ariel", Font.PLAIN, 24));
//        JPanel rightListPanel = new JPanel();
//        rightListPanel.setLayout(new BoxLayout(rightListPanel, BoxLayout.Y_AXIS));
        JScrollPane rightScroll = new JScrollPane(expensesTable);
        rightScroll.setPreferredSize(new Dimension(800, 500));
        rightScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(rightScroll);
        panel.add(rightPanel, BorderLayout.EAST);




        // buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton add = new JButton("Add");
        add.setFont(new Font("ariel", Font.BOLD, 30));
        add.setPreferredSize(new Dimension(150, 30));
        add.addActionListener(actionEvent -> {
//            viewModel.add();
        });
        JButton view = new JButton("View");
        view.setFont(new Font("ariel", Font.BOLD, 30));
        view.setPreferredSize(new Dimension(150, 30));
        view.addActionListener(actionEvent -> {
//            viewModel.view();
        });
        JButton edit = new JButton("Edit");
                edit.setFont(new Font("ariel", Font.BOLD, 30));
        edit.setPreferredSize(new Dimension(150, 30));
        edit.addActionListener(actionEvent -> {
//            viewModel.edit();
        });
        JButton delete = new JButton("Delete");
        delete.setFont(new Font("ariel", Font.BOLD, 30));
        delete.setPreferredSize(new Dimension(150, 30));
        delete.addActionListener(actionEvent -> {
//            viewModel.delete();
        });
        buttonPanel.add(add);
        buttonPanel.add(view);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
        panel.add(buttonPanel, BorderLayout.SOUTH);



        frame.add(panel);
        frame.setSize(1600, 1200);

        frame.setResizable(false);
        frame.setVisible(true);

    }

}
