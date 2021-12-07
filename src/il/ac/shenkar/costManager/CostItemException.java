package il.ac.shenkar.costManager;

public class CostItemException extends Exception {

    public CostItemException(String message) {
        super(message);
    }

    public CostItemException(String message, Throwable cause) {
        super(message, cause);
    }
}