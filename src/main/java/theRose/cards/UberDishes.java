package theRose.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class UberDishes extends AbstractDynamicCard {

    /*
     * UberDishes: Create 4 (5) random (upgraded) food items in your discard pile.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(UberDishes.class.getSimpleName());
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

    private static final int CREATE = 4; // How many random food cards to create
    private static final int UPGRADE_CREATE = 1;

    // /STAT DECLARATION/

    public UberDishes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = CREATE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Create random Food cards
        AbstractCard card;
        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};
        int randomNum;

        for (int i = 0; i < baseMagicNumber; i++) {
            randomNum = ThreadLocalRandom.current().nextInt(0, 5); // Random num better [0, 4]
            card = foodCards[randomNum];

            if (this.upgraded) { // If this card is upgraded, create upgraded food cards.
                card.upgrade();
            }

            this.addToBot(new MakeTempCardInDiscardAction(card, 1));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_CREATE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}