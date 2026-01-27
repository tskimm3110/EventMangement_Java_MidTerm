package dao;

import config.DbConfig;
import model.Event;
import model.Participant;
import model.enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDaoImpl implements ParticipantDao{
    private Connection conn;
    public ParticipantDaoImpl(){
        conn= DbConfig.getInstace();
    }
    @Override
    public List<Participant> getAllParticipant(int pageNumber, int pageSize) throws SQLException {
        String SQL = """
                    SELECT * FROM participant ORDER BY id DESC LIMIT ? OFFSET ?
                    """;
        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setInt(1,pageSize);
        preparedStatement.setInt(2,(pageNumber-1)*pageSize);
        ResultSet rs = preparedStatement.executeQuery();
        List<Participant> participants = new ArrayList<>();
        while (rs.next()){
            Participant participant = retreive(rs);
            participants.add(participant);
        }
        return participants;
    }

    @Override
    public boolean addParticipant(Participant participant) throws SQLException{
        String SQL = """
                INSERT INTO participant
                 ( participant_code, full_name, gender,
                 address, role, email, phone, event_id, registeration_date,
                 payment_status, remarks, is_attended)
                 VALUES
                 (?,?,?,?,?,?,?,?,?,?,?,?);           
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,participant.getParticipantCode());
        pstm.setString(2,participant.getFullName());
        pstm.setString(3,participant.getGender().toString());
        pstm.setString(4,participant.getAddress());
        pstm.setString(5,participant.getRole().toString());
        pstm.setString(6,participant.getEmail());
        pstm.setString(7,participant.getPhone());
        pstm.setInt(8,participant.getEventId());
        pstm.setDate(9, Date.valueOf(participant.getRegistrationDate()));
        pstm.setString(10,participant.getPaymentStatus().toString());
        pstm.setString(11,participant.getRemarks());
        pstm.setString(12,participant.getIsAttended().toString());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean markAttended(String attended ,String code) throws SQLException {
        String SQL = """
                UPDATE participant SET is_attended = ? WHERE participant_code =?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,attended);
        pstm.setString(2,code);
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean payEvent(String payType, String code) throws SQLException {
        String SQL = """
                UPDATE participant SET payment_status = ? WHERE participant_code =?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,payType);
        pstm.setString(2,code);
        return pstm.executeUpdate()>0;
    }

    @Override
    public Participant findParticipantByCode(String code) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE participant_code = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,code);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            Participant participant = retreive(rs);
            return participant;
        }
        return null;
    }

    @Override
    public boolean findParticipantByPhone(String phone) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE phone = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,phone);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return true;
        }
        System.out.println("Not exist!");
        return false;    }


    @Override
    public List<Participant> searchParticipantByName(String name) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE full_name ILIKE ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,"%"+name+"%");
        ResultSet rs = pstm.executeQuery();
        List<Participant> participants = new ArrayList<>();
        while (rs.next()){
            Participant participant = retreive(rs);
            participants.add(participant);
        }
        return participants;
    }

    @Override
    public Participant searchParticipantByCode(String code) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE participant_code = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,code);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            Participant participant = retreive(rs);
            return participant;
        }
        return null;
    }

    @Override
    public Participant searchParticipantByPhoneNumber(String phoneNumber) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE phone = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,phoneNumber);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            Participant participant = retreive(rs);
            return participant;
        }
        return null;    }

    @Override
    public List<Participant> searchParticipantByEvent(Integer eventId) throws SQLException {
        String SQL = """
                SELECT * FROM participant WHERE event_id = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1,eventId);
        ResultSet rs = pstm.executeQuery();
        List<Participant> participants = new ArrayList<>();
        while (rs.next()){
            Participant participant = retreive(rs);
            participants.add(participant);
        }
        return participants;    }

    @Override
    public boolean deleteParticipantByCode(String code) throws SQLException {
        String SQL = """
                    DELETE FROM participant WHERE participant_code = ? 
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,code);
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateParticipant(Participant participant) throws SQLException {
//        String SQL = """
//                UPDATE participant SET
//                  participant_code = ?,
//                  full_name = ?,
//                  gender = ?,
//                  address = ?,
//                  role = ?,
//                  email = ?,
//                  phone = ?,
//                  event_id =?,
//                  registeration_date =?,
//                  payment_status =?,
//                  remarks =?,
//                  is_attended= ?
//                 WHERE participant_code = ?
//                """;
        String SQL = """
                UPDATE participant SET
                  participant_code = ?, 
                  full_name = ?, 
                  gender = ?,
                  address = ?, 
                  role = ?, 
                  email = ?, 
                  phone = ?, 
                  event_id =?, 
                  registeration_date =?,
                  remarks =?
                 WHERE participant_code = ?
                """;
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setString(1,participant.getParticipantCode());
        pstm.setString(2,participant.getFullName());
        pstm.setString(3,participant.getGender().toString());
        pstm.setString(4,participant.getAddress());
        pstm.setString(5,participant.getRole());
        pstm.setString(6,participant.getEmail());
        pstm.setString(7,participant.getPhone());
        pstm.setInt(8,participant.getEventId());
        pstm.setDate(9, Date.valueOf(participant.getRegistrationDate()));
        pstm.setString(10,participant.getRemarks());
        pstm.setString(11,participant.getParticipantCode());
        return pstm.executeUpdate()>0;
    }


    public static Participant retreive(ResultSet rs) throws SQLException {
        Participant participant = Participant.builder().
                id(rs.getInt("id")).
                participantCode(rs.getString("participant_code"))
                .fullName(rs.getString("full_name"))
                .gender(Gender.valueOf(rs.getString("gender")))
                .address(rs.getString("address"))
                .role(rs.getString("role"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .eventId(rs.getInt("event_id"))
                .registrationDate(rs.getDate("registeration_date").toLocalDate())
                .paymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")))
                .remarks(rs.getString("remarks"))
                .isAttended(AttendanceStatus.valueOf(rs.getString("is_attended")))
                .build();
        return participant;
    }
}
