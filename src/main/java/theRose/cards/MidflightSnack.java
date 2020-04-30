package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInHandAction;
import theRose.actions.ReduceFlightAction;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class MidflightSnack extends AbstractDynamicCard {

    /*
     * Midflight Snack: Can only be played in Flight. Draw 1 card. Create 1 random (Upgraded) Food item in your hand. Lose 1 Flight.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(MidflightSnack.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DRAW = 1;
    private static final int CREATE = 1;
    private static final int BUFF = 1;

    // /STAT DECLARATION/

    public MidflightSnack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = CREATE;
        BaseSecondMagicNumber = SecondMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Draw a card
        this.addToBot(new DrawCardAction(p, DRAW));

        // Reduce flight
        this.addToBot(new ReduceFlightAction(SecondMagicNumber));

        // Create random food item
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new RandomFoodInHandAction(this.upgraded));
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            // Can only use if in flight
            if (p.hasPower("Flight")) {
                canUse = true;
            }
            else {
                canUse = false;
                this.cantUseMessage = "Can only be used in flight.";
            }

            return canUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}