package ui;

import javax.swing.JScrollPane;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class TestTranslateChart extends ApplicationFrame {

    public TestTranslateChart(String title) {
        super(title);
        DemoPanel dem = new DemoPanel();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(dem);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scroll);
    }

    public static void main(String[] args) {

    	TestTranslateChart demo = new TestTranslateChart("Translate Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
   
}