package il.ac.shenkar.costManager;

/**
 * CostItemException
 * an exception that provides information about CostManager errors or other errors
 */
public class CostManagerException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public CostManagerException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause the underlying cause for this CostManagerException. The cause is saved for later retrieval by the {@link #getCause()} method.
     */
    public CostManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}