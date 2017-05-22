package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.Statistics;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Controller to show different statistics!
 *
 * Created by Gemin on 21.05.2017.
 */
public class StatisticsViewController {

    private Statistics statistics;

    static class ChartCanvas extends Canvas {

        JFreeChart chart;

        private FXGraphics2D g2;

        ChartCanvas(JFreeChart chart) {
            this.chart = chart;
            this.g2 = new FXGraphics2D(getGraphicsContext2D());
            // Redraw canvas when size changes.
            widthProperty().addListener(e -> draw());
            heightProperty().addListener(e -> draw());
        }

        private void draw() {
            double width = getWidth();
            double height = getHeight();
            getGraphicsContext2D().clearRect(0, 0, width, height);
            this.chart.draw(this.g2, new Rectangle2D.Double(0, 0, width,
                    height));
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) { return getWidth(); }

        @Override
        public double prefHeight(double width) { return getHeight(); }
    }

    private MainApp mainApp;
    private Stage dialogStage;
    private ChartCanvas lastChart;

    @FXML
    private Label enemiesDefeated;
    @FXML
    private Label goalsCompleted;
    @FXML
    private Label questsSucceeded;
    @FXML
    private AnchorPane ap2;

    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();

    @FXML
    private LineChart<String,Number> lineChartExpGold = new LineChart<String,Number>(xAxis,yAxis);


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        statistics = mainApp.getStatistics();
        lineChartExpGold.setVisible(false);

        enemiesDefeated.setText(""+statistics.getEnemiesDefeated());
        goalsCompleted.setText(""+statistics.getGoalsCompleted());
        questsSucceeded.setText(""+statistics.getQuestsSucceeded());
    }

    /**
     * Called when user clicks "Attributes" button
     */
    @FXML
    private void drawAttributeSpider() {
        lineChartExpGold.setVisible(false);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String systemName = "Attributes";

        if(lastChart != null) {
            lastChart.setHeight(0);
            lastChart.setWidth(0);
            ap2.getChildren().remove(lastChart);
        }
        ap2.setVisible(true);

        for(Attributes a:Attributes.values()) {
            dataset.addValue(a.getLevel(), systemName, a.getFieldDescription());
        }

        SpiderWebPlot spiderWebPlot = new SpiderWebPlot(dataset);
        spiderWebPlot.setBackgroundColor(java.awt.Color.DARK_GRAY);
        spiderWebPlot.setSeriesOutlinePaint(0, Color.white);
        spiderWebPlot.setSeriesPaint(0, Color.orange);

        JFreeChart spiderChart2 = new JFreeChart("Attributes Plot", JFreeChart.DEFAULT_TITLE_FONT, spiderWebPlot, true);
        spiderChart2.setAntiAlias(true);

        ChartCanvas swpCanvas = new ChartCanvas(spiderChart2);

        AnchorPane.setTopAnchor(swpCanvas, 10.0);
        AnchorPane.setLeftAnchor(swpCanvas, 10.0);
        AnchorPane.setRightAnchor(swpCanvas, 10.0);
        swpCanvas.setWidth(ap2.getWidth());
        swpCanvas.setHeight(ap2.getHeight());

        ap2.getChildren().add(swpCanvas);
        swpCanvas.draw();

        lastChart = swpCanvas;
    }

    /**
     * Called when user clicks "skill" button
     */
    @FXML
    private void drawSkillSpider() {
        lineChartExpGold.setVisible(false);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if(lastChart != null) {
            lastChart.setHeight(0);
            lastChart.setWidth(0);
            ap2.getChildren().remove(lastChart);
        }
        ap2.setVisible(true);

        String systemName = "Skill level";

        for(Skill s:mainApp.getSkillData()) {
            dataset.addValue(s.getLevel(), systemName, s.getName());
        }

        SpiderWebPlot spiderWebPlot = new SpiderWebPlot(dataset);
        spiderWebPlot.setBackgroundColor(java.awt.Color.DARK_GRAY);
        spiderWebPlot.setSeriesOutlinePaint(0, Color.white);
        spiderWebPlot.setSeriesPaint(0, Color.orange);

        JFreeChart spiderChart2 = new JFreeChart("Skill Level Plot", JFreeChart.DEFAULT_TITLE_FONT, spiderWebPlot, true);
        spiderChart2.setAntiAlias(true);

        ChartCanvas swpCanvas = new ChartCanvas(spiderChart2);

        AnchorPane.setTopAnchor(swpCanvas, 10.0);
        AnchorPane.setLeftAnchor(swpCanvas, 10.0);
        AnchorPane.setRightAnchor(swpCanvas, 10.0);
        swpCanvas.setWidth(ap2.getWidth());
        swpCanvas.setHeight(ap2.getHeight());

        ap2.getChildren().add(swpCanvas);
        swpCanvas.draw();

        lastChart = swpCanvas;
    }

    /**
     * Called when user clicks "Gold/Exp" Button
     */
    @FXML
    private void drawLineChart() {
        if(lastChart != null) {
            lastChart.setHeight(0);
            lastChart.setWidth(0);
            ap2.getChildren().remove(lastChart);
            lastChart.setVisible(false);
        }
        lineChartExpGold.setVisible(true);

        lineChartExpGold.getData().clear();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Exp");
        for(AbstractMap.SimpleEntry<String, Integer> a:statistics.getExperienceMap()) {;
            series1.getData().add(new XYChart.Data<>(a.getKey(), a.getValue()));
        }

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Gold");
        for(AbstractMap.SimpleEntry<String, Integer> a:statistics.getGoldMap()) {
            series2.getData().add(new XYChart.Data<>(a.getKey(), a.getValue()));
        }

        lineChartExpGold.setTitle("Gold/Experience gained");
        lineChartExpGold.getData().addAll(series1,series2);
        lineChartExpGold.setLegendSide(Side.RIGHT);
        yAxis.setAutoRanging(true);
    }

    /**
     * Called when user decides to leave the stat view
     */
    @FXML
    private void handleClose() {
        dialogStage.close();
    }


}
