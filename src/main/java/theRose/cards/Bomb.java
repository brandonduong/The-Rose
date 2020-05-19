package theRose.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import javax.smartcardio.Card;

import static theRose.ModInitializer.makeCardPath;

public class Bomb extends AbstractDynamicCard {

    /*
     * Agent Rose's Bomb: Whenever you draw this card, reduce its cost by !M!.
     *                    Create 1 Mission Accomplished in your hand. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Bomb.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 10;
    private static final int UPGRADE_COST = 8;
    private static final int REDUCE = 1;

    // /STAT DECLARATION/

    public Bomb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = REDUCE;
        this.exhaust = true;
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToBot(new ReduceCostAction(this.uuid, this.magicNumber));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create card in hand
        this.addToBot(new MakeTempCardInHandAction(new MissionAccomplished(), 1));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}