import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI {
    public static void display(List<Page> pages) {
        JFrame frame = new JFrame("Wikipedia Recommendation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JComboBox<String> dropdown = new JComboBox<>();
        for (Page p : pages){
            dropdown.addItem(p.title);
        }

        JButton bt = new JButton("Show Similar Websites");
        JLabel result = new JLabel("Pick a website and press the button");

        bt.addActionListener(e -> {
            int idx = dropdown.getSelectedIndex();
            Page selected = pages.get(idx);
            Page[] top = SimilarityMetric.topTwo(selected, pages);
            result.setText("1. " + top[0].title + "    2. " + top[1].title);
        });

        frame.add(dropdown);
        frame.add(bt);
        frame.add(result);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
}
