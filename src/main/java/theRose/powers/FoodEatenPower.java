package theRose.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.cards.FlipperFlap;
import theRose.util.TextureLoader;

public class FoodEatenPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = ModInitializer.makeID("FoodEatenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power32.png");

    private int milestone;

    public FoodEatenPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.milestone = 0;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        // First milestone reached at 5
        if (milestone == 0 && this.amount >= 5) {
            milestone += 1;
            // Gain strength
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
        }
        else if (milestone == 3 && this.amount < 15) {
            milestone -= 1;
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -1), -1));
        }

        // Second milestone reached at >= 10
        if (milestone == 1 && this.amount >= 10) {
            milestone += 1;
            // Gain strength
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1)));
        }
        else if (milestone == 2 && this.amount < 10) {
            milestone -= 1;
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -1), -1));
        }

        // Third milestone reached at >= 15
        if (milestone == 2 && this.amount >= 15) {
            milestone += 1;
            // Gain strength
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1)));
        }
        else if (milestone == 1 && this.amount < 5) {
            milestone -= 1;
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -1), -1));
        }

        updateDescription();

    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
        else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FoodEatenPower(owner, source, amount);
    }
}
