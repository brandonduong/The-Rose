package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodFightPower;

import static theRose.ModInitializer.makeCardPath;

public class FoodFight extends AbstractDynamicCard {

    /*
     * Food Fight: Whenever you play a Food item, deal !D! damage to a random enemy.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FoodFight.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 3; // Damage each food item
    private static final int BUFF_UPGRADE = 1;

    // /STAT DECLARATION/

    public FoodFight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Gain food fight power
        this.addToBot(new ApplyPowerAction(p, p,
                new FoodFightPower(p, p, baseMagicNumber)));

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