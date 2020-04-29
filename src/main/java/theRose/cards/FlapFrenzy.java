package theRose.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class FlapFrenzy extends AbstractDynamicCard {

    /*
     * Flap Frenzy: Play 1 Flipper Flap for each Flipper Flap played this combat. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FlapFrenzy.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 3;

    // /STAT DECLARATION/

    public FlapFrenzy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 0;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int flipperFlaps = 0;

        Iterator cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while(cardsPlayed.hasNext()) {
            AbstractCard card = (AbstractCard)cardsPlayed.next();
            if (card instanceof FlipperFlap) {
                ++flipperFlaps;
            }
        }

        //
        AbstractCard card = new FlipperFlap();
        for (int i = 0; i < flipperFlaps; i++) {
            // Play x Flipper Flappers
            card.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card,
                    null, card.energyOnUse, true, true), true);
        }
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