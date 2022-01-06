import il.ac.shenkar.costManager.IViewModel;
import il.ac.shenkar.costManager.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCostItem {
    private JPanel panel1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton saveButton;
    private JSpinner inputAmount;
    private JComboBox comboBox1;
    private JLabel inputName;
    private JLabel inputDate;

    public AddCostItem() {
        private IViewModel viewModel;
    
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = inputName.getText();
                String description = textField3.getText();
                String category = comboBox1.getSelectedItem().toString();
                int amount = (int) inputAmount.getValue();
                Item costItem = new Item(name, description, category, amount);
                viewModel.addItem(costItem);
                
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
