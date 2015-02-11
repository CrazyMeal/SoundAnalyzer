package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartPanel;

import ui.MainPanel;

public class CloseActionListener implements ActionListener {


	private ChartPanel chartPanel;
	private MainPanel mainPanel;

	public CloseActionListener(ChartPanel chartPanel, MainPanel mainPanel) {
		this.chartPanel = chartPanel;
		this.mainPanel = mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int idx = mainPanel.charts.indexOf(chartPanel);
		mainPanel.remove(mainPanel.getComponent(idx));
		mainPanel.charts.remove(chartPanel);
		System.out.println(idx);
		mainPanel.revalidate();
	}

}
