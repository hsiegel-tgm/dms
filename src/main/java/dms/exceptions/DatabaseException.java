package dms.exceptions;

/**
 * Exception for Database problems
 * @author Dominik
 * @version 0.1
 */
public class DatabaseException extends Exception {
    
    private String message;
    
    public DatabaseException(Exception ex) {
        this.message = ex.getMessage();
    }
    
    public DatabaseException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
}
