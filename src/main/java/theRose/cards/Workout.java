package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.SpotWeaknessAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Workout extends AbstractDynamicCard {

    /*
     * Workout: Gain 1 (2) Strength. Heal 3 HP.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Workout.class.getSimpleName());
    public static final String IMG = makeCardPath("Workout.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 1; // Give Strength
    private static final int BUFF_UPGRADE = 1; // Add 1 to upgrade
    private static final int HEAL = 3;

    // /STAT DECLARATION/

    public Workout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
        SecondMagicNumber = BaseSecondMagicNumber = HEAL;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain strength
        this.addToBot(new ApplyPowerAction(p, p,
                new StrengthPower(p, magicNumber)));

        // Heal
        this.addToBot(new HealAction(p, p, SecondMagicNumber));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(BUFF_UPGRADE);
            initializeDescription();
        }
    }
}