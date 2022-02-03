package il.ac.shenkar.costManager;

import java.util.*;

public class ConsoleView implements IView{
    private IViewModel viewModel;
    private LinkedList<Item> items;
    private ArrayList<Category> categories;
    private boolean isLoggedIn = false;



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
        this.categories = new ArrayList<Category>(categories);
    }

    @Override
    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public void setUser(User user) {

    }


    @Override
    public void start() {
        System.out.println("ConsoleView started");

        while(!isLoggedIn) {
            System.out.println("1) Login\n2) Register\n3) Exit");
            System.out.println("Please enter a command: ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    System.out.println("Please enter your email: ");
                    String email = scanner.nextLine();
                    System.out.println("Please enter your password: ");
                    String password = scanner.nextLine();
                    this.viewModel.login(email, password);
                    break;
                case "2":
                    System.out.println("Please enter your email: ");
                    String email2 = scanner.nextLine();
                    System.out.println("Please enter your password: ");
                    String password2 = scanner.nextLine();
                    System.out.println("Please enter your first name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Please enter your last name: ");
                    String lastName = scanner.nextLine();
                    this.viewModel.register(firstName, lastName, email2, password2);
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }

        System.out.println("\n Welcome to Cost Manager");
        while(true) {
            System.out.println("Please enter a command:");
            Scanner console = new Scanner(System.in);
            if (console == null) {
                System.out.println("No console.");
                System.exit(0);
            }
            String command = console.nextLine();
            if(command.equals("exit")){
                System.exit(0);
            }
            else if(command.equals("add")){
                System.out.println("Please enter a name:");
                String name = console.nextLine();
                System.out.println("Please enter a cost:");
                String cost = console.nextLine();
                System.out.println("Please select a category:");
                // print all gategories with index
                for(int i = 0; i < categories.size(); i++){
                    System.out.println(i+") "+categories.get(i).getName());
                }
                String category = console.nextLine();
                System.out.println("Please a description:");
                String description = console.nextLine();
                System.out.println("Please select currency:");
                for(int i = 0; i < IModel.CURRENCY.values().length; i++){
                    System.out.println(i+") "+IModel.CURRENCY.values()[i]);
                }
                String currency = console.nextLine();

                viewModel.addItem(name,Double.parseDouble(cost), categories.get(Integer.parseInt(category)), description ,IModel.CURRENCY.values()[Integer.parseInt(currency)].getValue(), new java.sql.Date(new Date().getTime()));
            }
            else if(command.equals("addCategory")){
                System.out.println("Please enter a name:");
                String name = console.nextLine();
                viewModel.addCategory(name);
            }
            else if(command.equals("delete")){
                System.out.println("Please enter an id:");
                String id = console.nextLine();
//                viewModel.removeCostItem(Integer.parseInt(id));
            }
            else if(command.equals("update")){
                System.out.println("Please enter an id:");
                String id = console.nextLine();
            }
            else if(command.equals("get")){
                viewModel.getItems();
            }
            else if(command.equals("getCategories")){
                for(Category category : categories){
                    System.out.println(category.getName());
                }
            }
            else if(command.equals("help")){
                System.out.println("add - add an item");
                System.out.println("delete - delete an item");
                System.out.println("update - update an item");
                System.out.println("get - get all items");
                System.out.println("exit - exit the program");
            }
            else if(command.equals("report")){
                Calendar cal = Calendar.getInstance();
                System.out.println(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                for (Item item : items) {
                    // check that date in the current month
                    cal.setTime(item.getDate());
                    if(cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) && cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)){
                        System.out.println(item.getName()+" "+item.getCost()+" "+item.getCurrency()+" "+item.getDate());
                    }
                }

            }
            else{
                System.out.println("Invalid command");
            }

            }
        }


    }


