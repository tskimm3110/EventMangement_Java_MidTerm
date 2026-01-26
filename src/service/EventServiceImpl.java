package service;

import dao.EventDao;
import dao.EventDaoImpl;
import model.Event;
import model.enums.EventType;
import telegram_bot.TelegramBot;
import util.ViewUtil;

import java.io.IOException;
import java.net.MalformedURLException;
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
                String msg = "📢 New Event Added!\n\n"
                        + "🆔 Event Code: " + event.getEventCode() + "\n"
                        + "📛 Event Name: " + event.getEventName() + "\n"
                        + "📂 Type: " + event.getEventType() + "\n\n"
                        + "📅 Start Date: " + event.getStartDate() + "\n"
                        + "📅 End Date: " + event.getEndDate() + "\n\n"
                        + "📍 Location: " + event.getLocation() + "\n"
                        + "👤 Organizer: " + event.getOrganizerName() + "\n\n"
                        + "👥 Max Participants: " + event.getMaxParticipant() + "\n"
                        + "📌 Status: " + event.getStatus() + "\n\n"
                        + "📝 Description:\n"
                        + event.getDescription();
                TelegramBot.sendMessage(msg);
            }else {
                throw new RuntimeException("Failed To Added New Event !");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event searchEventByCode(String code) {
       try {
           Event event = eventDao.searchEventByCode(code);
           if(event==null){
               throw new RuntimeException("Event doesn't Exist ! ");
           }
           return event;
       }catch (SQLException e){
            throw new RuntimeException(e);
       }
    }

    @Override
    public Event searchEventById(Integer id) {
        try {
            Event event = eventDao.searchEventByID(id);
            if(event==null){
                throw new RuntimeException("Event doesn't Exist ! ");
            }
            return event;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event searchEventByCodeForAdd(String code) {
        try {
            return eventDao.searchEventByCode(code);
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
    public void updateEvent( String code, Event event) {
        try {
            Event ev = searchEventByCode(code);
            if(event.getEventName()!=null) ev.setEventName(event.getEventName());
            if(event.getEventType()!=null) ev.setEventType(event.getEventType());
            if(event.getStartDate()!=null) ev.setStartDate(event.getStartDate());
            if(event.getEndDate()!=null) ev.setEndDate(event.getEndDate());
            if(event.getLocation()!=null) ev.setLocation(event.getLocation());
            if(event.getOrganizerName()!=null) ev.setOrganizerName(event.getOrganizerName());
            if(event.getDescription()!=null) ev.setDescription(event.getDescription());
            if(event.getStatus()!=null) ev.setStatus(event.getStatus());
            if(event.getMaxParticipant()!=null) ev.setMaxParticipant(event.getMaxParticipant());


            if(eventDao.updateEvent(ev)) ViewUtil.printHeader("Event Code [ " + event.getEventCode() + " ] Updated Successfully!");

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
