/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzerGraphs;

import dataTemplates.resultTemplate;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Didar
 */
public class DistanceLineChartCreator {

    public DistanceLineChartCreator (ArrayList <resultTemplate> list_resultAnalyzer){
        this.list_resultAnalyzer = list_resultAnalyzer; 
        dataset = createDataset();
    } 
    
private DefaultCategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "Allignment with release goal (euclidean distance)";
        final String series2 = "Proposed value";
        final String series3 = "Actual value";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (resultTemplate iterator: list_resultAnalyzer){
            dataset.addValue(iterator.distance, series1, "gap of "+String.valueOf(iterator.daysForReplan)+" days");
            dataset.addValue(iterator.totalValue, series2, "gap of "+String.valueOf(iterator.daysForReplan)+" days");
            dataset.addValue(iterator.totalActualValue, series3, "gap of "+String.valueOf(iterator.daysForReplan)+" days");
           
        }    
        return dataset;
} 

public JFreeChart createDistanceLineChart() {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            chartTitle,       // chart title
            xAxisLabel,                    // domain axis label
            yAxisLabel,                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

      
        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    } 



    String chartTitle="Allignment with release goal while replanning in different intervals";
    String xAxisLabel="Number of days gap between replanning";
    String yAxisLabel="Value and allignment with release goal"; 
    JFreeChart chart; 
    public static ArrayList <resultTemplate> list_resultAnalyzer; 
    DefaultCategoryDataset dataset; 

     
    
    
    
}
