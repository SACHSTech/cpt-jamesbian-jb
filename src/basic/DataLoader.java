package basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        Collections.sort(countryList);
        return countryList;
    }

    // Merge Sort Algorithim
    public static void mergeSort(ArrayList<String> yearList) {
        ArrayList<String> temp = new ArrayList<String>(yearList);
        mergeSortCalc(yearList, 0, yearList, temp);

    }

    private static void mergeCalc(ArrayList<String> yearList, int from, int to, ArrayList<String> temp) {
        // If the array length is greater than 1
        if (to - from >= 1) {
            int mid = (from + to) / 2; // split the list in half
            mergeCalc(yearList, from, mid, temp); // mergesort the first half
            mergeCalc(yearList, mid + 1, to, temp); // mergesort the second half
            merge(yearList, from, mid, to, temp); // merge
        }
    }

    private static void merge(ArrayList<String> yearList, int from, int mid, int to, ArrayList<String> temp) {
        int i = from; // track left array position
        int j = mid + 1; // track right array position
        int k = from; // track temp position

        while (i <= mid && j <= to) {
            // If the element in the left subarray is less
            // than the element in the right subarray it
            // is next in the merged list
            if (arr[i] < arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        // We may left over elements from either list
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= to) {
            temp[k] = arr[j];
            j++;
            k++;
        }

        // Copy over elements from temp to arr
        for (k = from; k <= to; k++) {
            arr[k] = temp[k];
        }
    }

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
        mergeSort(yearList);
    }
}
