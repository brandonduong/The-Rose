package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;

import static theRose.ModInitializer.makeCardPath;

public class BigBlubber extends AbstractDynamicCard {

    /*
     * BigBlubber: Increase your Food Eaten counter by !M!. Gain Block equal to your Food Eaten counter.
     */


    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BigBlubber.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 2;
    private static final int UPGRADE_BUFF = 1;

    // /STAT DECLARATION/


    public BigBlubber() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Food eaten += 1
        this.addToBot(new ApplyPowerAction(p, p, new FoodEatenPower(p, p, magicNumber)));

        // Gain block
        int counter = 0;
        if (p.hasPower("theRose:FoodEatenPower")) {
            counter = p.getPower("theRose:FoodEatenPower").amount;
        }
        baseBlock = block = counter + magicNumber;
        this.applyPowersToBlock();

        this.addToBot(new GainBlockAction(p, p, block));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BUFF);
            initializeDescription();
        }
    }

}