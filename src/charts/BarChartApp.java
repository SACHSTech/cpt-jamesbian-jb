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
  
  HashMap<String, ArrayList<WorkingItem>> countrySpaceItems =  dataLoader.getCountrySpaceItems();
  //ArrayList<String> countriestoBeDisplayed = new ArrayList<String>();
  ArrayList<String> countriestoBeDisplayed = dataLoader.getUniqueCountries();

 public void refreshBarData() {
  BarChart.Series<String,Float> series = new BarChart.Series<>();
  for (Map.Entry<String, ArrayList<WorkingItem>> set : countrySpaceItems.entrySet()) {
    String country = set.getKey();
    if (!countriestoBeDisplayed.contains(country)) {
      continue;
    }
    ArrayList<WorkingItem> items = set.getValue();

    for (WorkingItem item : items) {
      series.getData().add(new BarChart.Data(item.getYear(), item.getYearlyHours())); 
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
    xAxis.setLabel("Years (2000 - 2017)");
    xAxis.setCategories(FXCollections.<String>observableArrayList(arrYears));
    yAxis = new NumberAxis("Working Hours", 0.0d, 2500.0d, 1000.0d);
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

  public void setCountrySpaceItems(HashMap<String, ArrayList<WorkingItem>> countrySpaceItems) {
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
    CheckBox cbAll = new CheckBox("All Countries");
    ChoiceBox dropdown = new ChoiceBox();

    cbAll.selectedProperty().addListener(
      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
        if (new_val) {
          System.out.println("All countries checked: ");
          countriestoBeDisplayed.clear();
          countriestoBeDisplayed = (dataLoader.getUniqueCountries());
          chart.getData().clear();
          refreshBarData();
        }
      });
      

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

    hbox.getChildren().addAll(cbAll, dropdown);
    vbox.getChildren().addAll(hbox, chart);
  

    return vbox;

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    dataLoader.csvConvert();
    countrySpaceItems =  dataLoader.getCountrySpaceItems();
    countriestoBeDisplayed = dataLoader.getUniqueCountries();
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