package theRose.cards;

import basemod.BaseMod;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.HungryFormPower;

import static theRose.ModInitializer.makeCardPath;

public class HungryForm extends AbstractDynamicCard {

    /*
     * Hungry Form: At the start of your turn, consume 2 (3) random food item(s).
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(HungryForm.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int CONSUME = 2; // Consume 1
    private static final int CONSUME_UPGRADE = 1; // Add 1 to upgrade

    // /STAT DECLARATION/

    public HungryForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = CONSUME;
        this.tags.add(BaseModCardTags.FORM);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy times 2.
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new HungryFormPower(AbstractDungeon.player, AbstractDungeon.player, baseMagicNumber)));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(CONSUME_UPGRADE);
            initializeDescription();
        }
    }
}