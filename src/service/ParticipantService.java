package service;

import dao.ParticipantDaoImpl;
import model.Participant;

import java.util.List;

public interface ParticipantService {
    List<Participant> getAllParticipants(int pageNumber, int pageSize);
    boolean addParticipant(Participant participant);
    boolean markAttended(String attende, String ccode);
    boolean findParticipantByCode(String code);
    boolean payForEvent(String payType, String code);
    List<Participant> searchByName(String name);
    Participant searchByCode(String code);
    Participant searchByPhoneNumber(String phoneNumber);
    List<Participant> searchByEvent(String eventName);
    boolean deleteByCode(String code);
    boolean update(String code,Participant participant);
}
