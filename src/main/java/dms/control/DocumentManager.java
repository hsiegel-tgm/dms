package dms.control;

import dms.DmsSession;
import dms.exceptions.DatabaseException;
import dms.model.Document;
import dms.model.User;
import java.util.List;

/**
 * Manages the interaction with the Event class
 * @author Dominik Scholz
 * @version 0.3
 */
public class DocumentManager {
    
    private DmsSession session;
    private DataManager dataManager;
    
    public DocumentManager(DmsSession session) {
        this.session = session;
        dataManager = session.getDataManager();
    }
//    
//    /**
//     * Updates an event
//     * @param name
//     * @param description
//     * @param allowMultipleVotes
//     * @param dates
//     * @param member
//     * @param event
//     * @return the updated event (useless bcause given as param)
//     * @throws SigmaSchedulerException 
//     */
//    public Event updateEvent(String name, String description, boolean allowMultipleVotes, List<Date> dates, Set<User> member, Event event) throws SigmaSchedulerException {
//        //Creating new event
//        event.setManager(session.getUser());
//        event.setName(name);
//        event.setDescription(description);
//        
//        event.setAllowMultipleVotes(allowMultipleVotes);
//        event.setMember(member);
//        
//        //Parsing vote dates
//        Set<VoteDate> voteDates = new HashSet<VoteDate>();
//        VoteDate voteDate;
//        for(Date date : dates) {
//            voteDate = new VoteDate();
//            voteDate.setDate(date);
//            voteDate.setVoter(new HashSet<User>());
//            voteDates.add(voteDate);
//        }
//        event.setVoteDates(voteDates);
//        
//        //Saving the new event
//        dataManager.save(event);
//        
//        return event;
//    }
//    
//    /**
//     * Creates a new event
//     * @param name
//     * @param description
//     * @param allowMultipleVotes
//     * @param dates
//     * @param member
//     * @return the new event
//     * @throws SigmaSchedulerException 
//     */
//    public Event createEvent(String name, String description, boolean allowMultipleVotes, List<Date> dates, Set<User> member) throws SigmaSchedulerException {
//        //Creating new event
//        Event event = new Event();
//        event.setManager(session.getUser());
//        event.setName(name);
//        event.setDescription(description);
//        event.setState(EventState.UNPUBLISHED);
//        
//        event.setCreateDate(new Date());
//        event.setAllowMultipleVotes(allowMultipleVotes);
//        event.setComments(new HashSet<Comment>());
//        event.setMember(member);
//        
//        //Parsing vote dates
//        Set<VoteDate> voteDates = new HashSet<VoteDate>();
//        VoteDate voteDate;
//        for(Date date : dates) {
//            voteDate = new VoteDate();
//            voteDate.setDate(date);
//            voteDate.setVoter(new HashSet<User>());
//            voteDates.add(voteDate);
//        }
//        event.setVoteDates(voteDates);
//        
//        //Saving the new event
//        dataManager.save(event);
//        
//        return event;
//    }
//    
    /**
     * Returns all events managed by the given user
     * @param manager the manager of the events
     * @return a list of all events managed by the given user
     */
    public List<Document> getManagedDocuments(User manager) throws DatabaseException {
        return dataManager.executeQuery("getManagedDocuments",manager);
    }
//
    /**
     * Deletes an event
     * @param event
     * @throws SigmaSchedulerException 
     */
    public void delete(Document document) throws DatabaseException {
        dataManager.delete(document);
    }
//
//    /**
//     * Publishes an event
//     * @param event
//     * @throws SigmaSchedulerException 
//     */
//    public void publishEvent(Event event) throws SigmaSchedulerException {
//        event.setState(EventState.VOTEABLE);
//        dataManager.save(event);
//    }
//    
}
