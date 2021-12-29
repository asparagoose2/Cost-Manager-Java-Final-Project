package il.ac.shenkar.costManager;

import java.util.Collection;

/**
 * The interface View. This interface is used to define the methods that the view will use.
 */
public interface IView {
    /**
     * The method is used to display the data in the view.
     * @param data the data to display.
     */
    public void displayData(String data);

    /**
     * The method is used to display the error in the view.
     * @param error the error to display.
     */
    public void displayError(String error);

    /**
     * The method is used to display the message in the view.
     * @param message the message to display.
     */
    public void displayMessage(String message);

    /**
     * The method is used to set the ViewModel.
     * @param viewModel the ViewModel to set.
     */
    public void setViewModel(IViewModel viewModel);

    /**
     * The method is used by the ViewModel to set the items to display.
     * @param items the items to display.
     */
    public void setItems(Collection<Item> items);

    /**
     * The method is used by the ViewModel to set the categories to display.
     * @param categories the categories to display.
     */
    public void setCategories(Collection<Category> categories);
}
