package www.sumanmyon.com.fitnessapp.DataBase.FoodTrackingListDB;

public class TeaCoffeeDrinks {

    String[] teaCoffeeDrink = {
            "Tea",
            "Coffee",
            "Milk",
            "Coke",
            "Fanta",
            "Lemonade",
            "Pepsi",
            "Sprite"
    };

    int[] teaCoffeeDrinkCalorie = {
            2,
            2,
            103,
            149,
            138,
            149,
            156,
            131
    };

    String[] teaCoffeeDrinkServing = {
            "1 cup",
            "1 cup",
            "1 glass",
            "1 bottle",
            "1 bottle",
            "1 cup",
            "1 bottle",
            "1 bottle"
    };

    public String[] getTeaCoffeeDrink() {
        return teaCoffeeDrink;
    }

    public int[] getTeaCoffeeDrinkCalorie() {
        return teaCoffeeDrinkCalorie;
    }

    public String[] getTeaCoffeeDrinkServing() {
        return teaCoffeeDrinkServing;
    }
}
