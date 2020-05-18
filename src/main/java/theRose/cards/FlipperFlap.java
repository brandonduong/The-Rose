package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class FlipperFlap extends AbstractDynamicCard {

    /*
     * Flipper Flap: Deal 1 (2) damage to a random enemy 2 times. Gain !M! Flight. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FlipperFlap.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 1;
    private static final int TIMES = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int BUFF = 1;

    // /STAT DECLARATION/

    public FlipperFlap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = TIMES;
        BaseSecondMagicNumber = SecondMagicNumber = BUFF;

        this.exhaust = true;
        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Create damage actions
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(p, damage),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        // Gain flight (Penguin Power)
        this.addToBot(new ApplyPowerAction(p, p,
                new PenguinPower(p, SecondMagicNumber)));
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