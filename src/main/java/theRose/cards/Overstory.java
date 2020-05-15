package theRose.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInHandAction;
import theRose.cards.overstory.BeakDiveOverstory;
import theRose.cards.overstory.ChatOverstory;
import theRose.cards.overstory.StayStillOverstory;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Overstory extends AbstractDynamicCard {

    /*
     * Overstory: Add an (Upgraded) Exhaustive Ethereal Beak Dive, Chat, random Food item, and Stay Still! to your hand.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Overstory.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;

    // /STAT DECLARATION/

    public Overstory() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create a Beak Dive
        AbstractCard card = new BeakDiveOverstory();
        card.upgraded = this.upgraded;
        this.addToBot(new MakeTempCardInHandAction(card));

        // Create a Chat
        card = new ChatOverstory();
        card.upgraded = this.upgraded;
        this.addToBot(new MakeTempCardInHandAction(card));

        // Create random food item in hand
        this.addToBot(new RandomFoodInHandAction(this.upgraded));

        // Create a Stay Still!
        card = new StayStillOverstory();
        card.upgraded = this.upgraded;
        this.addToBot(new MakeTempCardInHandAction(card));

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