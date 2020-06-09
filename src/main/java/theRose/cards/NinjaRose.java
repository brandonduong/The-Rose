package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInDiscardAction;
import theRose.actions.RandomFoodInDrawAction;
import theRose.characters.TheRose;
import theRose.powers.FliredUpPower;

import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class NinjaRose extends AbstractDynamicCard {

    /*
     * Ninja Rose: Gain !theRose:SecondMagic! Flired Up.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(NinjaRose.class.getSimpleName());
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
    private static final int BUFF = 4;
    private static final int UPGRADE_BUFF = 1;
    // /STAT DECLARATION/

    public NinjaRose() {
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