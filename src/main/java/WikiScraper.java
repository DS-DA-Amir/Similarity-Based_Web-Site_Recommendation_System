import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import java.io.IOException;

public class WikiScraper {
    public static void main(String[] args) throws IOException{
        List <String> urls = Files.readAllLines(Path.of("urls.txt"));

        for (String url : urls) {
            try {
                String text = fetchText(url);
                System.out.println(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String fetchText(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements paragraph = document.select(".mw-content-ltr p");

        String bodyText = paragraph.text();
        return bodyText;
    }

}

