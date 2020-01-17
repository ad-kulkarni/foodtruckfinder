import helper.FoodTruckHelper;
import model.FoodTruck;
import service.FoodTruckFinderService;

import java.util.List;

import static util.Constants.PAGE_LIMIT;

public class FoodTruckMain {

    private static FoodTruckFinderService foodTruckFinderService;
    private static FoodTruckHelper foodTruckHelper;

    public static void main(String[] args) {

        initializeComponents();

        List<FoodTruck> foodTrucks = foodTruckHelper.getAllOpenFoodTrucksInSF(foodTruckFinderService);

        if(foodTrucks.isEmpty()) {
            System.out.println("No food trucks open right now!");
            return;
        }

        if(foodTrucks.size() <= PAGE_LIMIT) {
            foodTruckHelper.printFoodTruckList(foodTrucks);
        } else {
            foodTruckHelper.displayPaginatedResultsBasedOnUserInput(foodTrucks);
        }
    }

    private static void initializeComponents() {
        foodTruckFinderService = new FoodTruckFinderService();
        foodTruckHelper = new FoodTruckHelper();
    }
}
