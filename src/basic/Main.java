package basic;

import SpaceThing;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static final String delimiter = ",";

    public void csvConvert() throws IOException {
        String country = "";
        int fileColCounter = 0;
        HashMap<String, ArrayList<SpaceThing>> countrySpaceThings = new HashMap<String, ArrayList<SpaceThing>>();

        File file = new File("C:/Users/buyat/Downloads/objectsInSpace.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = " ";
        String[] tempArr;
        while ((line = br.readLine()) != null) {
            if (fileColCounter == 0) {
                continue;
            }
            
            tempArr = line.split(delimiter);
            country = tempArr[0];
            countrySpaceThings.get("England");
            for (String tempStr : tempArr) {
                System.out.print(tempStr + " ");
            }
            System.out.println();

        }
        br.close();
    }

    public static void main(String[] args) throws IOException {

        Main main = new Main();

        main.csvConvert();
    }
}
