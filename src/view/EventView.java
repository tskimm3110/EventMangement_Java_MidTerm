package view;

import jdk.jshell.execution.Util;
import model.Event;
import model.enums.EventStatus;
import model.enums.EventType;
import service.EventService;
import service.EventServiceImpl;
import util.InputUtil;
import util.ViewUtil;

import javax.swing.text.View;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class EventView {


    public void eventViewMenu(EventService eventService){
        do{
            ViewUtil.printMenuEvent();
            String eventOpt = InputUtil.getText("Enter Option : ");
            switch (eventOpt){
                case "1": {
                    int pageNumber = 1;
                    int pageSize = 5;
                    Scanner sc = new Scanner(System.in);

                    while (true) {
                        List<Event> eventsPage = eventService.getAllEvent(pageNumber, pageSize);
                        ViewUtil.printEventList(eventsPage);

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
                    ViewUtil.printSearchEventMenu();
                    String searchOpt = InputUtil.getText("Search By : ");
                    if(searchOpt.equals("1")){
                        String code = InputUtil.getText("Enter Event Code : ");
                        try {
                            List<Event> event = eventService.searchEventByCode(code);
                            ViewUtil.printEventDetail(event);
                        }catch (RuntimeException e){
                            ViewUtil.printHeader(e.getMessage());
                        }
                    }
                    if(searchOpt.equals("2")){
                        String name = InputUtil.getText("Enter Event Name : ");
                        try {
                            List<Event> event = eventService.searchEventByName(name);
                            ViewUtil.printEventDetail(event);
                        }catch (RuntimeException e){
                            ViewUtil.printHeader(e.getMessage());
                        }
                    }
                    if(searchOpt.equals("3")){
                       try {
                           ViewUtil.printEnumType();
                           String eventType = String.valueOf(InputUtil.getTextWithEnum(EventType.class,"Enter Event Type : "));
                           ViewUtil.printEventDetail(eventService.searchEventByType(eventType));
                       }catch (RuntimeException e){
                           ViewUtil.printHeader(e.getMessage());
                       }
                    }
                    if(searchOpt.equals("4")){
                        try {
                            LocalDate startDate = null;
                            while (startDate == null) {
                                String input = InputUtil.getText(
                                        "Event Start Date (yyyy-MM-dd) : "
                                );

                                try {
                                    startDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid date. Example: 2999-12-02");
                                }
                            }

                            ViewUtil.printEventDetail(eventService.searchEventByStartedDate(startDate));
                        }catch (RuntimeException e){
                            ViewUtil.printHeader(e.getMessage());
                        }
                    }
                    break;
                }
                case "3" : {

                    ViewUtil.printHeader("Add New Event");
                    String code = InputUtil.getText("Event Code : ");
                    String name = InputUtil.getText("Event Name : ");



                    ViewUtil.printEnumType();
//                    String eventType = InputUtil.getText("Event Type : ");
                    String eventType = String.valueOf(InputUtil.getTextWithEnum(EventType.class,"Enter Event Type : "));
                    LocalDate startDate = null;
                    while (startDate == null) {
                        String input = InputUtil.getText(
                                "Event Start Date (yyyy-MM-dd) : "
                        );

                        try {
                            startDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date. Example: 2999-12-02");
                        }
                    }


//                    String endDate = InputUtil.getText("Event End Date (yyyy-MM-dd) : ");
//
//                    LocalDate endDateConverter = LocalDate.parse(
//                            endDate,  DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                    );


                    LocalDate endDate = null;
                    while (endDate == null) {
                        String input = InputUtil.getText(
                                "Event Start Date (yyyy-MM-dd) : "
                        );

                        try {
                            endDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date. Example: 2999-12-02");
                        }
                    }


                    String location = InputUtil.getText("Enter Location : ");
                    String organizerName = InputUtil.getText("Enter Organizer Name : ");
                    String description = InputUtil.getText("Enter Event Description : ");

                    ViewUtil.printEnumStatus();
//                    String eventStatus = InputUtil.getText("Enter Event Status : ");
                    String eventStatus = String.valueOf(InputUtil.getTextWithEnum(EventStatus.class,"Enter Event Status : "));

                    Integer maxParticipant = InputUtil.getIntegerMoreThanZero("Enter Max Participant : ");

                    try {

                        Event event = Event.builder()
                                .eventCode(code)
                                .eventName(name)
                                .eventType(EventType.valueOf(eventType.toUpperCase()))
                                .startDate(startDate)
                                .endDate(endDate)
                                .location(location)
                                .organizerName(organizerName)
                                .description(description)
                                .status(EventStatus.valueOf(eventStatus))
                                .maxParticipant(maxParticipant).build();
                        eventService.addEvent(event);

                    }catch (DateTimeException e){
                        System.out.println(e.getMessage());
                    }
                }
                case "4" : {
                  try {
                      ViewUtil.printHeader("Update Event");
                      String code = InputUtil.getText("Enter Event Code : ");

                      if(eventService.searchEventByCode(code)==null){
                          ViewUtil.printHeader("Event Doesn't Exist !");
                          break;
                      }

                      String name = InputUtil.getText("Event Name : ");
                      ViewUtil.printEnumType();
                      String eventType = String.valueOf(InputUtil.getTextWithEnum(EventType.class,"Enter Event Type : "));
                      LocalDate startDate = null;
                      while (startDate == null) {
                          String input = InputUtil.getText(
                                  "Event Start Date (yyyy-MM-dd) : "
                          );

                          try {
                              startDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                          } catch (DateTimeParseException e) {
                              System.out.println("Invalid date. Example: 2999-12-02");
                          }
                      }

                      LocalDate endDate = null;
                      while (endDate == null) {
                          String input = InputUtil.getText(
                                  "Event Start Date (yyyy-MM-dd) : "
                          );

                          try {
                              endDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                          } catch (DateTimeParseException e) {
                              System.out.println("Invalid date. Example: 2999-12-02");
                          }
                      }

                      String location = InputUtil.getText("Enter Location : ");
                      String organizerName = InputUtil.getText("Enter Organizer Name : ");
                      String description = InputUtil.getText("Enter Event Description : ");

                      ViewUtil.printEnumStatus();
                      String eventStatus = String.valueOf(InputUtil.getTextWithEnum(EventStatus.class,"Enter Event Status : "));

                      Integer maxParticipant = InputUtil.getIntegerMoreThanZero("Enter Max Participant : ");

                      Event event = Event.builder()
                              .eventCode(code)
                              .eventName(name)
                              .eventType(EventType.valueOf(eventType.toUpperCase()))
                              .startDate(startDate)
                              .endDate(endDate)
                              .location(location)
                              .organizerName(organizerName)
                              .description(description)
                              .status(EventStatus.valueOf(eventStatus))
                              .maxParticipant(maxParticipant).build();
                      eventService.updateEvent(event);
                  }catch (RuntimeException e){
                      ViewUtil.printHeader(e.getMessage());
                  }
                  break;
                }
                case "5" : {
                   try {
                       ViewUtil.printHeader("Delete Event");
                       String code = InputUtil.getText("Enter Code : " );
                       eventService.deleteEvent(code);
                   }catch (RuntimeException e){
                       ViewUtil.printHeader(e.getMessage());
                   }
                    break;
                }
                case "0":return;
            }
        }while (true);
    }
}
