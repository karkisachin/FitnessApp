package www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB;

public class Meat {

    String[] meat = {
            "Beef",
            "Chicken",
            "Duck",
            "Goat",
            "Ostrich",
            "Pork",
            "Turkey"
    };

    int[] meatCalorie = {
            407,
            183,
            534,
            143,
            123,
            363,
            321
    };

    String[] meatServing = {
            "1 steak",
            "raw",
            "1 serving",
            "raw",
            "1 serving",
            "1 chop",
            "1 steak"
    };

    public String[] getMeat() {
        return meat;
    }

    public int[] getMeatCalorie() {
        return meatCalorie;
    }

    public String[] getMeatServing() {
        return meatServing;
    }
}
