/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzerGraphs;

import static analyzerGraphs.valueLineChartCreator.list_resultAnalyzer;
import dataTemplates.resultTemplate;
import java.awt.Color;
import java.awt.GradientPaint;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Didar
 */
public class barChartCreator {
    
    
    public barChartCreator(ArrayList <resultTemplate> list_resultAnalyzer) {
        this.list_resultAnalyzer = list_resultAnalyzer; 
        
        final CategoryDataset dataset = createDataset();
        chart = createChart(dataset);
         
    }
    
    public JFreeChart getBarChart (){
        return chart; 
    }
    
    private CategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "Actual";
        final String series2 = "Proposed";
//        final String series3 = "Third";

        // column keys...
        final String category1 = "Feature";
        final String category2 = "Bug";
        final String category3 = "Improvement";
//        final String category4 = "Category 4";
//        final String category5 = "Category 5";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (resultTemplate iterator: list_resultAnalyzer){
//            dataset.addValue(iterator.actftrRatio, series1, category1);
//            dataset.addValue(iterator.actbugRatio, series1, category2);
//            dataset.addValue(iterator.actimpRatio, series1, category3);
//            
//            
//            dataset.addValue(iterator.prpftrRatio, series2, category1);
//            dataset.addValue(iterator.prpbugRatio, series2, category2);
//            dataset.addValue(iterator.prpimpRatio, series2, category3);
            
            dataset.addValue(iterator.actftrRatio, series1, category1);
            dataset.addValue(iterator.actbugRatio, series1, category2);
            dataset.addValue(iterator.actimpRatio, series1, category3);
            
            
            dataset.addValue(iterator.prpftrRatio, series2, category1);
            dataset.addValue(iterator.prpbugRatio, series2, category2);
            dataset.addValue(iterator.prpimpRatio, series2, category3);
            
            
            
        }
        
//        dataset.addValue(1.0, series1, category1);
//        dataset.addValue(4.0, series1, category2);
//        dataset.addValue(3.0, series1, category3);
//        dataset.addValue(5.0, series1, category4);
//        dataset.addValue(5.0, series1, category5);
//
//        dataset.addValue(5.0, series2, category1);
//        dataset.addValue(7.0, series2, category2);
//        dataset.addValue(6.0, series2, category3);
//        dataset.addValue(8.0, series2, category4);
//        dataset.addValue(4.0, series2, category5);
//
//        dataset.addValue(4.0, series3, category1);
//        dataset.addValue(3.0, series3, category2);
//        dataset.addValue(2.0, series3, category3);
//        dataset.addValue(3.0, series3, category4);
//        dataset.addValue(6.0, series3, category5);
        
        return dataset;
        
    }
    
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Transformation of effort in each category for different replan interval",         // chart title
            "Type of Change requests",               // domain axis label
            "Percentage of effort spent",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
    JFreeChart chart; 
    public static ArrayList <resultTemplate> list_resultAnalyzer; 

}
