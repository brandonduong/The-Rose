package theRose.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInDiscardAction;
import theRose.actions.RandomFoodInDrawAction;
import theRose.characters.TheRose;

import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class BabyRose extends AbstractDynamicCard {

    /*
     * Baby Rose: Create !M! Picky Eater in your discard pile.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BabyRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int CREATE = 2;
    // /STAT DECLARATION/

    public BabyRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CREATE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = new PickyEater();
        this.addToBot(new MakeTempCardInDiscardAction(card, magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}