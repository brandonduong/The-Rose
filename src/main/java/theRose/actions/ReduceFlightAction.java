package theRose.actions;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import theRose.ModInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReduceFlightAction extends AbstractGameAction {

    private int amt;

    public ReduceFlightAction(int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amt = amount;
    }

    public void update() {
        this.isDone = false;
        AbstractPlayer p = AbstractDungeon.player;

        if (!p.hasPower("Flight")) {
            this.isDone = true;
            return;
        }

        AbstractPower c = p.getPower("Flight");

        // If flight will go to zero, remove flight
        ModInitializer.logger.info(c.amount - this.amt);
        if (c.amount - this.amt <= 0){
            this.addToBot(new RemoveSpecificPowerAction(p, p, "Flight"));
            this.isDone = true;
            return;
        }

        // Else, reduce flight
        c.stackPower(-this.amt);
        this.isDone = true;
    }
}