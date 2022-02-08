package il.ac.shenkar.costManager;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class GUI implements IView {
    // data
    private User user;
    private LinkedList<Item> items;
    private ArrayList<Category> categories;
    private boolean isLoggedIn = false;
    String[] columnNames = {"Date", "Description", "Amount", "Category"};


    // control
    final private IViewModel viewModel;

    // UI components
    private JFrame frame;
    private Font font = new Font("ariel", Font.PLAIN, 22);
    JTable expensesTable;
    JToggleButton sortButton;

    // inputs
    JComboBox<Category> newItemCategoryCombo;
    JPanel newItemCategoryPanel;
    JButton addCategoryButton;
    Dimension inputDimension = new Dimension(200, 30);


    // Login
    JPanel panel;
    JLabel emailLabel, passwordLabel, message;
    JTextField emailInput;
    JPasswordField passwordInput;
    JButton submit, cancel;

    public GUI(IViewModel vm) {
        this.viewModel = vm; //cannot use this.setViewModel(vm) because it is final
    }

    /**
     * This method is used to create and display the login page.
     */
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

        // Email Label
        JPanel userLabelPanel = new JPanel();
        userLabelPanel.setLayout(new GridLayout(1, 2));
        emailLabel = new JLabel();
        emailLabel.setText("Email: ");
        emailLabel.setFont(font);
        emailInput = new JTextField();
        emailInput.setFont(font);
        emailInput.setPreferredSize(new Dimension(400, 30));
        userLabelPanel.add(emailLabel);
        userLabelPanel.add(emailInput);
        userLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(1, 2));
        passwordLabel = new JLabel();
        passwordLabel.setText("Password: ");
        passwordLabel.setFont(font);
        passwordInput = new JPasswordField();
        passwordInput.setFont(font);
        passwordInput.setPreferredSize(new Dimension(400, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
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
            String userName = emailInput.getText();
            String password = String.valueOf(passwordInput.getPassword());
            if (userName.equals("") || password.equals("")) {
                message.setText("Please enter your user name and password");
            } else {
                if (vm != null) {
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

        // submit on enter
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick();
                }
            }
        };
        emailInput.addKeyListener(keyListener);
        passwordInput.addKeyListener(keyListener);

        frame.setSize(500, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * This method is used to display the register view.
     */
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
        emailLabel = new JLabel();
        emailLabel.setText("Email :");
        emailLabel.setFont(font);
        emailInput = new JTextField();
        emailInput.setFont(font);
        emailInput.setPreferredSize(new Dimension(400, 30));
        userLabelPanel.add(emailLabel);
        userLabelPanel.add(emailInput);
        userLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(1, 2));
        passwordLabel = new JLabel();
        passwordLabel.setText("Password :");
        passwordLabel.setFont(font);
        passwordInput = new JPasswordField();
        passwordInput.setFont(font);
        passwordInput.setPreferredSize(new Dimension(400, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
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
            String email = emailInput.getText();
            String password = String.valueOf(passwordInput.getPassword());
            if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                if (email.contains("@")) {
                    if (email.contains(".")) {
                        if (password.length() >= 8) {
                            viewModel.register(firstName, lastName, email, password);
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

    /**
     * This method is used to display the main application page.
     */
    public void mainPage() {
        frame = new JFrame();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // header
        // Text part
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel header = new JLabel("Cost Manager");
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setFont(new Font("ariel", Font.BOLD, 40));
        JLabel subHeader;
        if (user != null) {
            subHeader = new JLabel("Welcome " + user.getName());
        } else {
            subHeader = new JLabel("Welcome to Cost Manager");
        }
        subHeader.setFont(new Font("ariel", Font.BOLD, 30));
        subHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel headerTextPanel = new JPanel();
        headerTextPanel.setLayout(new BoxLayout(headerTextPanel, BoxLayout.Y_AXIS));
        headerTextPanel.add(header);
        headerTextPanel.add(subHeader);
        headerPanel.add(headerTextPanel, BorderLayout.WEST);

        // logout button
        JButton logoutButton = new JButton("logout");
        logoutButton.setFont(new Font("ariel", Font.BOLD, 20));
        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.setLayout(new BoxLayout(logoutButtonPanel, BoxLayout.Y_AXIS));
        logoutButton.setLayout(new BoxLayout(logoutButton, BoxLayout.Y_AXIS));
        logoutButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutButtonPanel.add(logoutButton);
        headerPanel.add(logoutButtonPanel, BorderLayout.EAST);
        logoutButton.addActionListener(actionEvent -> {
            viewModel.logout();
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);


        // Left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        // Title
        JLabel leftHeader = new JLabel("Add New Item", SwingConstants.LEFT);
        leftHeader.setFont(new Font("ariel", Font.BOLD, 30));
        leftHeader.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 10));
        JPanel leftHeaderPanel = new JPanel();
        leftHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftHeaderPanel.add(leftHeader);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;

        JPanel itemInputPanel = new JPanel();
        itemInputPanel.setLayout(new GridBagLayout());

        // Input new item form
        //Description
        JLabel newItemDescription = createInputLabel("Description: "); //new JLabel("Name :");
        JTextField newItemDescriptionText = new JTextField();
        newItemDescriptionText.setFont(new Font("ariel", Font.PLAIN, 20));
        itemInputPanel.add(newItemDescription, c);
        c.gridx = 1;
        itemInputPanel.add(newItemDescriptionText, c);
        c.gridy = 1;
        c.gridx = 0;

        // date picker
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setDefaultYearMonth(YearMonth.from(LocalDate.now()));
        dateSettings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
        Font calendarFont = new Font("ariel", Font.PLAIN, 20);
        dateSettings.setFontCalendarDateLabels(calendarFont);
        dateSettings.setFontCalendarWeekdayLabels(calendarFont);
        dateSettings.setFontCalendarWeekNumberLabels(calendarFont);
        dateSettings.setFontMonthAndYearMenuLabels(calendarFont);
        dateSettings.setFontValidDate(calendarFont);
        dateSettings.setFontTodayLabel(calendarFont);
        DatePicker datePicker = new DatePicker(dateSettings);
        JLabel date = createInputLabel("Date: ");

        itemInputPanel.add(date, c);
        c.gridx = 1;
        itemInputPanel.add(datePicker, c);
        c.gridy = 2;
        c.gridx = 0;

        // price
        JLabel newItemPrice = createInputLabel("Price: ");
        NumberFormat priceFormatter = new DecimalFormat("#0.00");
        JFormattedTextField newItemPriceText = new JFormattedTextField(priceFormatter);
        newItemPriceText.setPreferredSize(new Dimension(200, 30));
        newItemPriceText.setFont(new Font("ariel", Font.PLAIN, 20));
        itemInputPanel.add(newItemPrice, c);
        c.gridx = 1;
        itemInputPanel.add(newItemPriceText, c);
        c.gridy = 3;
        c.gridx = 0;

        // select category
        JLabel newItemCategory = createInputLabel("Category: ");
        this.newItemCategoryCombo = this.renderCategories();
        // add new category button
        addCategoryButton = new JButton("+");
        addCategoryButton.setPreferredSize(new Dimension(30, 30));
        addCategoryButton.setFont(new Font("ariel", Font.PLAIN, 18));
        addCategoryButton.setMargin(new Insets(0, 0, 0, 0));
        addCategoryButton.addActionListener(e -> {
            AddCategoryDialog addCategoryDialog = new AddCategoryDialog(frame, "Add Category", true);
            addCategoryDialog.setVisible(true);
        });

        this.newItemCategoryPanel = new JPanel();
        this.newItemCategoryPanel.setLayout(new BoxLayout(this.newItemCategoryPanel, BoxLayout.X_AXIS));
        this.newItemCategoryPanel.add(this.newItemCategoryCombo);
        this.newItemCategoryPanel.add(Box.createHorizontalStrut(3)); // empty space
        this.newItemCategoryPanel.add(addCategoryButton);

        itemInputPanel.add(newItemCategory, c);
        c.gridx = 1;
        itemInputPanel.add(newItemCategoryPanel, c);
        c.gridy = 4;
        c.gridx = 0;

        // currency
        JLabel newItemCurrency = createInputLabel("Currency: ");
        JComboBox<IModel.CURRENCY> newItemCurrencyCombo = new JComboBox<IModel.CURRENCY>();
        newItemCurrencyCombo.setPreferredSize(new Dimension(200, 30));
        newItemCurrencyCombo.setFont(new Font("ariel", Font.PLAIN, 20));
        for (int i = 0; i < IModel.CURRENCY.values().length; i++) {
            newItemCurrencyCombo.addItem(IModel.CURRENCY.values()[i]);
        }

        itemInputPanel.add(newItemCurrency, c);
        c.gridx = 1;
        itemInputPanel.add(newItemCurrencyCombo, c);
        c.gridy = 5;
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
            String description = newItemDescriptionText.getText();
            String price = newItemPriceText.getText();
            String category = this.newItemCategoryCombo.getSelectedItem().toString();
            System.out.println(datePicker.getDate());
            Category selectedCategory = this.newItemCategoryCombo.getSelectedItem() == null ? null : (Category) this.newItemCategoryCombo.getSelectedItem();
            int currency = ((IModel.CURRENCY) newItemCurrencyCombo.getSelectedItem()).getValue();
            System.out.println(currency);
            if (description.equals("") || price.equals("") || category.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                viewModel.addItem(description, Double.parseDouble(price), selectedCategory, currency, Date.valueOf(datePicker.getDate()));
                sortButton.setSelected(false);
                newItemDescriptionText.setText("");
                newItemPriceText.setText("");
                newItemCategoryCombo.setSelectedIndex(0);
                newItemCurrencyCombo.setSelectedIndex(0);
                datePicker.setDate(LocalDate.now());
            }
        });

        c.gridx = 1;
        itemInputPanel.add(saveButtonPanel, c);

        leftPanel.add(leftHeaderPanel);
        leftPanel.add(itemInputPanel);
        leftPanel.setPreferredSize(new Dimension(500, 200));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right side
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        // Right title
        JPanel rightHeaderPanel = new JPanel();
        rightHeaderPanel.setLayout(new BorderLayout());
        rightHeaderPanel.setSize(new Dimension(800, 80));
        rightHeaderPanel.setMinimumSize(new Dimension(1, 1));
        JLabel rightTitle = new JLabel("Your Expenses");
        rightTitle.setFont(new Font("ariel", Font.BOLD, 30));
        rightHeaderPanel.add(rightTitle, BorderLayout.WEST);

        // Sort/Report controls
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayout(1, 3));
        // month selector
        JComboBox<Month> sortMonth = new JComboBox<Month>(Month.values());
        sortMonth.addActionListener(actionEvent -> {
            sortButton.setSelected(false);
            viewModel.getItems();
        });
        // year input filed
        DateFormat format = new SimpleDateFormat("Y");
        JFormattedTextField sortYear = new JFormattedTextField(format);
        sortYear.setFont(new Font("ariel", Font.PLAIN, 20));
        sortYear.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                sortButton.setSelected(false);
                viewModel.getItems();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                sortButton.setSelected(false);
                viewModel.getItems();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                sortButton.setSelected(false);
                viewModel.getItems();

            }
        });
        // sort button
        sortButton = new JToggleButton("Report");
        sortButton.addActionListener(actionEvent -> {
            if (sortYear.getText().equals("")) {

            } else {
                if (sortButton.isSelected()) {
                    int year = Integer.parseInt(sortYear.getText());
                    Month month = (Month) sortMonth.getSelectedItem();
                    if (year > LocalDate.now().getYear()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid year");
                        sortButton.setSelected(false);
                    } else {
                        viewModel.getItems(month, year);
                    }
                } else {
                    viewModel.getItems();
                }
            }

        });

        sortPanel.add(sortMonth);
        sortPanel.add(sortYear);
        sortPanel.add(sortButton);

        rightHeaderPanel.add(sortPanel, BorderLayout.EAST);
        rightHeaderPanel.add(Box.createHorizontalStrut(100), BorderLayout.CENTER);
        rightHeaderPanel.setMaximumSize(new Dimension(800, 40));
        rightPanel.add(rightHeaderPanel);

        // Expenses scrollable table
        expensesTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        if (items != null) {
            for (Item item : items) {
                String currencySymbol = item.getCurrency() == IModel.CURRENCY.USD.getValue() ? "$" : item.getCurrency() == IModel.CURRENCY.EUR.getValue() ? "€" : "₪";
                String[] row = {item.getDate().toString(), item.getDescription(), String.valueOf(item.getCost()) + currencySymbol, item.getCategory().getName()};
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
        mainPanel.add(rightPanel, BorderLayout.EAST);


        frame.add(mainPanel);
        frame.setSize(1600, 1200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * This method is used to create an inout label
     *
     * @param text the text for the lable
     * @return
     */
    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("ariel", Font.BOLD, 20));
        label.setPreferredSize(new Dimension(100, 30));
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        label.setAlignmentY(JLabel.TOP_ALIGNMENT);
        return label;
    }

    /**
     * updates the table with the new items
     *
     * @param data not used in this implementation
     */
    @Override
    public void displayData(String data) {
        if (expensesTable != null) {
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            if (items != null) {
                for (Item item : items) {
                    String currencySymbol = item.getCurrency() == IModel.CURRENCY.USD.getValue() ? "$" : item.getCurrency() == IModel.CURRENCY.EUR.getValue() ? "€" : "₪";
                    String[] row = {item.getDate().toString(), item.getDescription(), String.valueOf(item.getCost()) + currencySymbol, item.getCategory().getName()};
                    tableModel.addRow(row);
                }
            }
            expensesTable.setModel(tableModel);
        }
    }


    /**
     * This method is used to alert the user of an occurred error in the application.
     * error is displayed in a JOptionPane.
     *
     * @param error      the error to display.
     * @param shouldExit if true, the application will exit after the error is displayed.
     */
    @Override
    public void displayError(String error, Boolean shouldExit) {
        JOptionPane.showMessageDialog(frame, error, "Error!", JOptionPane.ERROR_MESSAGE);
        if (shouldExit) {
            System.exit(0);
        }

    }

    /**
     * This method is used to display a message to the user.
     * message is display in a JOptionPane.
     *
     * @param message the message to display.
     * @param title   the title of the message.
     */
    @Override
    public void displayMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is used to set the view model
     *
     * @param viewModel the ViewModel to set.
     */
    @Override
    public void setViewModel(IViewModel viewModel) {
        System.out.println("set view model");
        System.out.println(viewModel);
    }

    /**
     * This method is used to set the items in the view.
     *
     * @param items the items to display.
     */
    @Override
    public void setItems(Collection<Item> items) {
        this.items = (LinkedList<Item>) items;
    }

    /**
     * Method to build combo box to select categories.
     * Data is taken from "categories" field.
     *
     * @return the combobox created
     */
    private JComboBox<Category> renderCategories() {
        JComboBox<Category> newCombo = new JComboBox<Category>();
        newCombo.setPreferredSize(new Dimension(250, 30));
        newCombo.setFont(new Font("ariel", Font.PLAIN, 20));
        if (categories != null) {
            for (Category category : categories) {
                newCombo.addItem(category);
            }
        }
        return newCombo;
    }

    /**
     * Method to set the categories in the view.
     * Combo box is updated with the new categories.
     *
     * @param categories the categories to display.
     */
    @Override
    public void setCategories(Collection<Category> categories) {
        this.categories = new ArrayList<Category>(categories);
        if (this.newItemCategoryPanel != null) {
            this.newItemCategoryPanel.remove(0);
            this.newItemCategoryPanel.add(renderCategories(), 0);
            this.newItemCategoryPanel.updateUI();
        }
    }

    /**
     * Method to set the isLoggedIn field.
     * If true, the main page is displayed.
     * If false, the login page is displayed.
     *
     * @param isLoggedIn the flag to set.
     */
    @Override
    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        if (isLoggedIn) {
            frame.dispose();
            mainPage();
        } else {
            frame.dispose();
            loginPage();
        }
    }

    /**
     * Method to start the application.
     */
    @Override
    public void start() {
        loginPage();
    }

    /**
     * Method to set logged in user
     * @param user
     */
    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Class to handle the add category dialog
     */
    public class AddCategoryDialog extends JDialog {
        private JTextField categoryName;
        private JButton add;
        private JButton cancel;
        private JPanel buttonPanel;
        private JPanel mainPanel;
        private JPanel namePanel;
        private JLabel nameLabel;
        private JLabel errorLabel;

        /**
         * Constructor for the add category dialog
         *
         * @param parent the Frame from which the dialog is displayed
         * @param title  the String to display in the dialog's title bar
         * @param modal specifies whether dialog blocks user input to other top-level windows when shown. If true, the modality type property is set to DEFAULT_MODALITY_TYPE otherwise the dialog is modeless
         */
        public AddCategoryDialog(JFrame parent, String title, boolean modal) {
            super(parent, title, modal);
            this.mainPanel = new JPanel();
            mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
            this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            this.namePanel = new JPanel();
            this.namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.nameLabel = new JLabel("Category name:");
            nameLabel.setFont(new Font("ariel", Font.BOLD, 22));
            this.namePanel.add(nameLabel);
            this.categoryName = new JTextField(20);
            categoryName.setFont(new Font("Arial", Font.PLAIN, 24));
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
            add.setPreferredSize(new Dimension(150, 40));
            add.setFont(new Font("Arial", Font.PLAIN, 26));
            this.cancel = new JButton("Cancel");
            this.cancel.addActionListener(actionEvent -> {
                this.dispose();
            });
            cancel.setPreferredSize(new Dimension(150, 40));
            cancel.setFont(new Font("Arial", Font.PLAIN, 26));
            this.buttonPanel.add(cancel);
            this.buttonPanel.add(add);
            this.mainPanel.add(buttonPanel);

            this.setContentPane(this.mainPanel);
            this.setSize(680, 300);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }

    }

}

