package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

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

public class DemoPanel extends JPanel
implements ActionListener {

	private TimeSeries series;

	private ArrayList<ChartWrapper> charts = new ArrayList<ChartWrapper>();


	/**
	 * Creates a new demo panel.
	 */
	public DemoPanel() {
		super(new BorderLayout());
		
		// Partie analyse données
		File file = new File("res/sound.wav");
		WavePlayer player = new WavePlayer(file);
		player.setup();
		AmplitudeDatas datas = new AmplitudeDatas(player.analyze(), player.getDuration());
		player.close();
		System.out.println("duration> " + datas.getMinutes() + "min " + datas.getSeconds() + "s");
		// Fin analyse
		
		DefaultXYDataset dataset = new DefaultXYDataset();
		int j = 0;
		double[][] data = new double[2][datas.getDatas().length];
		for(double i : datas.getNormalizedDatas()){
				data[0][j] = j;
				data[1][j] = i;
			j++;
		}
		dataset.addSeries("test", data);
		addChart(dataset);

		JPanel dashboard = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		dashboard.add(scroll);
		dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));  
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

		JFreeChart chart1 = ChartFactory.createTimeSeriesChart("Translate Demo 1","X","Y",dataset,true,true,false);

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
		renderer.setSeriesPaint(0, Color.black);
		// fix the range
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		Range range = DatasetUtilities.findDomainBounds(dataset);
		axis.setRange(range);
		return chart1;
	}


	/**
	 * Creates a sample dataset.
	 *
	 * @param name  the dataset name.
	 * @param base  the starting value.
	 * @param start  the starting period.
	 * @param count  the number of values to generate.
	 *
	 * @return The dataset.
	 */
	private XYDataset createDataset(String name, double base, RegularTimePeriod start, int count) {

		this.series = new TimeSeries(name, start.getClass());
		RegularTimePeriod period = start;
		double value = base;
		for (int i = 0; i < count; i++) {
			this.series.add(period, value);   
			period = period.next();
			value = value * (1 + (Math.random() - 0.495) / 10.0);
		}

		TimeSeriesCollection tsc = new TimeSeriesCollection();
		tsc.addSeries(this.series);
		return new TranslatingXYDataset(tsc);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		addChart(createDataset("Random 1", 100.0, new Minute(), 200));
		this.revalidate();
	}
	

}