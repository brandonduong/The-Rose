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
     * Midflight Snack: Can only be played in Flight. Create 1 random (Upgraded) Food item in your hand. Draw 1 card. Lose 1 Flight.
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

    private static final int COST = 1;
    private static final int CREATE = 1;
    private static final int BUFF = 1;
    private static final int MULTIPLIER = 2;

    // /STAT DECLARATION/

    public MidflightSnack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = CREATE;
        BaseSecondMagicNumber = SecondMagicNumber = MULTIPLIER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage
        // Only apply if in flight
        if (p.hasPower("Flight")) {
            baseDamage = damage = p.getPower("Flight").amount * MULTIPLIER;

            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                    damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        // Create random food item
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new RandomFoodInHandAction(this.upgraded));
        }

        // Reduce flight
        this.addToBot(new ReduceFlightAction(BUFF));
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