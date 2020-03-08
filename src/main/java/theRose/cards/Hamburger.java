package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;

import static theRose.ModInitializer.makeCardPath;

public class Hamburger extends AbstractDynamicCard {

    /*
     * Hamburger: Gain 6 Block. This card counts as 3 Food items. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Hamburger.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int BLOCK = 6;

    private static final int FOOD_VALUE = 3;
    private static final int UPGRADE_VALUE = 3;

    // /STAT DECLARATION/

    public Hamburger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = FOOD_VALUE;

        this.tags.add(CustomTags.FOOD);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain Block
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

        // Food eaten += 3
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