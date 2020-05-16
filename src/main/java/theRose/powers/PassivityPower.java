package theRose.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theRose.ModInitializer;

import theRose.util.TextureLoader;

public class PassivityPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = ModInitializer.makeID("PassivityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // Initialize milestone
    private int milestone = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power32.png");

    public PassivityPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        if (milestone == 0 && this.amount >= this.owner.currentHealth * 0.25F) {
            milestone += 1;
            this.addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new WeakPower(this.owner, 2, false)));
        }
        // Second milestone reached at >= 50% current HP
        if (milestone == 1 && this.amount >= this.owner.currentHealth * 0.5F) {
            milestone += 1;
            this.addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new VulnerablePower(this.owner, 2, false)));
        }

        // Third milestone reached at >= 75% current HP
        if (milestone == 2 && this.amount >= (this.owner.currentHealth * 0.75F) ) {
            milestone += 1;
            this.addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new StrengthPower(this.owner, -2), -2, true));
        }

        // Fourth milestone reached at >= 100% current HP
        if (milestone == 3 && this.amount >= this.owner.currentHealth) {
            milestone += 1;

            this.owner.applyStartOfTurnPowers();
        }

        // Prevents softlocks
        if (AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty()) {
            AbstractDungeon.getCurrRoom().cannotLose = false;
        }

        updateDescription();

    }

    @Override
    public void atStartOfTurn() {
        onAfterUseCard(null, null);

        // Flee handling
        if (!this.owner.isDying && milestone == 4) {
            this.owner.isEscaping = true;
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this.owner, "Have a good day!", 0.3F, 2.5F));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction((AbstractMonster) this.owner));
        }
    }

    @Override
    public void onInitialApplication() {
        atStartOfTurn();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.milestone + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PassivityPower(owner, source, amount);
    }
}
