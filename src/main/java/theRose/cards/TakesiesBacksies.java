package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class TakesiesBacksies extends AbstractDynamicCard {

    /*
     * Takesies Backsies: For every !M! Passivity, reduce Passivity by !M!, deal !D! damage, and consume a random Food item.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(TakesiesBacksies.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int STACKS = 5; // For ever 5 Bribe
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 1;

    // /STAT DECLARATION/

    public TakesiesBacksies() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = STACKS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard card;
        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};
        int randomNum;

        if (m.hasPower("theRose:PassivityPower")) {
            for (int i = m.getPower("theRose:PassivityPower").amount; i > baseMagicNumber; i -= baseMagicNumber) {

                // Reduce Bribe
                this.addToBot(new ReducePowerAction(m, p, "theRose:PassivityPower", baseMagicNumber));

                // Deal damage
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

                // Consume random Food items
                randomNum = ThreadLocalRandom.current().nextInt(0, 5); // Random num between [0, 4]
                card = foodCards[randomNum];
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, null, card.energyOnUse, true, true), true);
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            initializeDescription();
        }
    }
}