package dms.exceptions;

/**
 * Exception for Input problems
 * @author Dominik
 * @version 0.1
 */
public class InputException extends Exception {
    
    private String message;
    
    public InputException(Exception ex) {
        this.message = ex.getMessage();
    }
    
    public InputException(String message) {
        this.message = message;
    }
        
    @Override
    public String getMessage() {
        return message;
    }
}
