package theRose.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.HungryFormPower;

import java.util.ArrayList;
import java.util.List;

import static theRose.ModInitializer.makeCardPath;

public class Reorder extends AbstractDynamicCard {

    /*
     * Reorder: Return all Exhausted Food items to your hand. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Reorder.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 3;

    // /STAT DECLARATION/

    public Reorder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // If no cards in exhaust pile, do nothing
        if (AbstractDungeon.player.exhaustPile.isEmpty()) {
            return;
        }

        // Return all Food items from the exhaust pile
        int exhaustSize = p.exhaustPile.size();
        CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
        List<AbstractCard> cardsToExhaust = new ArrayList<>();

        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};

        for (AbstractCard c : cardsToReturn.group) {
            if (c.tags.contains(CustomTags.FOOD)) {
                if (p.hand.size() >= BaseMod.MAX_HAND_SIZE){
                    AbstractDungeon.player.discardPile.addToTop(c);
                } else {
                    AbstractDungeon.player.hand.addToHand(c);
                }
                cardsToExhaust.add(c);
                c.unfadeOut();
                c.unhover();
                c.fadingOut = false;
            }
        }
        for (AbstractCard c : cardsToExhaust) {
            AbstractDungeon.player.exhaustPile.removeCard(c);
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}