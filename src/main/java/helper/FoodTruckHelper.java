package helper;

import model.FoodTruck;
import org.json.JSONArray;
import org.json.JSONObject;
import service.FoodTruckFinderService;
import util.DateUtils;
import util.FoodTruckComparator;
import util.StringFormatUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static util.Constants.DAY_OF_THE_WEEK;
import static util.Constants.DISPLAY_ADDRESS;
import static util.Constants.DISPLAY_NAME;
import static util.Constants.END_TIME;
import static util.Constants.FOOD_TRUCK_ADDRESS;
import static util.Constants.FOOD_TRUCK_NAME;
import static util.Constants.LINE_BREAK;
import static util.Constants.LINE_FORMAT;
import static util.Constants.PAGE_LIMIT;
import static util.Constants.START_TIME;
import static util.Constants.USER_OPTION_NEXT_PAGE;
import static util.Constants.USER_OPTION_PREVIOUS_PAGE;

public class FoodTruckHelper {

    public List<FoodTruck> getAllOpenFoodTrucksInSF(FoodTruckFinderService foodTruckFinderService) {

        try {
            JSONArray foodTrucksJsonArray = new JSONArray(foodTruckFinderService.getAllFoodTrucksInSFJson());

            return getAllOpenFoodTrucks(foodTrucksJsonArray, DateUtils.getCurrentDay(), DateUtils.getCurrentTimeOfTheDay());
        } catch (ParseException e) {
            System.out.println("Food Truck data could not be retrieved/parsed!");
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<FoodTruck> getAllOpenFoodTrucks(JSONArray foodTruckDataArray, String currentDay, Date currentTime) throws ParseException {

        List<FoodTruck> foodTrucks = new ArrayList<>();

        for(int i=0; i<foodTruckDataArray.length(); i++) {
            JSONObject foodTruckJson = (JSONObject) foodTruckDataArray.get(i);

            String dayOfWeek = foodTruckJson.getString(DAY_OF_THE_WEEK);
            String startTimeString = foodTruckJson.getString(START_TIME);
            String endTimeString = foodTruckJson.getString(END_TIME);

            Date startTime = DateUtils.getDateFormatForTime().parse(startTimeString);
            Date endTime = DateUtils.getDateFormatForTime().parse(endTimeString);

            if(currentDay.equalsIgnoreCase(dayOfWeek) && currentTime.after(startTime) && currentTime.before(endTime)) {

                FoodTruck foodTruck = new FoodTruck();
                foodTruck.setName(foodTruckJson.getString(FOOD_TRUCK_NAME));
                foodTruck.setAddress(foodTruckJson.getString(FOOD_TRUCK_ADDRESS));

                foodTrucks.add(foodTruck);
            }
        }

        if(!foodTrucks.isEmpty()) {
            Collections.sort(foodTrucks, new FoodTruckComparator());
        }

        return foodTrucks;
    }

    public void displayPaginatedResultsBasedOnUserInput(List<FoodTruck> foodTrucks) {
        Map<Integer, List<FoodTruck>> foodTrucksPaginatedMap = new HashMap<>();

        List<FoodTruck> foodTrucksByPage = new ArrayList<>();
        int counter = 1;
        for(FoodTruck foodTruck : foodTrucks) {
            foodTrucksByPage.add(foodTruck);
            if(counter++ % PAGE_LIMIT == 0) {
                foodTrucksPaginatedMap.put(counter / PAGE_LIMIT, foodTrucksByPage);
                foodTrucksByPage = new ArrayList<>();
            }
        }
        if(!foodTrucksByPage.isEmpty()) {
            foodTrucksPaginatedMap.put((counter + PAGE_LIMIT) / PAGE_LIMIT, foodTrucksByPage);
        }

        int page = 1;
        int totalPages = foodTrucks.size() % PAGE_LIMIT == 0 ? foodTrucks.size() / PAGE_LIMIT : (foodTrucks.size() / PAGE_LIMIT) + 1;

        System.out.println(LINE_FORMAT);
        System.out.println(LINE_FORMAT);
        System.out.println("Enter '1' to navigate to Next page");
        System.out.println("Enter '2' to navigate to Previous page");
        System.out.println("Press any other character key to exit");
        System.out.println(LINE_FORMAT);
        System.out.println(LINE_FORMAT);

        System.out.println(foodTrucks.size() + " food trucks are open right now");

        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(page == 1) {
                printFoodTruckList(foodTrucksPaginatedMap.get(page));
            }
            if(page != 1) {
                System.out.print("(" + (page - 1) + ")" + " <-- Previous page  ");
            }
            if(page != totalPages) {
                System.out.println("Next page --> " + "(" + (page + 1) + ")");
            }

            String userInput = scanner.next();
            if(USER_OPTION_NEXT_PAGE.equalsIgnoreCase(userInput)) {
                if(++page <= totalPages) {
                    printFoodTruckList(foodTrucksPaginatedMap.get(page));
                } else {
                    page = totalPages;
                }
            } else if(USER_OPTION_PREVIOUS_PAGE.equalsIgnoreCase(userInput)) {
                if(--page > 0) {
                    printFoodTruckList(foodTrucksPaginatedMap.get(page));
                } else {
                    page = 1;
                }
            } else {
                System.exit(0);
            }
        }
    }

    public void printFoodTruckList(List<FoodTruck> foodTrucks) {
        System.out.println(LINE_FORMAT);
        System.out.format(StringFormatUtils.FORMAT_LEFT_ALIGN + LINE_BREAK, DISPLAY_NAME, DISPLAY_ADDRESS);
        System.out.println(LINE_FORMAT);
        for (FoodTruck foodTruck : foodTrucks) {
            System.out.format(StringFormatUtils.FORMAT_LEFT_ALIGN + LINE_BREAK, foodTruck.getName(), foodTruck.getAddress() + LINE_BREAK);
        }
        System.out.println(LINE_FORMAT + LINE_BREAK + LINE_BREAK);
    }
}
