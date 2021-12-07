package il.ac.shenkar.costManager;

public class Main {

    public static void main(String[] args) {
        try {
            SimpleDBModel m = new SimpleDBModel();
            m.getItems();
            m.addItem(new Item("aa",2));
            m.getItems();
        } catch (CostItemException e) {
            e.printStackTrace();
        }

    }
}
