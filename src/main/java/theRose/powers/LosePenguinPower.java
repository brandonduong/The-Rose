package theRose.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import theRose.ModInitializer;

import theRose.util.TextureLoader;

public class LosePenguinPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = ModInitializer.makeID("LosePenguinPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power32.png");

    public LosePenguinPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) { // At the end of your turn
        this.flash();
        // Remove Flight if stacks will go to 0
        if (owner.hasPower("Flight") && owner.getPower("Flight").amount == this.amount) {
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, "Flight"));
        }

        // Lose Flight
        else if (owner.hasPower("Flight")) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FlightPower(this.owner, -this.amount), -this.amount));

        }

        // Remove LoseFlightPower
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "theRose:LosePenguinPower"));
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (-this.amount) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LosePenguinPower(owner, source, amount);
    }
}
