import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class WikiScraper {
    public static void main(String[] args) throws IOException{
        List <String> urls = Files.readAllLines(Path.of("urls.txt"));
        List <Page> pages = new ArrayList<>();

        for (String url : urls) {
            try {
                String text = fetchText(url);
                String title = url.substring(url.lastIndexOf("/") + 1).replace("_", " ");
                String[] words = text.toLowerCase().split("[^a-z]+");

                HT table = new HT();
                for (String word : words) {
                    if(!word.isEmpty()){
                        table.add(word);
                    }
                }
                pages.add(new Page(title, table));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SwingUtilities.invokeLater(() -> GUI.display(pages));
    }

    static String fetchText(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements paragraph = document.select(".mw-content-ltr p");

        String bodyText = paragraph.text();
        return bodyText;
    }


}

