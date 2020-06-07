package theRose.fields;

import basemod.abstracts.CustomSavable;

import static theRose.ModInitializer.*;

public class KilledSave implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        return killed;
    }

    @Override
    public void onLoad(Integer num) {
        killed = num;
    }
}