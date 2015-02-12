package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import ui.actionListeners.CloseActionListener;
import ui.actionListeners.ExportActionListener;
import ui.actionListeners.StopActionListener;
import ui.actionListeners.PlayActionListener;
import audio.play.AudioLinePlayer;
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
		
		/*controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton buttonFlacClose = new JButton("Close");
		buttonFlacClose.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonFlacClose.addActionListener(new CloseActionListener(chartPanel, this));
		controlsPanel.add(buttonFlacClose);*/
		
		return controlsPanel;
	}
	

}
