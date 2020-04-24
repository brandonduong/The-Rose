package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Workout extends AbstractDynamicCard {

    /*
     * Workout: Gain 2 (3) Strength
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Workout.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 2; // Give 2 Strength
    private static final int BUFF_UPGRADE = 1; // Add 1 to upgrade

    // /STAT DECLARATION/

    public Workout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy times 2.
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, baseMagicNumber)));

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