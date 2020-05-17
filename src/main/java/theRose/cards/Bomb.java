package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Bomb extends AbstractDynamicCard {

    /*
     * Agent Rose's Bomb: Whenever you draw this card, reduce its cost by !M!. Deal !D! to all enemies.
     *                    Play 1 Mission Accomplished. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Bomb.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 12;
    private static final int UPGRADE_COST = 10;
    private static final int DAMAGE = 100;
    private static final int REDUCE = 1;

    // /STAT DECLARATION/

    public Bomb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
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
        // Play card
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(new MissionAccomplished(), false));

        // Deal damage
        this.addToBot(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
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