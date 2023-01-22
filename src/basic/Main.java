package basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static final String delimiter = ",";

    public void csvConvert() throws IOException {
        String country = "";
        int fileColCounter = 0;
        HashMap<String, ArrayList<SpaceItem>> countrySpaceItems = new HashMap<String, ArrayList<SpaceItem>>();

        File file = new File("C:/Users/buyat/Downloads/objectsInSpace.csv");
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
            
            ArrayList<SpaceItem> things = countrySpaceItems.get(country);
            if (things == null) {
                things = new ArrayList<SpaceItem>();
            } 
            //ArrayList<SpaceItem> createThing = new ArrayList<SpaceItem>();
            things.add(new SpaceItem(country,tempArr[1], Integer.parseInt(tempArr[2]), Integer.parseInt(tempArr[3])));
            countrySpaceItems.put(country, things);

        }
        br.close();
    }

    public static void main(String[] args) throws IOException {

        Main main = new Main();

        main.csvConvert();
    }
}
