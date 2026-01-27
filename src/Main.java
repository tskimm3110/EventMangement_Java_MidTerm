import auth.Auth;
import config.DbConfig;
import service.EventService;
import service.EventServiceImpl;
import service.ParticipantService;
import service.ParticipantServiceImpl;
import telegram_bot.TelegramBot;
import util.InputUtil;
import util.ViewUtil;
import view.EventView;
import view.ParticipantView;

import javax.swing.plaf.ViewportUI;

public class Main {
    public static void main(String[] args) {

        DbConfig.init();
        EventService eventService = new EventServiceImpl();
        EventView eventView = new EventView();
        ParticipantView participantView = new ParticipantView();
        ParticipantService participantService = new ParticipantServiceImpl();
        boolean isLogin = false;
        do{
            ViewUtil.header();
            ViewUtil.login();
            String opt = InputUtil.getText("Enter Option : ");

            switch (opt){
                case "1" :{
                    Auth auth = new Auth();
                    String email = InputUtil.getText("Enter Username  : ");
                    String password = InputUtil.getText("Enter Password : ");
                    isLogin = auth.login(email,password);
                    if(isLogin){
                        ViewUtil.mainMenu();
                        String menuOpt = InputUtil.getText("Enter Option : ");
                        if(menuOpt.equals("1")){
                            eventView.eventViewMenu(eventService);
                        }else if(menuOpt.equals("2")){
                            participantView.participantViewMenu(eventService,participantService);
                        }
                        if (menuOpt.equals("0")) break;
                    }else {
                        ViewUtil.printHeader("Invalid Username Or Password!");
                    }
                    break;
                }
                case "0": return;
            }

        }while (true);

    }
}