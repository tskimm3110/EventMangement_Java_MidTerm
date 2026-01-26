package dao;

import config.DbConfig;
import model.Event;
import model.enums.EventStatus;
import model.enums.EventType;
import service.EventService;
import service.EventServiceImpl;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao{
    private final Connection conn;

    public EventDaoImpl(){
        conn = DbConfig.getInstace();
    }

    @Override
    public List<Event> getAllEvent(int pageNumber, int pageSize) throws SQLException {

        String SQL = """
                    SELECT * FROM event ORDER BY id LIMIT ? OFFSET ?
                    """;
        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setInt(1,pageSize);
        preparedStatement.setInt(2,(pageNumber-1)*pageSize);

        ResultSet rs = preparedStatement.executeQuery();
        List<Event> events = new ArrayList<>();
        while (rs.next()){
            Event event = retreive(rs);
            events.add(event);
        }

        return events;
    }

    @Override
    public boolean addEvent(Event event ) throws SQLException {
        String SQL = """
                INSERT INTO event
                (event_code,event_name,event_type,start_date,end_date,
                location,organizer_name,description,status,max_participant)
                VALUES
                (?,?,?,?,?,?,?,?, ?,?
                ); 
                """;

        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,event.getEventCode());
        pstm.setString(2,event.getEventName());
        pstm.setString(3,event.getEventType().toString());
        pstm.setDate(4, Date.valueOf(event.getStartDate()));
        pstm.setDate(5,Date.valueOf(event.getEndDate()));
        pstm.setString(6,event.getLocation());
        pstm.setString(7,event.getOrganizerName());
        pstm.setString(8,event.getDescription());
        pstm.setString(9,event.getStatus().toString());
        pstm.setInt(10,event.getMaxParticipant());

        return pstm.executeUpdate()>0;
    }

    @Override
    public  Event searchEventByCode(String code) throws SQLException {
        String SQL = """
                SELECT * FROM event WHERE event_code = ?    
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,code);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            Event event = retreive(rs);
            return event;
        }else {
            return null;
        }
    }

    @Override
    public Event searchEventByID(Integer id) throws SQLException {
        String SQL = """
                SELECT * FROM event WHERE id = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            Event event = retreive(rs);
            return event;
        }else {
            return null;
        }
    }

    @Override
    public  List<Event> searchEventByName(String name) throws SQLException {
        String SQL = """
                SELECT * FROM event WHERE event_name ILIKE ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,"%"+name+"%");
        ResultSet rs = pstm.executeQuery();
        List<Event> events = new ArrayList<>();
        while (rs.next()){
            Event event = retreive(rs);
            events.add(event);
        }
        return events;
    }

    @Override
    public List<Event> searchEventByType(String type) throws SQLException {
        String SQL = """
                SELECT * FROM event WHERE event_type = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1, type);
        ResultSet rs = pstm.executeQuery();
        List<Event> events = new ArrayList<>();
        while (rs.next()){
            Event event = retreive(rs);
            events.add(event);
        }
        return events;
    }

    @Override
    public List<Event> searchEventByStartedDate(LocalDate date) throws SQLException {
        String SQL = """
                SELECT * FROM event WHERE start_date = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setDate(1, Date.valueOf(date));
        ResultSet rs = pstm.executeQuery();
        List<Event> events = new ArrayList<>();
        while (rs.next()){
            Event event = retreive(rs);
            events.add(event);
        }
        return events;
    }

    @Override
    public boolean updateEvent(Event event) throws SQLException {
        String SQL = """
                UPDATE event SET
                 event_code = ?,
                 event_name = ?,
                 event_type = ?,
                 start_date = ?,
                 end_date = ?,
                 location = ?,
                 organizer_name = ?,
                 description = ?,
                 status = ?,
                 max_participant = ?
                WHERE event_code = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,event.getEventCode());
        pstm.setString(2,event.getEventName());
        pstm.setString(3,event.getEventType().toString());
        pstm.setDate(4, Date.valueOf(event.getStartDate()));
        pstm.setDate(5,Date.valueOf(event.getEndDate()));
        pstm.setString(6,event.getLocation());
        pstm.setString(7,event.getOrganizerName());
        pstm.setString(8,event.getDescription());
        pstm.setString(9,event.getStatus().toString());
        pstm.setInt(10,event.getMaxParticipant());
        pstm.setString(11,event.getEventCode());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteEvent(String code) throws SQLException {
        String SQL = """
                DELETE FROM event WHERE event_code = ? 
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,code);
        return pstm.executeUpdate()>0;
    }


    public static Event retreive(ResultSet rs) throws SQLException {
        Event event = Event.builder()
                .id(rs.getInt("id"))
                .eventCode(rs.getString("event_code"))
                .eventName(rs.getString("event_name"))
                .eventType(EventType.valueOf(rs.getString("event_type")))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .location(rs.getString("location"))
                .organizerName(rs.getString("organizer_name"))
                .description(rs.getString("description"))
                .status(EventStatus.valueOf(rs.getString("status")))
                .maxParticipant(rs.getInt("max_participant"))
                .build();
        return event;
    }
}
