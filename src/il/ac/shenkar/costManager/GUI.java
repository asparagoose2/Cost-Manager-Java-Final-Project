package il.ac.shenkar.costManager;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;




public class GUI implements IView {
    // data
    private User user;
    private LinkedList<Item> items;
    private ArrayList<Category> categories;
    private boolean isLoggedIn = false;
    String[] columnNames = {"Date", "Name", "Amount", "Category"};


    // control
    final private IViewModel viewModel;

    // UI components
    private JFrame frame;
    private Font font = new Font("ariel", Font.PLAIN, 22);
    JTable expensesTable;

    // Login
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    public static void main(String[] args) {
//        GUI g = new GUI();
//        g.mainPage();
    }

    public GUI(IViewModel vm) {
        viewModel = vm;
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
        AtomicReference<IViewModel> vm = new AtomicReference<>(viewModel);

        submit.addActionListener(actionEvent -> {
            System.out.println("submit");
            String userName = userName_text.getText();
            String password = String.valueOf(password_text.getPassword());
            if (userName.equals("") || password.equals("")) {
                message.setText("Please enter your user name and password");
            } else {
                if(vm != null) {
                    vm.get().login(userName, password);
                } else {
                    message.setText("Please login first");
                }
            }
        });

        cancel = new JButton("Register");
        cancel.setFont(font);
        cancel.setPreferredSize(new Dimension(150, 30));
        cancel.addActionListener(actionEvent -> {
            register();
        });
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

    public void register() {
        frame.dispose();
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Cost Manager");
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel subHeader = new JLabel("Register");
        subHeader.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        header.setFont(new Font("ariel", Font.BOLD, 40));
        subHeader.setFont(new Font("ariel", Font.BOLD, 30));
        headerPanel.add(header);
        headerPanel.add(subHeader);
        panel.add(headerPanel, BorderLayout.NORTH);

        // First Name Label
        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new GridLayout(1, 2));
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name :");
        firstNameLabel.setFont(font);
        JTextField firstNameInput = new JTextField();
        firstNameInput.setFont(font);
        firstNameInput.setPreferredSize(new Dimension(400, 30));
        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstNameInput);
        firstNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Last Name Label
        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new GridLayout(1, 2));
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name :");
        lastNameLabel.setFont(font);
        JTextField lastNameInput = new JTextField();
        lastNameInput.setFont(font);
        lastNameInput.setPreferredSize(new Dimension(400, 30));
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameInput);
        lastNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Email Label
        JPanel userLabelPanel = new JPanel();
        userLabelPanel.setLayout(new GridLayout(1, 2));
        user_label = new JLabel();
        user_label.setText("Email :");
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

        //input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(firstNamePanel);
        inputPanel.add(lastNamePanel);
        inputPanel.add(userLabelPanel);
        inputPanel.add(passwordPanel);

        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(font);
        registerButton.setPreferredSize(new Dimension(200, 30));
        registerButton.addActionListener(e -> {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String userName = userName_text.getText();
            String password = String.valueOf(password_text.getPassword());
            if (firstName.equals("") || lastName.equals("") || userName.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                if (userName.contains("@")) {
                    if (userName.contains(".")) {
                        if (password.length() >= 8) {
                            viewModel.register(firstName, lastName, userName, password);
                        } else {
                            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid email");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email");
                }
            }
        });
        // cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(font);
        cancelButton.setPreferredSize(new Dimension(200, 30));
        cancelButton.addActionListener(e -> {
            frame.dispose();
            loginPage();
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
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
        JLabel subHeader;
        if(user != null) {
            subHeader = new JLabel("Welcome " + user.getName());
        } else {
            subHeader = new JLabel("Welcome to Cost Manager");
        }
        subHeader.setFont(new Font("ariel", Font.BOLD, 30));
        subHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        JLabel leftHeader = new JLabel("Add New Item", SwingConstants.LEFT);
        leftHeader.setFont(new Font("ariel", Font.BOLD, 30));
        leftHeader.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 10, 10, 10);

        JLabel newItem = new JLabel("New Item");
        newItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItem.setFont(new Font("ariel", Font.BOLD, 30));

        JPanel newItemPanel = new JPanel();
        newItemPanel.setLayout(new GridBagLayout());

        JLabel newItemName = new JLabel("Name :");
        newItemName.setFont(new Font("ariel", Font.BOLD, 20));
        JTextField newItemName_text = new JTextField();
        newItemName_text.setMaximumSize(new Dimension(200, 30));
        newItemName_text.setPreferredSize(new Dimension(200, 30));
        newItemName_text.setFont(new Font("ariel", Font.PLAIN, 20));
        newItemName_text.setAlignmentX(Component.RIGHT_ALIGNMENT);
        newItemPanel.add(newItemName, c);
        c.gridx = 1;
        newItemPanel.add(newItemName_text, c);
        c.gridy = 1;
        c.gridx = 0;

        // date picker
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setDefaultYearMonth(YearMonth.from(LocalDate.now()));
        dateSettings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
        dateSettings.setFontCalendarDateLabels(new Font("ariel", Font.BOLD, 20));
        dateSettings.setFontCalendarWeekdayLabels(new Font("ariel", Font.BOLD, 20));
        dateSettings.setFontCalendarWeekNumberLabels(new Font("ariel", Font.BOLD, 20));
        dateSettings.setFontMonthAndYearMenuLabels(new Font("ariel", Font.BOLD, 20));
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setFont(new Font("ariel", Font.BOLD, 30));
        JLabel date = new JLabel("Date :");
        date.setFont(new Font("ariel", Font.BOLD, 20));

        newItemPanel.add(date, c);
        c.gridx = 1;
        newItemPanel.add(datePicker, c);
        c.gridy = 2;
        c.gridx = 0;





        JPanel newItemPricePanel = new JPanel();
        newItemPricePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel newItemPrice = new JLabel("Price :");
        newItemPrice.setFont(new Font("ariel", Font.BOLD, 20));
        JTextField newItemPrice_text = new JTextField();
        newItemPrice_text.setPreferredSize(new Dimension(200, 30));
        newItemPrice_text.setFont(new Font("ariel", Font.BOLD, 20));
        newItemPanel.add(newItemPrice, c);
        c.gridx = 1;
        newItemPanel.add(newItemPrice_text, c);
        c.gridy = 3;
        c.gridx = 0;

        JLabel newItemDescription = new JLabel("Description :");
        newItemDescription.setFont(new Font("ariel", Font.BOLD, 20));
        JTextArea newItemDescription_text = new JTextArea();
        newItemDescription_text.setPreferredSize(new Dimension(200, 30));
        newItemDescription_text.setFont(new Font("ariel", Font.BOLD, 20));
        newItemPanel.add(newItemDescription, c);
        c.gridx = 1;
        newItemPanel.add(newItemDescription_text, c);
        c.gridy = 4;
        c.gridx = 0;

        // select category
        JLabel newItemCategory = new JLabel("Category :");
        newItemCategory.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemCategory.setFont(new Font("ariel", Font.BOLD, 20));
        JComboBox<Category> newItemCategory_combo = new JComboBox<Category>();
        newItemCategory_combo.setPreferredSize(new Dimension(200, 30));
        newItemCategory_combo.setFont(new Font("ariel", Font.BOLD, 20));
        if (categories != null) {
            for (Category category : categories) {
                newItemCategory_combo.addItem(category);
            }
        }
        // add category button
        JButton addCategoryButton = new JButton("+");
        addCategoryButton.setPreferredSize(new Dimension(50, 50));
        addCategoryButton.setFont(new Font("ariel", Font.PLAIN, 14));
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCategoryDialog addCategoryDialog = new AddCategoryDialog(frame, "Add Category", true);
                addCategoryDialog.setVisible(true);
            }
        });


        JPanel newItemCategoryPanel = new JPanel();
        newItemCategoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        newItemCategoryPanel.add(newItemCategory_combo);
        newItemCategoryPanel.add(addCategoryButton);


        newItemPanel.add(newItemCategory, c);
        c.gridx = 1;
        newItemPanel.add(newItemCategoryPanel, c);
        c.gridy = 5;
        c.gridx = 0;

        // currency type
        JLabel newItemCurrency = new JLabel("Currency :");
        newItemCurrency.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newItemCurrency.setFont(new Font("ariel", Font.BOLD, 20));
        JComboBox<IModel.CURRENCY> newItemCurrency_combo = new JComboBox<IModel.CURRENCY>();
        newItemCurrency_combo.setPreferredSize(new Dimension(200, 30));
        newItemCurrency_combo.setFont(new Font("ariel", Font.BOLD, 20));
        for(int i = 0; i < IModel.CURRENCY.values().length; i++) {
            newItemCurrency_combo.addItem(IModel.CURRENCY.values()[i]);
        }

        newItemPanel.add(newItemCurrency, c);
        c.gridx = 1;
        newItemPanel.add(newItemCurrency_combo, c);
        c.gridy = 6;
        c.gridx = 0;

        // save button
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.setLayout(new GridLayout(1, 1));
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 30));
        saveButton.setFont(new Font("ariel", Font.BOLD, 20));
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        saveButton.addActionListener(actionEvent -> {
            String name = newItemName_text.getText();
            String price = newItemPrice_text.getText();
            String description = newItemDescription_text.getText();
            String category = newItemCategory_combo.getSelectedItem().toString();
            System.out.println(datePicker.getDate());
            Category selectedCategory = newItemCategory_combo.getSelectedItem() == null ? null : (Category) newItemCategory_combo.getSelectedItem();
            int currency = ((IModel.CURRENCY) newItemCurrency_combo.getSelectedItem()).getValue(); //.toString().equals("USD") ? IModel.CURRENCY.USD.getValue() : newItemCurrency_combo.getSelectedItem().toString().equals("EUR") ? IModel.CURRENCY.EUR.getValue() : IModel.CURRENCY.NIS.getValue();
            System.out.println(currency);
            if (name.equals("") || price.equals("") || description.equals("") || category.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                viewModel.addItem(name, Double.parseDouble(price), selectedCategory ,description, currency, Date.valueOf(datePicker.getDate()));
                newItemName_text.setText("");
                newItemPrice_text.setText("");
                newItemDescription_text.setText("");
                newItemCategory_combo.setSelectedIndex(0);
                newItemCurrency_combo.setSelectedIndex(0);
                datePicker.setDate(null);

            }
        } );

        c.gridx = 1;
        newItemPanel.add(saveButtonPanel, c);


        leftHeader.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        leftPanel.add(leftHeader);
        leftPanel.add(newItemPanel);

        leftPanel.setPreferredSize(new Dimension(500, 200));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(leftPanel, BorderLayout.WEST);


        //scrollable table
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel right = new JLabel("Your Expenses");
        right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        right.setFont(new Font("ariel", Font.BOLD, 30));
        rightPanel.add(right);

        JToggleButton showCurrentMonth = new JToggleButton("Current Month");
        showCurrentMonth.setPreferredSize(new Dimension(200, 30));
        showCurrentMonth.setFont(new Font("ariel", Font.BOLD, 20));
        showCurrentMonth.addActionListener(actionEvent -> {
            if (showCurrentMonth.isSelected()) {
                viewModel.showCurrentMonth();
            } else {
                viewModel.getItems();
            }
                } );
        rightPanel.add(showCurrentMonth);


        expensesTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        if(items != null) {
            for (Item item : items) {
                String currencySymbol = item.getCurrency() == IModel.CURRENCY.USD.getValue() ? "$" : item.getCurrency() == IModel.CURRENCY.EUR.getValue() ? "€" : "₪";
                String[] row = {item.getDate().toString(), item.getName(), String.valueOf(item.getCost()) + currencySymbol, item.getCategory().getName()};
                tableModel.addRow(row);
            }
        }
        expensesTable.setModel(tableModel);
        expensesTable.setFont(new Font("ariel", Font.BOLD, 24));
        expensesTable.getTableHeader().setFont(new Font("ariel", Font.PLAIN, 24));
        expensesTable.setRowHeight(30);
        expensesTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
        expensesTable.setFillsViewportHeight(true);

        JScrollPane rightScroll = new JScrollPane(expensesTable);
        rightScroll.setPreferredSize(new Dimension(1000, 500));
        rightScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(rightScroll);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(rightPanel, BorderLayout.EAST);


        frame.add(panel);
        frame.setSize(1600, 1200);

        frame.setResizable(false);
        frame.setVisible(true);

    }

    @Override
    public void displayData(String data) {
        if(expensesTable != null) {
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            if (items != null) {
                for (Item item : items) {
                    String currencySymbol = item.getCurrency() == IModel.CURRENCY.USD.getValue() ? "$" : item.getCurrency() == IModel.CURRENCY.EUR.getValue() ? "€" : "₪";
                    String[] row = {item.getDate().toString(), item.getName(), String.valueOf(item.getCost()) + currencySymbol, item.getCategory().getName()};
                    tableModel.addRow(row);
                }
            }
            expensesTable.setModel(tableModel);
        }
    }

    @Override
    public void displayError(String error, Boolean shouldExit) {
        JOptionPane.showMessageDialog(frame,error,"Error!", JOptionPane.ERROR_MESSAGE);
        if (shouldExit) {
            System.exit(0);
        }

    }

    @Override
    public void displayMessage(String message, String title) {

    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        System.out.println("set view model");
        System.out.println(viewModel);
    }

    @Override
    public void setItems(Collection<Item> items) {
        this.items = (LinkedList<Item>) items;
    }

    @Override
    public void setCategories(Collection<Category> categories) {
        System.out.println("set categories");
        System.out.println(categories.size());
        this.categories = new ArrayList<Category>(categories);
        frame.dispose();
        mainPage();
//        mainPage();

    }

    @Override
    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn=isLoggedIn;
        if (isLoggedIn) {
            frame.dispose();
            mainPage();
        }
    }

    @Override
    public void start() {

        loginPage();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public class AddCategoryDialog extends JDialog {
        private JTextField categoryName;
        private JButton add;
        private JButton cancel;
        private JPanel buttonPanel;
        private JPanel mainPanel;
        private JPanel namePanel;
        private JLabel nameLabel;
        private JLabel errorLabel;

        public AddCategoryDialog(JFrame parent, String title, boolean modal) {
            super(parent, title, modal);
            this.mainPanel = new JPanel();
            this.mainPanel.add(new JLabel(title));
            this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            this.namePanel = new JPanel();
            this.namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.nameLabel = new JLabel("Category name:");
            this.namePanel.add(nameLabel);
            this.categoryName = new JTextField(20);
            this.namePanel.add(categoryName);
            this.mainPanel.add(namePanel);
            this.errorLabel = new JLabel("");
            this.errorLabel.setForeground(Color.RED);
            this.errorLabel.setVisible(false);
            this.mainPanel.add(errorLabel);
            this.buttonPanel = new JPanel();
            this.buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.add = new JButton("Add");
            this.add.addActionListener(actionEvent -> {
                if (categoryName.getText().isEmpty()) {
                    errorLabel.setText("Category name cannot be empty!");
                } else {
                    viewModel.addCategory(categoryName.getText());
                    this.dispose();
                }

            });
            this.cancel = new JButton("Cancel");
            this.cancel.addActionListener(actionEvent -> {
                this.dispose();
            });
            this.buttonPanel.add(add);
            this.buttonPanel.add(cancel);
            this.mainPanel.add(buttonPanel);

            this.setContentPane(this.mainPanel);
            this.setSize(400, 200);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }

    }

}

