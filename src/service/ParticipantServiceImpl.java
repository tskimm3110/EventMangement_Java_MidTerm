package service;

import dao.ParticipantDao;
import dao.ParticipantDaoImpl;
import model.Participant;
import telegram_bot.TelegramBot;
import util.ViewUtil;

import java.sql.SQLException;
import java.util.List;

public class ParticipantServiceImpl implements ParticipantService{
    private ParticipantDao participantDao = new ParticipantDaoImpl();
    @Override
    public List<Participant> getAllParticipants(int pageNumber, int pageSize) {
        try {
            return participantDao.getAllParticipant(pageNumber,pageSize);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addParticipant(Participant p) {
        try {
            String msg = "📌 New Participant Registration\n\n"
                    + "Code: " + p.getParticipantCode() + "\n"
                    + "Name: " + p.getFullName() + "\n"
                    + "Gender: " + p.getGender() + "\n"
                    + "Role: " + p.getRole() + "\n\n"
                    + "📍 Address: " + p.getAddress() + "\n"
                    + "📧 Email: " + p.getEmail() + "\n"
                    + "📞 Phone: " + p.getPhone() + "\n\n"
                    + "🗓 Registration Date: " + p.getRegistrationDate() + "\n"
                    + "💳 Payment Status: " + p.getPaymentStatus() + "\n"
                    + "✅ Attended: " + p.getIsAttended() + "\n\n"
                    + "📝 Remarks: " + p.getRemarks(); ;
            TelegramBot.sendMessage(msg);
            return participantDao.addParticipant(p);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean markAttended(String attende, String code) {
        try {
            if(participantDao.findParticipantByCode(code)!=null){
                return participantDao.markAttended(attende,code);
            }
            throw new RuntimeException("Participant Code Doesn't Exist !");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findParticipantByCode(String code) {
        try {
            if(participantDao.findParticipantByCode(code) != null){
                return true;
            }
            throw new RuntimeException("Participant Code Doesn't Exist !");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean payForEvent(String payType, String code) {
        try {
            if(participantDao.payEvent(payType,code)){
                return true;
            }
            throw new RuntimeException("Failed TO Update Payment !");
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<Participant> searchByName(String name) {
        try {
            return participantDao.searchParticipantByName(name);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Participant searchByCode(String code) {
        try {
            if(!findParticipantByCode(code)) throw  new RuntimeException("Participant with this code doesn't exist!");
            return participantDao.searchParticipantByCode(code);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Participant searchByPhoneNumber(String phoneNumber) {
        try {
            Participant participant = participantDao.searchParticipantByPhoneNumber(phoneNumber);
            if(participant==null) throw new RuntimeException("Participant With This Phone Number Doesn't Exist!");
            return participantDao.searchParticipantByPhoneNumber(phoneNumber);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Participant> searchByEvent(String eventName) {
        try {
            EventService eventService = new EventServiceImpl();
            if(eventService.searchEventByName(eventName) == null) throw new RuntimeException("No Participant In This Event!");

            return participantDao.searchParticipantByEvent(eventService.searchEventByName(eventName).getFirst().getId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteByCode(String code) {
        try {
            if(participantDao.findParticipantByCode(code) == null) throw new RuntimeException("Participant with this code doesn't exist!");
            return participantDao.deleteParticipantByCode(code);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(String code, Participant participant) {
        try {
            Participant pt =searchByCode(code);
            if(participant.getFullName()!=null) pt.setFullName(participant.getFullName());
            if(participant.getGender()!=null) pt.setGender(participant.getGender());
            if(participant.getAddress()!=null) pt.setAddress(participant.getAddress());
            if(participant.getRole()!=null) pt.setRole(participant.getRole());
            if(participant.getEmail()!=null) pt.setEmail(participant.getEmail());
            if(participant.getPhone()!=null) pt.setPhone(participant.getPhone());
            if(participant.getEventId()!=null) pt.setEventId(participant.getEventId());
            if(participant.getRegistrationDate()!=null) pt.setRegistrationDate(participant.getRegistrationDate());
//            if(participant.getPaymentStatus()!=null) pt.setPaymentStatus(participant.getPaymentStatus());
            if(participant.getRemarks()!=null) pt.setRemarks(participant.getRemarks());
//            if(participant.getIsAttended()!=null) pt.setIsAttended(participant.getIsAttended());

            if(participantDao.updateParticipant(pt)) ViewUtil.printHeader("Participant Code [ " + participant.getParticipantCode() + " ] Updated Successfully!");
            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
