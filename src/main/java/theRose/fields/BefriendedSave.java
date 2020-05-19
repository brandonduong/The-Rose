package theRose.fields;

import basemod.abstracts.CustomSavable;

import static theRose.ModInitializer.*;

public class BefriendedSave implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        return befriended;
    }

    @Override
    public void onLoad(Integer num) {
        befriended = num;
    }
}