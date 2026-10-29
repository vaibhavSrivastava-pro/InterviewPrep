
import java.util.ArrayList;


public class HTMLParser {
    ArrayList<String> getUrls(String url){
        ArrayList<String> urlList = new ArrayList<>();
        switch (url) {
            case "https://codewitharyan.com/" -> {
                sleep(100);
                urlList.add("https://codewitharyan.com/system-design/low-level-design");
                sleep(100);
                urlList.add("https://codewitharyan.com/tech-blogs");
            }
            case "https://codewitharyan.com/system-design/low-level-design" -> {
                sleep(100);
                urlList.add("https://codewitharyan.com/tech-blogs/web-crawler-multithreaded");
            }
            case "https://codewitharyan.com/tech-blogs" -> {
                sleep(100);
                urlList.add("https://codewitharyan.com/tech-blogs/design-chess-game");
            }
            default -> {
            }
        }
        
        sleep(100);
        return urlList;
    }

    void sleep(int timeout){
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
        }
    }
}
