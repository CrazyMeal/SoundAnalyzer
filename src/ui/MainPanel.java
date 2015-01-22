package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import audio.analysis.AmplitudeDatas;
import audio.play.WavePlayer;

public class MainPanel extends JPanel
implements ActionListener {

	private TimeSeries series;

	private ArrayList<ChartWrapper> charts = new ArrayList<ChartWrapper>();


	public MainPanel() {
		super(new BorderLayout());
		JPanel dashboard = new JPanel(new BorderLayout());
		dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));  
		dashboard.setMinimumSize(new Dimension(600, 300));
		JButton button = new JButton();
		button.setText("ajouter Graph");
		button.setMaximumSize(new Dimension(100, 50));
		button.addActionListener(this);
		JPanel buttonPannel = new JPanel();
		buttonPannel.add(button);
		dashboard.add(buttonPannel);
		add(dashboard, BorderLayout.SOUTH);
	}

	public void addChart(XYDataset dataset) {
		ChartWrapper wrapper = new ChartWrapper();
		wrapper.chart = createChart(dataset);
		wrapper.chartPanel = new ChartPanel(wrapper.chart);
		wrapper.chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
		wrapper.chartPanel.setDomainZoomable(true);
		wrapper.chartPanel.setRangeZoomable(true);
		Border border = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createEtchedBorder()
				);
		wrapper.chartPanel.setBorder(border);
		charts.add(wrapper);
		this.setLayout(new GridLayout(charts.size()+1, 1));
		add(wrapper.chartPanel, charts.size()-1);
	}

	/**
	 * Creates the demo chart.
	 *
	 * @return The chart.
	 */
	private JFreeChart createChart(XYDataset dataset) {

		JFreeChart chart1 = ChartFactory.createTimeSeriesChart(
				"New Chart",
				"X",
				"Y",
				dataset,
				true,
				true,
				false
				);

		chart1.setBackgroundPaint(Color.white);
		XYPlot plot = chart1.getXYPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

		plot.setDomainCrosshairVisible(true);
		plot.setDomainCrosshairLockedOnData(false);
		plot.setRangeCrosshairVisible(false);
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setPaint(Color.black);
		// fix the range
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		Range range = DatasetUtilities.findDomainBounds(dataset);
		axis.setRange(range);
		return chart1;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.revalidate();
	}
	

}