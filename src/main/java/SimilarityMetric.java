import java.util.List;

public class SimilarityMetric {

    public static double idf(String word, List<Page> pages){
        int df = 0;
        for (Page page : pages){
            if(page.table.contains(word)){
                df++;
            }
        }
        return Math.log((double) pages.size() / df);
    }

    public static double cosine(Page a, Page b, List<Page> pages) {
        double dot = 0;
        double lenA = 0;
        double lenB = 0;

        for (String word : a.table.keys()) {
            double wa = weight(word, a, pages);
            double wb = weight(word, b, pages);
            dot += wa * wb;
            lenA += Math.pow(wa, 2);
        }

        for (String word : b.table.keys()) {
            double wb = weight(word, b, pages);
            lenB += Math.pow(wb, 2);
        }

        return dot / (Math.sqrt(lenA) * Math.sqrt(lenB));
    }

    public static Page[] topTwo(Page selected, List<Page> pages) {
        Page best = null;
        Page second = null;
        double bestScore = -1;
        double secondScore = -1;

        for (Page p : pages) {
            if (p == selected) continue;
            double score = cosine(selected, p, pages);

            if (score > bestScore) {
                second = best;
                secondScore = bestScore;
                best = p;
                bestScore = score;
            } else if (score > secondScore) {
                second = p;
                secondScore = score;
            }
        }
        return new Page[] { best, second };
    }

    public static double weight(String word, Page page, List<Page> pages){
        return page.table.getCount(word) * idf(word, pages);
    }
}