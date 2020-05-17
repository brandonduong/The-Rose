package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.ReduceFlightAction;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;

import static theRose.ModInitializer.makeCardPath;

public class TreadmillTreat extends AbstractDynamicCard {

    /*
     * Treadmill Treat: Draw and discard the top !M! cards in your draw pile. If a card is 0-cost, play it.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(TreadmillTreat.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DRAW = 3;
    private static final int UPGRADE_DRAW = 2;

    // /STAT DECLARATION/

    public TreadmillTreat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card;
        int cards = p.drawPile.size();

        for(int i = 0; i < magicNumber && i < cards; i++) {
            card = p.drawPile.getTopCard();
            if (card.cost == 0) {
                this.addToBot(new DrawCardAction(1));
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, true,
                        0, true, true));
            }

            else {
                this.addToBot(new DrawCardAction(1));
                this.addToBot(new DiscardSpecificCardAction(card));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            initializeDescription();
        }
    }
}