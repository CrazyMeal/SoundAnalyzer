package ui;
/* -------------------
* TranslateDemo1.java
* -------------------
* (C) Copyright 2006, by Object Refinery Limited.
*
*/


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
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
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
* A demo that uses a "wrapper" dataset that provides a translation of the
* underlying dataset.
*/
public class TestTranslateChart extends ApplicationFrame {

    
   
    /**
     * A demonstration application showing how to control a crosshair using an
     * external UI component.
     *
     * @param title  the frame title.
     */
    public TestTranslateChart(String title) {
        super(title);
        setContentPane(new DemoPanel());
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

    	TestTranslateChart demo = new TestTranslateChart("Translate Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
   
}