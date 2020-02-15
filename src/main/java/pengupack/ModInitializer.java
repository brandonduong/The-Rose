package pengupack;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.PostDrawHook;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


@SpireInitializer
public class ModInitializer implements PostDrawSubscriber, PostBattleSubscriber,
        PostExhaustSubscriber, PostDungeonInitializeSubscriber {

    // Instance Variables
    private int exhaust, total_exhaust;

    private void resetCounts() {
        total_exhaust = exhaust = 0;
    }

    public ModInitializer() {
        //Used for when subscribe to any hooks in BaseMod
        BaseMod.subscribe(this);
        resetCounts(); // Initialize exhaust counter to 0
    }

    //Used by @SpireInitializer
    public static void initialize() {
        //Create an instance of our classes and gets our code going after
        //BaseMod and ModTheSpire initialize
        ModInitializer modInitializer = new ModInitializer();

        //Refer to BaseMod wiki to get started
        //(skipping the decomiling part for now as not needed)
    }

    @Override
    public void receivePostDraw(AbstractCard card) {
        // After player draws a card
        System.out.println(card.name + " was drawn!");
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
        // After player plays a card with "Exhaust"

        exhaust++;
        total_exhaust++;
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        // After every battle
        System.out.println(exhaust + " cards were exhausted this battle.");
        System.out.println(total_exhaust + " cards were exhausted this act.");
        exhaust = 0; // Reset exhaust counter to 0
    }

    @Override
    public void receivePostDungeonInitialize() {
        // Runs before every new act
        resetCounts();
    }


}