package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
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

public class ChartWorker extends SwingWorker<JFreeChart, JFreeChart> {

	private XYDataset dataset;
	private String fileName;
	private MainPanel mainPanel;
	private File file;


	public ChartWorker(String fileName, XYDataset dataset, MainPanel mainPanel,
			File file) {
		this.fileName = fileName;
		this.dataset = dataset;
		this.mainPanel = mainPanel;
		this.file = file;
	}
	
	@Override
	protected JFreeChart doInBackground() throws Exception {
		System.out.println("diBack");

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
		System.out.println("test2");
		
		JPanel newPanel = new JPanel(new BorderLayout());
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder());
		chartPanel.setBorder(border);

		
		mainPanel.charts.add(chartPanel);
		if(mainPanel.charts.size() == 1)
			mainPanel.setLayout(new GridLayout(mainPanel.charts.size()+1, 1));
		else
			mainPanel.setLayout(new GridLayout(mainPanel.charts.size(), 1));
		
		newPanel.add(mainPanel.createControlsPanel(file, chartPanel), BorderLayout.WEST);
		newPanel.add(chartPanel);
		
		mainPanel.add(newPanel, mainPanel.charts.size()-1);
		mainPanel.revalidate();
		return chart;
	}

}
