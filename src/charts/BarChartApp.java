/* ....Show License.... */
package charts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import basic.DataLoader;
import basic.SpaceItem;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A chart that displays rectangular bars with heights indicating data values
 * for categories. Used for displaying information when at least one axis has
 * discontinuous or discrete data.
 */
public class BarChartApp extends Application {
  private BarChart<String,Float> chart;
  private CategoryAxis xAxis;
  private NumberAxis yAxis;
  ArrayList<String> years;
  DataLoader dataLoader = new DataLoader();
  
  HashMap<String, ArrayList<SpaceItem>> countrySpaceItems =  dataLoader.getCountrySpaceItems();
  //ArrayList<String> countriestoBeDisplayed = new ArrayList<String>();
  ArrayList<String> countriestoBeDisplayed = dataLoader.getUniqueCountries();

 public void refreshBarData() {
  BarChart.Series<String,Float> series = new BarChart.Series<>();
  for (Map.Entry<String, ArrayList<SpaceItem>> set : countrySpaceItems.entrySet()) {
    String country = set.getKey();
    if (!countriestoBeDisplayed.contains(country)) {
      continue;
    }
    ArrayList<SpaceItem> items = set.getValue();
    //ArrayList<BarChart.Data> data = new ArrayList<BarChart.Data>();

    for (SpaceItem item : items) {
      series.getData().add(new BarChart.Data(item.getYear(), item.getYearlyLaunches())); 
    }
    //series.add(new BarChart.Series(country, FXCollections.observableArrayList(data)));

  }
  chart.getData().add(series);

 }

  public void makeBar() {
    int n = this.years.size();
    String arrYears[] = new String[n];
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);

    xAxis = new CategoryAxis();
    xAxis.setCategories(FXCollections.<String>observableArrayList(arrYears));
    yAxis = new NumberAxis("Yearly Obects Launched", 0.0d, 150.0d, 50.0d);
    chart = new BarChart(xAxis,yAxis); 
    refreshBarData();
  }

  public void countrySelected(String countryName) throws InterruptedException, IOException {
    System.out.println("country selected: " + countryName);
    countriestoBeDisplayed.clear();
    countriestoBeDisplayed.add(countryName);
    chart.getData().clear();
    refreshBarData();
  }

  public void setCountrySpaceItems(HashMap<String, ArrayList<SpaceItem>> countrySpaceItems) {
    this.countrySpaceItems = countrySpaceItems;
  }

  public Parent createContent() throws IOException {
    Group root = new Group();
    //.csvConvert();
    countrySpaceItems = dataLoader.getCountrySpaceItems();
    years = dataLoader.getYearList();
    int n = this.years.size();
    String arrYears[] = new String[n];

    // Copying contents of s to arr[]
    System.arraycopy(years.toArray(), 0, arrYears, 0, n);
    //countriestoBeDisplayed = m.getUniqueCountries();

     makeBar();
    

    VBox vbox = new VBox();
    HBox hbox = new HBox();
    vbox.setMaxSize(900, 600);
    chart.setMaxSize(880, 560);
    hbox.setPadding(new Insets(10, 10, 10, 10));
    vbox.setSpacing(10);
    hbox.setSpacing(50);
    CheckBox cb1 = new CheckBox("All Countries");
    ChoiceBox dropdown = new ChoiceBox();

    dropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        try {
          try {
            countrySelected((String) dropdown.getItems().get((Integer) number2));
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });
    dropdown.getItems().addAll(dataLoader.getUniqueCountries());

    hbox.getChildren().addAll(cb1, dropdown);
    vbox.getChildren().addAll(hbox, chart);
  

    return vbox;

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    dataLoader.csvConvert();
    countrySpaceItems =  dataLoader.getCountrySpaceItems();
    //ArrayList<String> countriestoBeDisplayed = new ArrayList<String>();
    countriestoBeDisplayed = dataLoader.getUniqueCountries();
    Scene scene = new Scene(createContent(), 900, 600);
    primaryStage.setScene(scene);
  //  primaryStage.setScene(new Scene(createContent(), 900, 600));
    primaryStage.show();
  }

  /**
   * Java main for when running without JavaFX launcher
   */
  public static void main(String[] args) {
    launch(args);
  }
}