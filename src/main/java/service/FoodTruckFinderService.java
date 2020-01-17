package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodTruckFinderService {

    private final String BASE_URL = "http://data.sfgov.org";
    private final String RESOURCE_URL = "/resource/bbb8-hzi6.json";
    private final String HTTP_GET = "GET";

    public String getAllFoodTrucksInSFJson() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(BASE_URL + RESOURCE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTP_GET);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

        } catch (Exception e) {
            System.out.println("Could not retrieve Food Truck data: " + e.getMessage());
        }

        return result.toString();
    }
}