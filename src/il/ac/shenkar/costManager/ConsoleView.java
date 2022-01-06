package il.ac.shenkar.costManager;

import java.util.Collection;
import java.util.LinkedList;

public class ConsoleView implements IView{
    private IViewModel viewModel;
    private LinkedList<Item> items;

    @Override
    public void displayData(String data) {
        System.out.println(data);
        for(Item item : items){
            System.out.println(item.getName()+" "+item.getCost()+" "+item.getCategory());
        }
    }

    @Override
    public void displayError(String error, Boolean shouldExit) {
        System.out.println("Error! " + error);
    }

    @Override
    public void displayMessage(String message, String title) {
        System.out.println("Message: "+message);

    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void setItems(Collection<Item> items) {
        this.items = new LinkedList<Item>(items);

    }

    @Override
    public void setCategories(Collection<Category> categories) {

    }
}
