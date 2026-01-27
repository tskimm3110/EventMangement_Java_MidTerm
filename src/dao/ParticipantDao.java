package dao;

import model.Participant;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.List;

public interface ParticipantDao {
    List<Participant> getAllParticipant(int pageNumber, int pageSize) throws SQLException;
    boolean addParticipant(Participant participant) throws SQLException;
    boolean markAttended(String attended ,String code) throws SQLException;
    boolean payEvent(String payType ,String code) throws SQLException;
    Participant findParticipantByCode(String code) throws SQLException;
    boolean findParticipantByPhone(String phone) throws SQLException;
    List<Participant> searchParticipantByName(String name) throws SQLException;
    Participant searchParticipantByCode(String code) throws SQLException;
    Participant searchParticipantByPhoneNumber(String phoneNumber) throws SQLException;
    List<Participant> searchParticipantByEvent(Integer eventId) throws SQLException;
    boolean deleteParticipantByCode(String code) throws SQLException;
    boolean updateParticipant(Participant participant) throws SQLException;
}
