/*
 https://codewitharyan.com/tech-blogs/web-crawler-multithreaded 
*/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;



public class MultithreadedWebCrawler {
    String hostname;
    ConcurrentHashMap<String, Boolean> urlHashMap = new ConcurrentHashMap<>();
    ExecutorService executor = Executors.newFixedThreadPool(5);
    AtomicInteger numberOfUrlsToParse = new AtomicInteger(0);
    HTMLParser htmlParser;

    class Task implements Runnable{
        String url;

        public Task(String url) {
            this.url = url;
        }

        @Override 
        public void run(){
            for(String extractedUrl: htmlParser.getUrls(url)){
                String currHostName = extractedUrl.split("/")[2];

                if(currHostName.equals(hostname) && urlHashMap.putIfAbsent(extractedUrl, true) == null){
                    numberOfUrlsToParse.addAndGet(1);
                    executor.submit(new Task(extractedUrl));
                }
            }

            numberOfUrlsToParse.decrementAndGet();
        }
    }

    public List crawl(String url, HTMLParser htmlParser){

        hostname = url.split("/")[2];
        this.htmlParser = htmlParser;

        urlHashMap.put(url, true);

        numberOfUrlsToParse.addAndGet(1);

        executor.submit(new Task(url));

        while(numberOfUrlsToParse.get() > 0){
            try {
                Thread.sleep(80);
            } catch (Exception e) {
            }
        }

        executor.shutdown();

        return new ArrayList<>(urlHashMap.keySet());
    }

    public static void main(String[] args) {
        MultithreadedWebCrawler obj = new MultithreadedWebCrawler();
        HTMLParser htmlParser = new HTMLParser();
        List output = obj.crawl("https://codewitharyan.com/", htmlParser);

        for(Object o: output){
            System.out.println(o);
        }
    }
    
}
