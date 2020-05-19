package theRose.fields;

import basemod.abstracts.CustomSavable;
import static theRose.ModInitializer.foodEaten;

public class FoodEatenSave implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        return foodEaten;
    }

    @Override
    public void onLoad(Integer num) {
        foodEaten = num;
    }
}