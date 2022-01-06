package il.ac.shenkar.costManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class SimpleView implements IView{
    private IViewModel viewModel;

    private String[] columnNames = {"Name", "Cost", "Date"};
    // UI components
    private JFrame frame;
    private JTextField inputField;
    private JButton btnSend;
    private JPanel inputPanel;
    private JPanel itemsTablePanel;
    private JTable itemsTable;
    private JScrollPane messagesScroller;
    private JPanel header;
    private JLabel applicationTitle;

    // navbar
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    // dropdown
    private JComboBox<String> dropdown;

    private JPanel navbar;

    private Font font = new Font("ariel", Font.PLAIN, 22);


    public void init() {
        frame = new JFrame("Cost Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // header
        header = new JPanel();
        header.setLayout(new FlowLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(600, 50));
        applicationTitle = new JLabel("Cost Manager");
        applicationTitle.setFont(font);
        // navbar
        navbar = new JPanel();
        navbar.setLayout(new FlowLayout());
        navbar.setBackground(Color.WHITE);
        navbar.setPreferredSize(new Dimension(600, 50));
        // dropdown
        dropdown = new JComboBox<>();
        dropdown.setPreferredSize(new Dimension(200, 30));
        dropdown.setFont(font);
        btnAdd = new JButton("Add");
        btnAdd.setFont(font);
        btnAdd.setPreferredSize(new Dimension(50, 30));
        btnEdit = new JButton("Edit");
        btnEdit.setFont(font);
        btnEdit.setPreferredSize(new Dimension(50, 30));
        btnDelete = new JButton("Delete");
        btnDelete.setFont(font);
        btnDelete.setPreferredSize(new Dimension(50, 30));
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(font);
        btnRefresh.setPreferredSize(new Dimension(50, 30));
        navbar.add(btnAdd);
        navbar.add(btnEdit);
        navbar.add(btnDelete);
        navbar.add(btnRefresh);
        navbar.add(dropdown);
        header.add(navbar);
        header.add(applicationTitle);
        frame.add(header, BorderLayout.NORTH);


        // input panel
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setPreferredSize(new Dimension(600, 50));
        inputField = new JTextField(30);
        inputField.setFont(font);
        inputField.setPreferredSize(new Dimension(600, 50));
        btnSend = new JButton("Send");
        btnSend.setFont(font);
        btnSend.setPreferredSize(new Dimension(100, 50));
        inputPanel.add(inputField);
        inputPanel.add(btnSend);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // items table panel
        itemsTablePanel = new JPanel();
        itemsTablePanel.setLayout(new FlowLayout());
        itemsTablePanel.setBackground(Color.WHITE);
        itemsTablePanel.setPreferredSize(new Dimension(600, 300));
        itemsTable = new JTable(new String[][]{columnNames,columnNames,columnNames,columnNames}, columnNames);
        itemsTable.setFont(font);
        itemsTable.setPreferredSize(new Dimension(600, 300));
        messagesScroller = new JScrollPane(itemsTable);
        messagesScroller.setPreferredSize(new Dimension(600, 300));
        itemsTablePanel.add(messagesScroller);
        frame.add(itemsTablePanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        SimpleView simpleView = new SimpleView();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                simpleView.init();
                simpleView.show();
            }
        });

//        try {
//            Thread.sleep(3000);
//            simpleView.displayError("Test error",false);
//            simpleView.displayMessage("Test message", "Test title");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            Thread.sleep(3000);
//            simpleView.displayError("Test error",true);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void show() {
        frame.setVisible(true);
    }


    @Override
    public void displayData(String data) {

    }

    @Override
    public void displayError(String error, Boolean shouldExit) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(frame,error,"Error!", JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (shouldExit) {
                System.exit(1);
            }
        }
    }

    @Override
    public void displayMessage(String message, String title) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(frame,message,title, JOptionPane.INFORMATION_MESSAGE);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void setItems(Collection<Item> items) {

    }

    @Override
    public void setCategories(Collection<Category> categories) {

    }
}
