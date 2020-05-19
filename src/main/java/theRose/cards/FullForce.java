package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class FullForce extends AbstractDynamicCard {

    /*
     * Full Force: Deal !D! damage. Gain !B! Block. If a Food item was eaten this turn, deal and gain !M! extra.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FullForce.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int BLOCK = 3;
    private static final int DAMAGE_BLOCK_BUFF = 2;
    private static final int UPGRADE_BASE = 1;
    private static final int UPGRADE_BUFF = 2;

    // /STAT DECLARATION/

    public FullForce() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = DAMAGE_BLOCK_BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Check if a food was played this combat
        Iterator cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        boolean foodPlayed = false;

        while(cardsPlayed.hasNext()) {
            AbstractCard card = (AbstractCard)cardsPlayed.next();
            if (card.tags.contains(CustomTags.FOOD)) {
                foodPlayed = true;
            }
        }

        // Deal damage according to condition
        if (foodPlayed) {
            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage + magicNumber), AbstractGameAction.AttackEffect.FIRE));

            // Gain block
            this.addToBot(new GainBlockAction(p, p, block + magicNumber));
        }

        else {
            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.FIRE));

            // Gain block
            this.addToBot(new GainBlockAction(p, p, block));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BASE);
            upgradeBlock(UPGRADE_BASE);
            upgradeMagicNumber(UPGRADE_BUFF);
            initializeDescription();
        }
    }
}