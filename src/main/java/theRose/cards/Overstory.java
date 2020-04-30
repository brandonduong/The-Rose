package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInDiscardAction;
import theRose.actions.RandomFoodInHandAction;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Overstory extends AbstractDynamicCard {

    /*
     * Overstory: Add an (Upgraded) Ethereal Beak Dive, Chat, random Food item, and Stay Still! to your hand.
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
        AbstractCard card = new BeakDive();
        card.isEthereal = true;
        card.upgraded = this.upgraded;
        this.addToBot(new MakeTempCardInHandAction(card));

        // Create a Chat
        card = new Chat();
        card.isEthereal = true;
        card.upgraded = this.upgraded;
        this.addToBot(new MakeTempCardInHandAction(card));

        // Create random food item in hand
        this.addToBot(new RandomFoodInHandAction(this.upgraded));

        // Create a Stay Still!
        card = new StayStill();
        card.isEthereal = true;
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