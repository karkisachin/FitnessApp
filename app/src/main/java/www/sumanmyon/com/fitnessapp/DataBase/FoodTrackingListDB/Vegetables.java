package www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB;

public class Vegetables {

    String[] vegetable ={
            "Bell Pepper",
            "Broccoli",
            "Cabbage",
            "Carrot",
            "Cauliflower",
            "Green Beans",
            "Mushrooms",
            "Mustard Greens",
            "Peas",
            "Potato",
            "Pumpkin",
            "Radishes",
            "Red Cabbage",
            "Spinach",
            "Sweet Potato",
            "Tomato"
    };

    int[] vegetableCalorie ={
            15,
            207,
            227,
            25,
            3,
            34,
            1,
            15,
            79,
            164,
            51,
            1,
            7,
            78,
            112,
            20
    };

    String[] vegetableServing ={
            "1 pepper",
            "1 bunch",
            "1 head",
            "1 carrot",
            "1 floweret",
            "1 cup",
            "1 mushroom",
            "1 cup, chopped",
            "1 cup",
            "1 potato",
            "1 pumpkin",
            "1 radish",
            "1 leaf",
            "1 bunch",
            "1 potato",
            "1 tomato"
    };

    public String[] getVegetable() {
        return vegetable;
    }

    public int[] getVegetableCalorie() {
        return vegetableCalorie;
    }

    public String[] getVegetableServing() {
        return vegetableServing;
    }
}
