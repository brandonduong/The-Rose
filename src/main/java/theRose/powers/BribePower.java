package theRose.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.cards.*;
import theRose.util.TextureLoader;

import java.util.concurrent.ThreadLocalRandom;

public class BribePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = ModInitializer.makeID("BribePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // Initialize milestone
    private int milestone = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power32.png");

    public BribePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();

    }

    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        // First milestone reached at >= 25% current HP
        if (milestone == 0 && this.amount >= this.owner.currentHealth / 4) {
            milestone += 1;
            this.addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new WeakPower(this.owner, 2, false)));
        }
        // Second milestone reached at >= 50% current HP
        else if (milestone == 1 && this.amount >= this.owner.currentHealth / 2) {
            milestone += 1;

            // Flee handling
            AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(this.owner));
            this.owner.currentHealth = 0;
            this.owner.isDead = true;
            this.owner.flipHorizontal = true;
            this.owner.escapeTimer = 2.0F;
        }

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.milestone + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BribePower(owner, source, amount);
    }
}
