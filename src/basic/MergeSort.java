package basic;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {
    public static String[] mergeSort(ArrayList<String> yearList) {
        String[] output = new String[yearList.size()];
        String[] years = new String[yearList.size()];
        years = yearList.toArray(years);
        mergeCalc(years, 0, years.length - 1, output);

        return output;

    }

    private static void mergeCalc(String[] years, int from, int to, String[] temp) {
        // If the array length is greater than 1
        if (to - from >= 1) {
            int mid = (from + to) / 2; // split the list in half
            mergeCalc(years, from, mid, temp); // mergesort the first half
            mergeCalc(years, mid + 1, to, temp); // mergesort the second half
            merge(years, from, mid, to, temp); // merge
        }
    }

    private static void merge(String[] years, int from, int mid, int to, String[] temp) {
        int i = from; // track left array position
        int j = mid + 1; // track right array position
        int k = from; // track temp position

        while (i <= mid && j <= to) {
            // If the element in the left subarray is less
            // than the element in the right subarray it
            // is next in the merged list
            if (years[i].compareTo(years[j]) < 0) {
                temp[k] = years[i];
                i++;
            } else {
                temp[k] = years[j];
                j++;
            }
            k++;
        }

        // We may left over elements from either list
        while (i <= mid) {
            temp[k] = years[i];
            i++;
            k++;
        }

        while (j <= to) {
            temp[k] = years[j];
            j++;
            k++;
        }

        // Copy over elements from temp to arr
        for (k = from; k <= to; k++) {
            years[k] = temp[k];
        }
    }

    public static void main(String[] args) {
        String[] stringSearch = { "hi", "flag", "bye", "pie" };
        ArrayList<String> stringArr = new ArrayList<String>(Arrays.asList(stringSearch));
        MergeSort.mergeSort(stringArr);
    }
}
