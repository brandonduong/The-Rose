package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FliredUpPower;

import static theRose.ModInitializer.makeCardPath;

public class BRose extends AbstractDynamicCard {

    /*
     * Body Builder Rose: Gain !theRose:SecondMagic! Flired Up.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BUFF = 3;
    private static final int UPGRADE_BUFF = 2;
    // /STAT DECLARATION/

    public BRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        BaseSecondMagicNumber = SecondMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain buff
        this.addToBot(new ApplyPowerAction(p, p,
                new FliredUpPower(p, p, SecondMagicNumber)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_BUFF);
            initializeDescription();
        }
    }
}