package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodAction;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class EarlyBird extends AbstractDynamicCard {

    /*
     * Early Bird: Gain !M! Flight. Add 1 random Food items to your hand.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(EarlyBird.class.getSimpleName());
    public static final String IMG = makeCardPath("EarlyBird.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 1; // Give 1 Penguin Flight
    private static final int CREATE = 1; // Create random food items
    private static final int UPGRADE_CREATE = 1;


    // /STAT DECLARATION/

    public EarlyBird() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
        BaseSecondMagicNumber = CREATE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain flight (Penguin Power)
        this.addToBot(new ApplyPowerAction(p, p, new PenguinPower(p, baseMagicNumber)));

        // Create random food items
        for (int i = 0; i < SecondMagicNumber; i++) {
            this.addToBot(new RandomFoodAction(false));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_CREATE);
            initializeDescription();
        }
    }
}