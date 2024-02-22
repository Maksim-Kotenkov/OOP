package ru.nsu.kotenkov;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * A class for drawing charts.
 * It includes collecting datasets from user's data types, setting up colors, etc...
 * We only can draw 6 lines for 6 types of algorithms,
 * but it is possible to draw as many as possible results for these algos.
 */
public class DrawingCharts extends ApplicationFrame {
    /**
     * Constructor for the class.
     *
     * @param applicationTitle window titleimport java.util.ArrayList;
     * @param chartTitle chart title
     * @param dots list of maps (size of array - time in ms)
     */
    public DrawingCharts(String applicationTitle,
                         String chartTitle, List<Map<Integer, Integer>> dots) {
        super(applicationTitle);

        JFreeChart chart = getChart(chartTitle, dots);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1120, 734));
        final XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = getXyLineAndShapeRenderer();
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    /**
     * A method to get a chart to save it to png.
     * Shouldn't get so long to call it twice, so it's static
     * and is called both in constructor and in draw.
     *
     * @param chartTitle a title for a chart
     * @param dots list of results (size to time elapsed)
     * @return a chart
     */
    public static JFreeChart getChart(String chartTitle, List<Map<Integer, Integer>> dots) {
        return ChartFactory.createXYLineChart(
                chartTitle,
                "Array size",
                "Time elapsed, ms",
                createDataset(dots),
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    /**
     * Setting up the renderer.
     *
     * @return renderer obj
     */
    private static XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesPaint(3, Color.BLUE);
        renderer.setSeriesPaint(4, Color.BLACK);
        renderer.setSeriesPaint(5, Color.GREEN);
        renderer.setSeriesPaint(6, Color.RED);

        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));
        renderer.setSeriesStroke(6, new BasicStroke(2.0f));

        return renderer;
    }

    /**
     * Creating datasets for the line chart from List(Map(int, int)).
     *
     * @param dots list of our results
     * @return data type for the chart
     */
    public static XYDataset createDataset(List<Map<Integer, Integer>> dots) {
        final XYSeriesCollection dataset = new XYSeriesCollection();

        if (dots.isEmpty()) {
            return dataset;
        }

        XYSeries s1 = new XYSeries("Linear");
        Map<Integer, Integer> linearMap = dots.get(0);
        for (Integer key : linearMap.keySet()) {
            s1.add(key, linearMap.get(key));
        }

        XYSeries s2 = new XYSeries("4Threads");
        Map<Integer, Integer> twoThreadsMap = dots.get(1);
        for (Integer key : twoThreadsMap.keySet()) {
            s2.add(key, twoThreadsMap.get(key));
        }

        XYSeries s3 = new XYSeries("8Threads");
        Map<Integer, Integer> fourThreadsMap = dots.get(2);
        for (Integer key : fourThreadsMap.keySet()) {
            s3.add(key, fourThreadsMap.get(key));
        }

        XYSeries s4 = new XYSeries("50Threads");
        Map<Integer, Integer> eightThreadsMap = dots.get(3);
        for (Integer key : eightThreadsMap.keySet()) {
            s4.add(key, eightThreadsMap.get(key));
        }

        XYSeries s5 = new XYSeries("100Threads");
        Map<Integer, Integer> fiftyThreadsMap = dots.get(4);
        for (Integer key : fiftyThreadsMap.keySet()) {
            s5.add(key, fiftyThreadsMap.get(key));
        }

        XYSeries s6 = new XYSeries("500Threads");
        Map<Integer, Integer> hundredThreads = dots.get(5);
        for (Integer key : hundredThreads.keySet()) {
            s6.add(key, hundredThreads.get(key));
        }

        // S7 AIRLINES
        XYSeries s7 = new XYSeries("ParallelStream");
        Map<Integer, Integer> parallelStreamMap = dots.get(6);
        for (Integer key : parallelStreamMap.keySet()) {
            s7.add(key, parallelStreamMap.get(key));
        }

        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);
        dataset.addSeries(s4);
        dataset.addSeries(s5);
        dataset.addSeries(s6);
        dataset.addSeries(s7);

        return dataset;
    }

    /**
     * Function for calling from outside.
     *
     * @param chartTitle chart title
     * @param dots our statistics
     */
    public static void draw(String chartTitle, List<Map<Integer, Integer>> dots)
            throws IOException {
        DrawingCharts toWindow = new DrawingCharts("Statistics", chartTitle, dots);
        JFreeChart toFile = DrawingCharts.getChart(chartTitle, dots);
        XYLineAndShapeRenderer renderer = getXyLineAndShapeRenderer();

        final XYPlot plot = toFile.getXYPlot();
        plot.setRenderer(renderer);

        File f = new File("./ResultChart.png");
        BufferedImage chartImage = toFile.createBufferedImage(800, 600, null);
        ImageIO.write(chartImage, "png", f);

        toWindow.pack();
        toWindow.setVisible(true);
    }
}
