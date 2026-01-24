package view;

import model.Event;
import model.Participant;
import model.enums.AttendanceStatus;
import model.enums.EventType;
import model.enums.Gender;
import model.enums.PaymentStatus;
import service.EventService;
import service.ParticipantService;
import util.InputUtil;
import util.ViewUtil;

import java.awt.image.SinglePixelPackedSampleModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ParticipantView {
    public void participantViewMenu(EventService eventService , ParticipantService participantService){
        do {
            ViewUtil.printMenuParticipant();
            String participantOpt = InputUtil.getText("Enter Option : ");
            switch (participantOpt){
                case "1":{
                    int pageNumber = 1;
                    int pageSize = 5;
                    Scanner sc = new Scanner(System.in);
                    while (true){
                        List<Participant> participants = participantService.getAllParticipants(pageNumber,pageSize);
                        ViewUtil.printParticipant(participants);

                        System.out.println("\nOptions: N = Next, P = Previous, Q = Quit");
                        String input = sc.nextLine().trim().toUpperCase();

                        if (input.equals("N")) {
                            pageNumber++;
                        } else if (input.equals("P") && pageNumber > 1) {
                            pageNumber--;
                        } else if (input.equals("Q")) {
                            break;
                        } else {
                            System.out.println("Invalid option.");
                        }
                    }
                    break;
                }
                case "2":{
                    break;
                }
                case "3":{
                    ViewUtil.printHeader("Register New Participant");
                    EventView.getEvent(eventService);
                    Integer id = null;
                    try {
                        String eventCode = InputUtil.getText("Enter Event Code : ");
                        id =eventService.searchEventByCode(eventCode).getId();

                    }catch (RuntimeException e){
                        ViewUtil.printHeader(e.getMessage());
                    }
                    if(id==null){
                        break;
                    }
                    String code = InputUtil.getText("Enter Participant Code : ");
                    String fullName = InputUtil.getText("Enter Participant Name : ");
                    String gender = String.valueOf(InputUtil.getTextWithEnum(Gender.class,"Enter Participant Gender : "));
                    String address = InputUtil.getText("Enter Participant Address : ");
                    String role = InputUtil.getText("Enter Participant Role : ");
                    String email = InputUtil.getText("Enter Email : ");
                    String phone = InputUtil.getText("Enter Phone Number : ");

                    LocalDate registrationDate = null;
                    while (true) {
                        String input = InputUtil.getText(
                                "Event Registration Date (yyyy-MM-dd)   : "
                        );

                        try {
                            registrationDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date. Example: 2999-12-02");
                        }
                    }


                    String paymentStatus = String.valueOf(InputUtil.getTextWithEnum(PaymentStatus.class,"Enter Payment Status : "));
                    String remarks = InputUtil.getText("Enter Remarks : ");
                    String isAttend = AttendanceStatus.PENDING.toString();

                    Participant participant = Participant.builder()
                            .participantCode(code)
                            .fullName(fullName)
                            .gender(Gender.valueOf(gender))
                            .address(address)
                            .role(role)
                            .email(email)
                            .phone(phone)
                            .eventId(id)
                            .registrationDate(registrationDate)
                            .paymentStatus(PaymentStatus.valueOf(paymentStatus))
                            .remarks(remarks)
                            .isAttended(AttendanceStatus.valueOf(isAttend))
                            .build();

                    participantService.addParticipant(participant);
                    break;
                }
                case "7":{
                   try {
                       ViewUtil.printHeader("Mark Attending");
                       String code = InputUtil.getText("Enter Participant Code : ");
                       if(!participantService.findParticipantByCode(code)){
                           return;
                       }
                       ViewUtil.printEnumAttended();
                       String att = String.valueOf(InputUtil.getTextWithEnum(AttendanceStatus.class,"Enter Attended Status ( 0 To Exit ) : "));
                       if(att.equals("null")){
                           break;
                       }
                       if(participantService.markAttended(att,code)){
                            ViewUtil.printHeader("Marked Attendance : " + att  + " Successfully! ");
                        }
                    }catch (RuntimeException e){
                       ViewUtil.printHeader(e.getMessage());
                   }
                    break;
                }
                case "8":{
                    try {
                        ViewUtil.printHeader("Participant Pay For Event");
                        String code = InputUtil.getText("Enter Participant Code");
                        if(!participantService.findParticipantByCode(code)){
                            return;
                        }
                        ViewUtil.printEnumPaymentStatus();
                        String payment = String.valueOf(InputUtil.getTextWithEnum(PaymentStatus.class,"Enter Payment Status ( 0 To Exit ) : "));
                        if(payment.equals("null")){
                            break;
                        }
                        if(participantService.payForEvent(payment,code)  ){
                            ViewUtil.printHeader("Payment status has been change to : " + payment + " !");
                        }
                    }catch (RuntimeException e){
                        ViewUtil.printHeader(e.getMessage());
                    }
                    break;
                }
                case "0": return;
            }
        }while (true);
    }



}
