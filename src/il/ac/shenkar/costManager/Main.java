package il.ac.shenkar.costManager;

import javax.swing.*;

public class Main {
    static IView view;
    static IModel model;
    static IViewModel viewModel;

    public static void main(String[] args) {
        try {
            model = new SimpleDBModel();
            viewModel = new ViewModel();
            view = new GUI(viewModel);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        view.setViewModel(viewModel);
        viewModel.setModel(model);
        viewModel.setView(view);

        SwingUtilities.invokeLater(() -> view.start());

    }
}
