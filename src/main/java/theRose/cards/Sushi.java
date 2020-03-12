package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;

import static theRose.ModInitializer.makeCardPath;

public class Sushi extends AbstractDynamicCard {

    /*
     * Sushi: Gain 2 Dexterity. At the end of this turn, lose 2 Dexterity. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Sushi.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int BUFF = 1;

    private static final int FOOD_VALUE = 1;
    private static final int UPGRADE_VALUE = 1;

    // /STAT DECLARATION/

    public Sushi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
        BaseSecondMagicNumber = FOOD_VALUE;

        this.tags.add(CustomTags.FOOD);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain 1 Dexterity
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, baseMagicNumber), baseMagicNumber));

        // Gain 1 Stacks of Lose Dexterity Power
        this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, baseMagicNumber), baseMagicNumber));

        // Food eaten += 1
        this.addToBot(new ApplyPowerAction(p, p, new FoodEatenPower(p, p, BaseSecondMagicNumber)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_VALUE);
            initializeDescription();
        }
    }
}