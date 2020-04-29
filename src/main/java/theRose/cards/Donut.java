package theRose.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;
import theRose.powers.PenguinPower;
import theRose.cards.CustomTags;

import static theRose.ModInitializer.makeCardPath;

public class Donut extends AbstractDynamicCard {

    /*
     * Donut: Draw 1 Card. Lose 1 Flight. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Donut.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int DRAW = 1;
    private static final int FLIGHT = -1;

    private static final int FOOD_VALUE = 1;
    private static final int UPGRADE_VALUE = 1;

    // /STAT DECLARATION/

    public Donut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDraw = DRAW;
        SecondMagicNumber = FLIGHT;
        BaseSecondMagicNumber = - FLIGHT; // Display integer for description

        baseMagicNumber = FOOD_VALUE;

        this.tags.add(CustomTags.FOOD);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Draw cards
        this.addToBot(
                new DrawCardAction(p, draw));

        // Remove Flight if stacks will go to 0
        if (p.hasPower("Flight") && p.getPower("Flight").amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Flight"));
        }

        // Lose Flight
        else if (p.hasPower("Flight")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new PenguinPower(p, SecondMagicNumber)));
        }

        // Food eaten += 1
        this.addToBot(new ApplyPowerAction(p, p, new FoodEatenPower(p, p, baseMagicNumber)));

    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_VALUE);
            initializeDescription();
        }
    }
}