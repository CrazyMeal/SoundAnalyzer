package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import ui.actionListeners.ExportActionListener;
import ui.actionListeners.StopActionListener;
import ui.actionListeners.PlayActionListener;
import controllers.ApplicationController;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -3329086471570271813L;
	public ArrayList<ChartPanel> charts = new ArrayList<ChartPanel>();
	private ApplicationController appController;

	public MainPanel(ApplicationController appController) {
		super(new BorderLayout());
		this.appController = appController;
	}

	public void addChart(File file, XYDataset dataset) {
		ChartWorker worker = new ChartWorker(file.getName(), dataset, this, file);
		worker.execute();
	}
	
	JPanel createControlsPanel(File file, ChartPanel chartPanel){
		
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton buttonPlay = new JButton("Play");
		buttonPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPlay.addActionListener(new PlayActionListener(file, this.appController));
		
		controlsPanel.add(buttonPlay);
		
		controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton buttonStop = new JButton("Stop");
		buttonStop.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonStop.addActionListener(new StopActionListener(this.appController));
		controlsPanel.add(buttonStop);
		
		controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton buttonFlacExport = new JButton("Export");
		buttonFlacExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonFlacExport.addActionListener(new ExportActionListener(file, this));
		controlsPanel.add(buttonFlacExport);
		
		return controlsPanel;
	}
	

}
