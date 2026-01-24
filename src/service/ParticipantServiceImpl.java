package service;

import dao.ParticipantDao;
import dao.ParticipantDaoImpl;
import model.Participant;
import telegram_bot.TelegramBot;

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
}
