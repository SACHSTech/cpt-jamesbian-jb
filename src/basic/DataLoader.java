package basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Turns Csv file into HashMap, and sorts
 * 
 * @param delimiter         splits string into tokens
 * @param yearList          ArrayList that returns set of unique years
 * @param countrySpaceItems HashMap that uses country as the key
 * @param countrySet        Converts the key of countrySpaceItems into a Set
 * @param countryList       Converts Set back into ArrayList for bar chart
 * @param yearSet           Set of unique country names
 * @param country           Holds country value of each WorkingItem to be put in
 *                          ArrayList
 * @param fileColCount      Counter for file column used to skip first column
 * @param file              Location of csv. file
 * @param fr                DataLoader Filereader
 * @param br                DataLoader Bufferedreader
 * @param line              Used while file does not contain " "
 * @author J.Bian
 */

public class DataLoader {

    // Global Variables
    public static final String delimiter = ",";
    ArrayList<String> yearList = new ArrayList<String>();
    HashMap<String, ArrayList<WorkingItem>> countrySpaceItems = new HashMap<String, ArrayList<WorkingItem>>();

    // Getter & Setter Methods
    public ArrayList<String> getYearList() {
        return yearList;
    }

    public void setYearList(ArrayList<String> yearSet) {
        this.yearList = yearSet;
    }

    public HashMap<String, ArrayList<WorkingItem>> getCountrySpaceItems() {
        return countrySpaceItems;
    }

    public void setCountrySpaceItems(HashMap<String, ArrayList<WorkingItem>> countrySpaceItems) {
        this.countrySpaceItems = countrySpaceItems;
    }

    // Converts Set into ArrayList
    public ArrayList<String> getUniqueCountries() {
        Set<String> countrySet = countrySpaceItems.keySet();
        ArrayList<String> countryList = new ArrayList<String>(countrySet);
        MergeSort.mergeSort(countryList);
        String [] sorted = MergeSort.mergeSort(countryList);
        countryList = new ArrayList<String>(Arrays.asList(sorted));

        return countryList;
    }

    // Gets Information from workingHours.csv; while fills an ArrayList of
    // workingItems while the next line is not null
    public void csvConvert() throws IOException {
        Set<String> yearSet = new HashSet<String>();
        String country = "";
        int fileColCounter = 0;
        File file = new File("C:/Users/buyat/Downloads/workingHours.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = " ";
        String[] tempArr;
        while ((line = br.readLine()) != null) {
            if (fileColCounter == 0) {
                fileColCounter++;
                continue;
            }
            tempArr = line.split(delimiter);
            country = tempArr[0];

            ArrayList<WorkingItem> things = countrySpaceItems.get(country);
            if (things == null) {
                things = new ArrayList<WorkingItem>();
            }
            // ArrayList<SpaceItem> createThing = new ArrayList<SpaceItem>();
            things.add(new WorkingItem(country, tempArr[1], tempArr[2], Double.parseDouble(tempArr[3])));
            countrySpaceItems.put(country, things);

            yearSet.add(tempArr[2]);
        }
        br.close();
        yearList = new ArrayList<String>(yearSet);
        String [] sorted = MergeSort.mergeSort(yearList);
        yearList = new ArrayList<String>(Arrays.asList(sorted));

    }
}
