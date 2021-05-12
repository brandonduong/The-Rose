package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class EnergyDrink extends AbstractDynamicCard {

    /*
     * Energy Drink: Gain 1 energy. Gain 1 Flight. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(EnergyDrink.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int ENERGY_GAIN = 1;
    private static final int FLIGHT = 1;

    private static final int FOOD_VALUE = 1;
    private static final int UPGRADE_VALUE = 1;

    // /STAT DECLARATION/

    public EnergyDrink() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = FOOD_VALUE;
        SecondMagicNumber = BaseSecondMagicNumber = FLIGHT;

        this.tags.add(CustomTags.FOOD);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain Energy
        this.addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, ENERGY_GAIN), ENERGY_GAIN));

        // Gain Flight
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new PenguinPower(p, SecondMagicNumber)));

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