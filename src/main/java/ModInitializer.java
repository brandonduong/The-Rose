import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class ModInitializer {

    public ModInitializer() {
        //Used for when subscribe to any hooks in BaseMod
        //BaseMod.subscribe(this)
    }

    //Used by @SpireInitializer
    public static void initialize() {
        //Create an instance of our classes and gets our code going after
        //BaseMod and ModTheSpire initialize
        ModInitializer modInitializer = new ModInitializer();

        //Refer to BaseMod wiki to get started
        //(skipping the decomiling part for now as not needed)
    }
}