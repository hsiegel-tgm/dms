package dms.control;

import dms.DmsSession;
import dms.exceptions.DatabaseException;
import dms.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Manages the interaction with the User class
 * @author Dominik Scholz
 * @version 0.3
 */
public class UserManager {
    
    private DmsSession session;
    private DataManager manager;
    
    public UserManager(DmsSession session) {
        this.session = session;
        manager = session.getDataManager();
    }
    
    /**
     * Registers a new User
     * @param name
     * @param email
     * @param password
     * @return the User variable assosciated with the User
     */
    public User registerUser(String name, String email, byte[] password) throws DatabaseException {
        //Check if user is alreay in the database
        if(!manager.executeQuery("getUser",name).isEmpty()) throw new DatabaseException("Username already registerd");
        
        //Creating new user
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(hash(password));
        
        //Saving new user
        DataManager.get().save(user);
        
        return user;
    }

    /**
     * Logs a User in
     * @param name
     * @param password
     * @return the User variable assosciated with the User
     * @throws SigmaSchedulerException 
     */
    public User loginUser(String name, byte[] password) throws DatabaseException {
        if(name.equals("")) throw new DatabaseException("Empty Name!");
        if(password.length == 0) throw new DatabaseException("Empty Password!");
        
        List<User> result = manager.executeQuery("getUser",name);
        
        //Check if user is registerd
        if(result.isEmpty()) throw new DatabaseException("Username not registerd");
        
        User user = (User) result.get(0);
        
        //Check if the password is right
        if(!Arrays.equals(user.getPassword(), hash(password))) throw new DatabaseException("Wrong password");
        
        //Setting the user for the current session
        session.setUser(user);
        return user;
    }
    
    /**
     * Returns all Users of this application
     * @return all Users of this application
     * @throws SigmaSchedulerException 
     */
    public List<User> getAllUsers() throws DatabaseException {
        return manager.executeQuery("getAllUsers");
    }
    
    /**
     * Searches the database after users with the given name as regexp or like
     * @param name
     * @return all matching users
     */
    public List<User> searchUser(String name) {
//        hql query to search all users with regular expression "name"
        return null;
    }

    /**
     * Helper method to hash the passwort, TODO: Salting
     * @param password
     * @return
     * @throws SigmaSchedulerException 
     */
    private byte[] hash (byte[] password) throws DatabaseException {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            throw new DatabaseException("Hash Problem, should never ever happen :/");
        }
        //Salt hash maybe?
        return messageDigest.digest(password);
    }
    
}
