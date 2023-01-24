/* ....Show License.... */
package charts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import basic.DataLoader;
import basic.WorkingItem;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Chart animator for JavaFx
 * 
 * @param chart                Barchart variable
 * @param linechart            Linechart variable
 * @param countriesDisplaying  ArrayList of unique country names
 * @param countrySpaceItems    Hashmap 
 * @param item                 fetches values
 * @author J.Bian
 */

public class BarChartApp extends Application {
  private BarChart<String, Float> chart;
  private LineChart<String, Float> lineChart;
  private CategoryAxis xAxis;
  private NumberAxis yAxis;
  ArrayList<String> years;
  DataLoader dataLoader = new DataLoader();

  HashMap<String, ArrayList<WorkingItem>> countrySpaceItems = dataLoader.getCountrySpaceItems();
  ArrayList<String> countriesDisplaying = dataLoader.getUniqueCountries();

  public void refreshBarData() {
    BarChart.Series<String, Float> series = new BarChart.Series<>();
    for (Map.Entry<String, ArrayList<WorkingItem>> set : countrySpaceItems.entrySet()) {
      String country = set.getKey();
      if (!countriesDisplaying.contains(country)) {
        continue;
      }
      ArrayList<WorkingItem> items = set.getValue();

      for (WorkingItem item : items) {
        series.getData().add(new BarChart.Data(item.getYear(), item.getYearlyHours()));
      }

    }
    chart.getData().add(series);

  }

  public void makeBar() {
    int n = this.years.size();
    String arrYears[] = new String[n];
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);

    xAxis = new CategoryAxis();
    xAxis.setLabel("Years (2000 - 2017)");
    xAxis.setCategories(FXCollections.<String>observableArrayList(arrYears));
    yAxis = new NumberAxis("Working Hours", 0.0d, 2500.0d, 1000.0d);
    chart = new BarChart(xAxis, yAxis);
    refreshBarData();
  }

  public void makeLine() {
    int n = this.years.size();
    String arrYears[] = new String[n];
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);

    xAxis = new CategoryAxis();
    xAxis.setLabel("Years (2000 - 2017)");
    xAxis.setCategories(FXCollections.<String>observableArrayList(arrYears));
    yAxis = new NumberAxis("Working Hours", 0.0d, 2500.0d, 1000.0d);
    lineChart = new LineChart(xAxis, yAxis);
    refreshLineData();
  }

  public void refreshLineData() {
    LineChart.Series<String, Float> series = new LineChart.Series<>();
    for (Map.Entry<String, ArrayList<WorkingItem>> set : countrySpaceItems.entrySet()) {
      String country = set.getKey();
      if (!countriesDisplaying.contains(country)) {
        continue;
      }
      ArrayList<WorkingItem> items = set.getValue();

      for (WorkingItem item : items) {
        series.getData().add(new LineChart.Data(item.getYear(), item.getYearlyHours()));
      }

    }
    lineChart.getData().add(series);

  }

  public void countrySelected(String countryName) throws InterruptedException, IOException {
    System.out.println("country selected: " + countryName);
    countriesDisplaying.clear();
    countriesDisplaying.add(countryName);
    chart.getData().clear();
    refreshBarData();
  }

  public void countrySelectedLine(String countryName) throws InterruptedException, IOException {
    System.out.println("country selected: " + countryName);
    countriesDisplaying.clear();
    countriesDisplaying.add(countryName);
    lineChart.getData().clear();
    refreshLineData();
  }

  public void setCountrySpaceItems(HashMap<String, ArrayList<WorkingItem>> countrySpaceItems) {
    this.countrySpaceItems = countrySpaceItems;
  }

  public Parent createContent() throws IOException {
    ;
    countrySpaceItems = dataLoader.getCountrySpaceItems();
    years = dataLoader.getYearList();
    int n = this.years.size();
    String arrYears[] = new String[n];

    // Copying contents of s to arr[]
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);
    // countriestoBeDisplayed = m.getUniqueCountries();

    makeBar();
    makeLine();

    TabPane tabPane = new TabPane();
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    TextField findCountry = new TextField();
    Button btnSearch = new Button("Search");
    vbox.setMaxSize(900, 600);
    chart.setMaxSize(880, 560);
    hbox.setPadding(new Insets(10, 10, 10, 10));
    vbox.setSpacing(10);
    hbox.setSpacing(50);
    ChoiceBox dropdown = new ChoiceBox();

    dropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {

        try {
          countrySelected((String) dropdown.getItems().get((Integer) number2));
        } catch (InterruptedException | IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });
    dropdown.getItems().addAll(dataLoader.getUniqueCountries());

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        ArrayList<String> countries = dataLoader.getUniqueCountries();
        int countryIndex = dataLoader.findCountry(findCountry.getText());
        if (countryIndex >= 0) {
          dropdown.setValue(countries.get(countryIndex));
        }
      }
    };
    btnSearch.setOnAction(event);

    hbox.getChildren().addAll(new Label("Choose a Country"), dropdown, findCountry, btnSearch);
    vbox.getChildren().addAll(hbox, chart);
    VBox vboxLineChart = new VBox();
    HBox hboxLineChart = new HBox();

    Tab tab1 = new Tab("BarChart", vbox);

    ChoiceBox dropdownLine = new ChoiceBox();
    dropdownLine.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {

        try {
          countrySelectedLine((String) dropdownLine.getItems().get((Integer) number2));
        } catch (InterruptedException | IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });
    ArrayList<String> countries = dataLoader.getUniqueCountries();
    dropdownLine.getItems().addAll(countries);

    dropdownLine.setValue(countries.get(0));
    dropdown.setValue(countries.get(0));

    vboxLineChart.setMaxSize(900, 600);
    lineChart.setMaxSize(880, 560);
    hboxLineChart.setPadding(new Insets(10, 10, 10, 10));
    vboxLineChart.setSpacing(10);
    hboxLineChart.setSpacing(50);
    hboxLineChart.getChildren().addAll(new Label("Choose a Country"), dropdownLine, lineChart);
    vboxLineChart.getChildren().addAll(hboxLineChart, lineChart);
    Tab tab2 = new Tab("LineChart", vboxLineChart);
    tabPane.getTabs().add(tab1);
    tabPane.getTabs().add(tab2);

    return tabPane;

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    dataLoader.csvConvert();
    countrySpaceItems = dataLoader.getCountrySpaceItems();
    countriesDisplaying = dataLoader.getUniqueCountries();
    Scene scene = new Scene(createContent(), 900, 600);
    primaryStage.setTitle("Annual Working Hours Global");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Java main for when running without JavaFX launcher
   */
  public static void main(String[] args) {
    launch(args);
  }
}