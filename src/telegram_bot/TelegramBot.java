package telegram_bot;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class TelegramBot {
    private static String token = "8594686879:AAFeIsW4Uls8gf-jsHgy9VVP6pO7TFENKqU";
    private static String chatId = "-5122374952";
    public static void sendMessage(String msg){
        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");
            String json = """
                        {
                          "chat_id": "%s",
                          "text": "%s"
                        }
                        """.formatted(chatId, msg);
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.close();
            conn.getInputStream();
            System.out.println("Message sent successfully to Telegram!");
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
