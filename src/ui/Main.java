package ui;
/* -------------------
* TranslateDemo1.java
* -------------------
* (C) Copyright 2006, by Object Refinery Limited.
*
*/


import java.awt.Dimension;
import java.io.File;

import javax.swing.JScrollPane;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import dataset.DatasetUtils;
import audio.analysis.AmplitudeDatas;
import audio.play.WavePlayer;

/**
* A demo that uses a "wrapper" dataset that provides a translation of the
* underlying dataset.
*/
public class Main extends ApplicationFrame {

    MainPanel demo;
   
    /**
     * A demonstration application showing how to control a crosshair using an
     * external UI component.
     *
     * @param title  the frame title.
     */
    public Main(String title) {
        super(title);
        demo = new MainPanel();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(demo);
        scroll.setSize(new Dimension(600, 300));
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scroll);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

    	Main demo = new Main("Translate Demo 1");

        demo.setPreferredSize(new Dimension(600, 540));
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.addChart(DatasetUtils.loadFile("res/sqr-10-2Hz.wav"));
        demo.addChart(DatasetUtils.loadFileAndNormalize("res/sqr-10-2Hz.wav"));

    }
    
    public void addChart(DefaultXYDataset dataset){
    	demo.addChart(dataset);
    	demo.invalidate();
    }
    
    
}