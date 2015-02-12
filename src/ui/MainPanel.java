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

public class MainPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3329086471570271813L;
	public ArrayList<ChartPanel> charts = new ArrayList<ChartPanel>();
	private ApplicationController appController;

	public MainPanel(ApplicationController appController) {
		super(new BorderLayout());
		this.appController = appController;
	}

	public void addChart(File file, XYDataset dataset) {
		JPanel newPanel = new JPanel(new BorderLayout());
		
		ChartPanel chartPanel = new ChartPanel(createChart(file.getName(), dataset));
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder());
		chartPanel.setBorder(border);

		
		
		charts.add(chartPanel);
		if(charts.size() == 1)
			this.setLayout(new GridLayout(charts.size()+1, 1));
		else
			this.setLayout(new GridLayout(charts.size(), 1));
		
		newPanel.add(this.createControlsPanel(file, chartPanel), BorderLayout.WEST);
		newPanel.add(chartPanel);
		
		this.add(newPanel, charts.size()-1);
		
	}

	private JFreeChart createChart(String fileName, XYDataset dataset) {

		JFreeChart chart = ChartFactory.createXYLineChart(
	            fileName, // chart title
	            "Time (s)", // domain axis label
	            "Amplitude",
	            dataset,  // initial series
	            PlotOrientation.VERTICAL, // orientation
	            false, // include legend
	            true, // tooltips?
	            false // URLs?
	            );

		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

		plot.setDomainCrosshairVisible(true);
		plot.setDomainCrosshairLockedOnData(false);
		plot.setRangeCrosshairVisible(false);
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, Color.black);
		// fix the range
		NumberAxis axis = (NumberAxis) plot.getDomainAxis();
		Range range = DatasetUtilities.findDomainBounds(dataset);
		axis.setRange(range);
		return chart;
	}
	
	private JPanel createControlsPanel(File file, ChartPanel chartPanel){
		
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


	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.revalidate();
	}
	

}
