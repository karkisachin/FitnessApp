package www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB;

public class Fruit {

    String[] fruit = {
            "Apple",
            "Avocado",
            "Banana",
            "Cherries",
            "Grapes",
            "Lemon",
            "Mango",
            "Olives",
            "Papaya",
            "Pineapple"
    };

    int[] fruitCalorie = {
            95,
            320,
            111,
            4,
            104,
            17,
            202,
            2,
            215,
            453
    };

    String[] fruitServing = {
            "1 apple",
            "1 avocado",
            "1 banana",
            "1 cherry",
            "1 cup",
            "1 lemon",
            "1 mango",
            "1 olive",
            "1 fruit",
            "1 pineapple"
    };

    public String[] getFruit() {
        return fruit;
    }

    public int[] getFruitCalorie() {
        return fruitCalorie;
    }

    public String[] getFruitServing() {
        return fruitServing;
    }
}
