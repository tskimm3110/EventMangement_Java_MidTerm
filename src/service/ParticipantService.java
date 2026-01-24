package service;

import model.Participant;

import java.util.List;

public interface ParticipantService {
    List<Participant> getAllParticipants(int pageNumber, int pageSize);
    boolean addParticipant(Participant participant);
    boolean markAttended(String attende, String ccode);
    boolean findParticipantByCode(String code);
    boolean payForEvent(String payType, String code);
}
