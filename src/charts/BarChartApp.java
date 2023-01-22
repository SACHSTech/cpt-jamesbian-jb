/* ....Show License.... */
package charts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import basic.DataLoader;
import basic.SpaceItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

/**
 * A chart that displays rectangular bars with heights indicating data values
 * for categories. Used for displaying information when at least one axis has
 * discontinuous or discrete data.
 */
public class BarChartApp extends Application {
  DataLoader m = new DataLoader();

  public void setCountrySpaceItems(HashMap<String, ArrayList<SpaceItem>> countrySpaceItems) {
    this.countrySpaceItems = countrySpaceItems;
  }

  private BarChart chart;
  private CategoryAxis xAxis;
  private NumberAxis yAxis;
  ArrayList<String> years;
  HashMap<String, ArrayList<SpaceItem>> countrySpaceItems;

  public Parent createContent() throws IOException {
    m.csvConvert();
    countrySpaceItems = m.getCountrySpaceItems();
    years = m.getYearList();
    int n = this.years.size();
    String arrYears[] = new String[n];

    // Copying contents of s to arr[]
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);

    // String[] years = this.years.toArray();
    xAxis = new CategoryAxis();
    xAxis.setCategories(FXCollections.<String>observableArrayList(arrYears));
    yAxis = new NumberAxis("Yearly Obects Launched", 0.0d, 150.0d, 50.0d);
    ArrayList<BarChart.Series> series = new ArrayList<BarChart.Series>();

    for (Map.Entry<String, ArrayList<SpaceItem>> set : countrySpaceItems.entrySet()) {
      String country = set.getKey();
      ArrayList<SpaceItem> items = set.getValue();
      ArrayList<BarChart.Data> data = new ArrayList<BarChart.Data>();

      for (SpaceItem item : items) {
        data.add(new BarChart.Data(item.getYear(), item.getYearlyLaunches()));

      }
      series.add(new BarChart.Series(country, FXCollections.observableArrayList(data)));

    }

    ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(series);

    // new BarChart.Series("", FXCollections.observableArrayList(
    // new BarChart.Data(arrYears[0], 567d),
    // new BarChart.Data(arrYears[1], 1292d),
    // new BarChart.Data(arrYears[2], 1292d))),
    // new BarChart.Series("Lemons", FXCollections.observableArrayList(
    // new BarChart.Data(arrYears[0], 956),
    // new BarChart.Data(arrYears[1], 1665),
    // new BarChart.Data(arrYears[2], 2559))),
    // new BarChart.Series("Oranges", FXCollections.observableArrayList(
    // new BarChart.Data(arrYears[0], 1154),
    // new BarChart.Data(arrYears[1], 1927),
    // new BarChart.Data(arrYears[2], 2774))));
    chart = new BarChart(xAxis, yAxis, barChartData, 25.0d);
    return chart;

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setScene(new Scene(createContent()));
    primaryStage.show();
  }

  public void ensemble(String[] years) {

  }

  /**
   * Java main for when running without JavaFX launcher
   */
  public static void main(String[] args) {
    launch(args);
  }
}