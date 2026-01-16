package service;

import dao.EventDao;
import dao.EventDaoImpl;
import model.Event;
import model.enums.EventType;
import util.ViewUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventServiceImpl implements EventService {
    private final EventDao eventDao = new EventDaoImpl();
    @Override
    public List<Event> getAllEvent(int pageNumber, int pageSize) {
        try{

            return eventDao.getAllEvent(pageNumber,  pageSize);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEvent(Event event) {
        try{
            if(eventDao.addEvent(event)){
                System.out.println("Event added successfully !");
            }else {
                throw new RuntimeException("Failed To Added New Event !");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> searchEventByCode(String code) {
       try {
           List<Event> events = eventDao.searchEventByCode(code);
           if(events==null){
               throw new RuntimeException("Event doesn't Exist ! ");
           }
           return events;
       }catch (SQLException e){
            throw new RuntimeException(e);
       }
    }

    @Override
    public List<Event> searchEventByName(String name) {
        try {
            return eventDao.searchEventByName(name);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> searchEventByType(String type) {
        try {
            return eventDao.searchEventByType(type);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> searchEventByStartedDate(LocalDate date) {
        try {
            return eventDao.searchEventByStartedDate(date);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEvent( Event event) {
        try {
            if(eventDao.updateEvent(event)){
                ViewUtil.printHeader("Event Code [ " + event.getEventCode() + " ] Updated Successfully!");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEvent(String code) {
        try {
            if(eventDao.deleteEvent(code)){
                ViewUtil.printHeader("Event Deleted Successfully! " );
            }else {
                throw new RuntimeException("Failed To Deleted The Event ! ");
            }
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
