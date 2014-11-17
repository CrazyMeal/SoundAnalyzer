package audio;

import static org.junit.Assert.*;

import org.jfree.ui.RefineryUtilities;
import org.junit.Test;

import ui.TestTranslateChart;

public class UITest {

	@Test
	public void newWindow() {
    	TestTranslateChart demo = new TestTranslateChart("Translate Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
	}

}
