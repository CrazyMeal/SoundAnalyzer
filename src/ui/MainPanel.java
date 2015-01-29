package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

public class MainPanel extends JPanel
implements ActionListener {

	private TimeSeries series;

	private ArrayList<ChartPanel> charts = new ArrayList<ChartPanel>();


	public MainPanel() {
		super(new BorderLayout());
		/*
		JPanel dashboard = new JPanel(new BorderLayout());
		dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));  
		dashboard.setMinimumSize(new Dimension(600, 300));
		dashboard.setBackground(Color.black);
		add(dashboard, BorderLayout.SOUTH);
		*/
	}

	public void addChart(String fileName, XYDataset dataset) {
		JPanel newPanel = new JPanel(new BorderLayout());
		
		ChartPanel chartPanel = new ChartPanel(createChart(fileName, dataset));
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		Border border = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createEtchedBorder()
				);
		chartPanel.setBorder(border);
		
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		
		controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton buttonPlay = new JButton("Play");
		buttonPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		controlsPanel.add(buttonPlay);
		
		controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton buttonPause = new JButton("Pause");
		buttonPause.setAlignmentX(Component.CENTER_ALIGNMENT);
		controlsPanel.add(buttonPause);
		
		
		
		newPanel.add(controlsPanel, BorderLayout.WEST);
		
		charts.add(chartPanel);
		
		this.setLayout(new GridLayout(charts.size()+1, 1));
		newPanel.add(chartPanel);
		
		add(newPanel, charts.size()-1);
	}

	private JFreeChart createChart(String fileName, XYDataset dataset) {

		JFreeChart chart = ChartFactory.createXYLineChart(
	            fileName, // chart title
	            "Time", // domain axis label
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


	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.revalidate();
	}
	

}
