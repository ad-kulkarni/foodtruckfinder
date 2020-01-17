package util;

import model.FoodTruck;

import java.util.Comparator;

public class FoodTruckComparator implements Comparator<FoodTruck> {

    @Override
    public int compare(FoodTruck ft1, FoodTruck ft2) {
        return ft1.getName().compareTo(ft2.getName());
    }
}
