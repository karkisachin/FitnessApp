package www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB;

public class Juice {

    String[] others = {
            "Apple Juice",
            "Banana Juice",
            "Carrot Juice",
            "Coconut Milk",
            "Coconut Water",
            "Cucumber Juice",
            "Grape Juice",
            "Lemon Juice",
            "Orange Juice",
            "Papaya Juice"
    };

    int[] othersCalorie = {
            110,
            120,
            96,
            559,
            46,
            24,
            144,
            50,
            110,
            139
    };

    String[] othersService = {
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass",
            "1 glass"
    };

    public String[] getOthers() {
        return others;
    }

    public int[] getOthersCalorie() {
        return othersCalorie;
    }

    public String[] getOthersService() {
        return othersService;
    }
}
