package dao;

import model.Event;
import model.enums.EventType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface EventDao {

    List<Event> getAllEvent(int pageNumber, int pageSize) throws SQLException;
    boolean addEvent(Event event) throws SQLException;
    List<Event> searchEventByCode(String code) throws SQLException;
    List<Event> searchEventByName(String name) throws SQLException;
    List<Event> searchEventByType(String type) throws SQLException;
    List<Event> searchEventByStartedDate(LocalDate date) throws SQLException;
    boolean updateEvent(Event event) throws SQLException;
    boolean deleteEvent(String code) throws SQLException;
}
