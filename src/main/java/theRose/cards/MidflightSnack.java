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
import theRose.actions.RandomFoodConsumeAction;
import theRose.actions.RandomFoodInHandAction;
import theRose.actions.ReduceFlightAction;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class MidflightSnack extends AbstractDynamicCard {

    /*
     * Midflight Snack:
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(MidflightSnack.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int CREATE = 3;
    private static final int UPGRADE_CREATE = 1;
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
            this.addToBot(new RandomFoodConsumeAction(this.upgraded));
        }

        // Lose all flight
        this.addToBot(new RemoveSpecificPowerAction(p, p, "Flight"));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_CREATE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}