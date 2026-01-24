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

public class Main {
    public static void main(String[] args) {

        DbConfig.init();
        EventService eventService = new EventServiceImpl();
        EventView eventView = new EventView();
        ParticipantView participantView = new ParticipantView();
        ParticipantService participantService = new ParticipantServiceImpl();
        do{
            ViewUtil.header();
            ViewUtil.mainMenu();
            String menuOpt = InputUtil.getText("Enter Option : ");
            if(menuOpt.equals("1")){
                eventView.eventViewMenu(eventService);
            }else if(menuOpt.equals("2")){
                participantView.participantViewMenu(eventService,participantService);
            }

            if (menuOpt.equals("0")) break;
        }while (true);

    }
}