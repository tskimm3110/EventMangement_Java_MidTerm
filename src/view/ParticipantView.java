package view;

import dao.ParticipantDao;
import model.Event;
import model.Participant;
import model.enums.AttendanceStatus;
import model.enums.EventType;
import model.enums.Gender;
import model.enums.PaymentStatus;
import service.EventService;
import service.EventServiceImpl;
import service.ParticipantService;
import util.InputUtil;
import util.ViewUtil;

import javax.swing.*;
import java.awt.image.SinglePixelPackedSampleModel;
import java.sql.SQLOutput;
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
                case "1": {
                    int pageNumber = 1;
                    int pageSize = 5;
                    Scanner sc = new Scanner(System.in);

                    while (true) {
                        List<Participant> participants =
                                participantService.getAllParticipants(pageNumber, pageSize);

                        if (participants.isEmpty()) {
                            System.out.println("No participants found.");
                            break;
                        }

                        ViewUtil.printParticipant(participants);

                        System.out.println("\nOptions: N = Next, P = Previous, Q = Quit");
                        String input = sc.nextLine().trim().toUpperCase();

                        if (input.equals("N")) {
                            if (participants.size() < pageSize) {
                                System.out.println("This is the last page.");
                            } else {
                                pageNumber++;
                            }

                        } else if (input.equals("P")) {
                            if (pageNumber > 1) {
                                pageNumber--;
                            } else {
                                System.out.println("This is the first page.");
                            }

                        } else if (input.equals("Q")) {
                            break;

                        } else {
                            System.out.println("Invalid option.");
                        }
                    }
                    break;
                }

                case "2":{
                    ViewUtil.printSearchParticipantMenu();
                    String searchOpt = InputUtil.getText("Search By : ");

                    switch (searchOpt){
                        case "1":{
                            String name = InputUtil.getText("Enter Participant Name : ");
                            try {
                                ViewUtil.printParticipantDetail(participantService.searchByName(name));
                            }catch (RuntimeException e){
                                ViewUtil.printHeader(e.getMessage());
                            }
                            break;
                        }
                        case "2":{
                            String code = InputUtil.getText("Enter Participant Code : ");
                            try {
                                ViewUtil.printParticipantDetail(List.of(participantService.searchByCode(code)));
                            }catch (RuntimeException e){
                                ViewUtil.printHeader(e.getMessage());
                            }
                            break;
                        }
                        case "3":{
                            String phoneNumber = InputUtil.getText("Enter Participant Phone Number : ");
                            try {
                                ViewUtil.printParticipantDetail(List.of(participantService.searchByPhoneNumber(phoneNumber)));
                            }catch (RuntimeException e) {
                                ViewUtil.printHeader(e.getMessage());
                            }
                            break;
                        }
                        case "4":{
                            String eventName= InputUtil.getText("Enter Event Name : ");
                            try {
                                ViewUtil.printParticipantDetail(participantService.searchByEvent(eventName));
                            }catch (RuntimeException e){
                                ViewUtil.printHeader(e.getMessage());
                            }
                            break;
                        }
                        case "0":break;
                    }
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
                    String email;
                    while (true) {
                        email = InputUtil.getText("Enter Email : ");
                        if (ViewUtil.isValidEmail(email)) {
                            break;
                        } else {
                            System.out.println("Invalid email! Please enter a valid email address.");
                        }
                    }

                    String phone;
                    while (true) {
                        phone = InputUtil.getText("Enter Phone Number : ");
                        if (InputUtil.isValidPhone(phone)) {
                            break;
                        } else {
                            ViewUtil.printHeader("Invalid phone number! Digits only (8–15 numbers).");
                        }
                    }
                    if(participantService.findByPhone(phone) == false){
                        ViewUtil.printHeader("Phone number already registered!");
                        break;
                    }

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
                case "4":{
                    ViewUtil.printHeader("Update Participant");
                   try {
                       String code = InputUtil.getText("Enter Participant Code : ");
                       if(!participantService.findParticipantByCode(code)) break;
                       ViewUtil.printParticipantDetail(List.of(participantService.searchByCode(code)));
                       String name = InputUtil.getText("Enter New Name [ 0 To Skip ] : ");
                       if(name.equals("0")) name=null;
                       Gender gender = InputUtil.getTextWithEnum(Gender.class,"Enter Participant Gender [ 0 To Skip ] : ");
                       String address = InputUtil.getText("Enter New Address [ 0 To Skip ] : ");
                       if(address.equals("0")) address =null;
                       String role = InputUtil.getText("Enter New Role [ 0 To Skip ] : ");
                       if(role.equals("0")) role=null;
                       String email = InputUtil.getText("Enter New Email [ 0 To Skip ] : ");
                       if(email.equals("0")) email=null;
                       String phone = InputUtil.getText("Enter New Phone [ 0 To Skip ] : ");
                       if(phone.equals("0")) phone=null;
                        EventView.getEvent(eventService);

//                        String eventCode = InputUtil.getText("Enter New Event By Code [ 0 To Skip ] : ");
//                       if(eventCode.equals("0")) eventCode=null;
//                       Integer id = null;
//                       Event event = eventService.searchEventByCode(eventCode);
//                       if(event == null) break;
//                        id = event.getId();

                       Integer id = null;

                       while (true) {
                           String eventCode = InputUtil.getText(
                                   "Enter New Event By Code [ 0 To Skip ] : "
                           );
                           if (eventCode.equals("0")) {
                               break;
                           }
                           try {
                               Event event = eventService.searchEventByCode(eventCode);
                               id = event.getId();
                               break;
                           } catch (RuntimeException e) {
                               ViewUtil.printHeader(e.getMessage());
                           }
                       }


                       LocalDate registerDate = null;
                       while (true) {
                           String input = InputUtil.getText( "Enter New Registration Date (yyyy-MM-dd) [0 To Skip]  : ");
                           try {
                               if(input.equals("0"))break;
                               registerDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                               break;
                           } catch (DateTimeParseException e) {
                               System.out.println("Invalid date. Example: 2999-12-02");
                           }
                       }

                       String remark = InputUtil.getText("Enter New Remarks [ 0 To Skip ] : ");
                       if(remark.equals("0")) remark=null;

                       System.out.println(id);

                       Participant participant = Participant.builder()
                               .participantCode(code)
                               .fullName(name)
                               .gender(gender)
                               .email(email)
                               .phone(phone)
                               .role(role)
                               .eventId(id)
                               .registrationDate(registerDate)
                               .remarks(remark)
                               .build();
                       participantService.update(code,participant);
                   }catch (RuntimeException e){
                       ViewUtil.printHeader(e.getMessage());
                   }

                    break;
                }
                case "5":{
                    try {
                        String code = InputUtil.getText("Enter Participant Code : ");
                        participantService.deleteByCode(code);
                        ViewUtil.printHeader("Participant Record Deleted Successfully!");
                    }catch (RuntimeException e){
                        ViewUtil.printHeader(e.getMessage());
                    }
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
