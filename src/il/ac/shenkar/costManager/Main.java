package il.ac.shenkar.costManager;

public class Main {

    public static void main(String[] args) {
        try {
            SimpleDBModel m = new SimpleDBModel();
            User user = m.login("arthur.dent@email.com","12345");
            System.out.println(user.getName());
            System.out.println(m.getItems(user));
//            m.addItem(new Item("aa",2));
//            m.getItems(new User("koral",1));
        } catch (CostItemException e) {
            e.printStackTrace();
        }

    }
}
