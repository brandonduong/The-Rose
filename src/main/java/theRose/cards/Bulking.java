package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodConsumeAction;
import theRose.characters.TheRose;
import theRose.powers.BulkingPower;

import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class Bulking extends AbstractDynamicCard {

    /*
     * Bulking: Whenever a Food item is eaten this turn, gain !B! Block. Consume 1 Food item.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Bulking.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BLOCK = 1;
    private static final int UPGRADE_BLOCK = 1;
    private static final int CONSUME = 1;

    // /STAT DECLARATION/

    public Bulking() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.BaseSecondMagicNumber = SecondMagicNumber = BLOCK;
        this.baseMagicNumber = CONSUME;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain !B! Bulking power
        this.addToBot(new ApplyPowerAction(p, p, new BulkingPower(p, p, SecondMagicNumber)));

        // Consume random food item
        this.addToBot(new RandomFoodConsumeAction(false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}