import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class EventManager implements Serializable {
    private HashMap<UUID, Event> events;

    /**
     * The constructor takes events and assigns the variable an appropriate value.
     *
     *
     */
    public EventManager(){
        this.events = new HashMap<>();
    }

    /**
     * Implements Getter, getEvents, for event IDs.
     *
     * @return event IDs for all scheduled events
     */

    public List<UUID> getEvents() {
        Collection<UUID> eventc = events.keySet();
        ArrayList<UUID> eventlist = new ArrayList<UUID>(eventc);
        return eventlist;
    }

    /**
     * Implements creator, createEvent, to instantiate an Event object.
     *
     * @return an Event object with assigned attributes as specified by the parameters.
     */
    public Event createEvent(String eventName, String speaker, String organizer, LocalDateTime startTime,
                             String roomName){
        return new Event(eventName, speaker, organizer, startTime, roomName);
    }

    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEvent(String eventName, String speaker, String organizer, LocalDateTime startTime,
                            String roomName){
        Event new_event = createEvent(eventName, speaker, organizer, startTime, roomName);
        LocalDateTime start = new_event.getStartTime();
        for(UUID id : events.keySet()){
            Event e = events.get(id);
            if (e.getStartTime() == start){
                return false;
            }
        }
        if (start.getHour() >= 9 && start.getHour() <= 16){
            events.put(new_event.getId(), new_event);
            return true;
        }else{
            return false;
        }
    }

    ///**
     //* Implements modifier, removeEvent, for events.
    // *
   //  * @return a boolean indicating if event was successfully removed
  //   */
//    public boolean removeEvent(Event event){
//        if (events.containsKey(event.getId())){
//            events.remove(event.getId());
//            return true;
//        }
//        return false;
//    } // for phase 2

    /**
     * Implements helper method, findEvent, to find event object when given its name.
     *
     * @return an Event object in hashmap of events associated with the given String eventName.
     */
//    private Event findEvent(String eventName){
//        for (Event e: events.values()){
//            if (e.getEventName().equals(eventName)){
//                return e;
//            }
//        }
//        return null;
//    }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
//        LocalDateTime s_event = event.getStartTime();
        if (event.getAttendees().contains(username)){
            return false;
        }
        // if room is full -> return false -> do in room manager?
//        for(UUID id : user.getEventsAttending()){
//            Event users_event = findEvent(id);
//            LocalDateTime s_user = users_event.getStartTime();
//            if (s_user.getHour() == s_event.getHour()){
//                return false;
//            }
//        } //assume conditions related to user are satisfied
        List<String> updated_event = event.getAttendees();
        updated_event.add(username);
        event.setAttendees(updated_event);
        return true;
    }

    /**
     * Implements modifier, removeAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully removed
     */
    public boolean removeAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        if (!(event.getAttendees().contains(username))){
            return false;
        }
        List<String> updated_event = event.getAttendees();
        if (updated_event.remove(username)){
            event.setAttendees(updated_event);
            // update room capacity?
            return true;
        }
        return false;

    }

}
