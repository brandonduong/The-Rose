package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class TripleJump extends AbstractDynamicCard {

    /*
     * Triple Jump: Deal !D! damage !M! times. Gain 1 Flight.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(TripleJump.class.getSimpleName());
    public static final String IMG = makeCardPath("TripleJump.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int BUFF = 1; // Give 1 Penguin Flight


    // /STAT DECLARATION/

    public TripleJump() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BUFF;

        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain flight (Penguin Power)
        this.addToBot(new ApplyPowerAction(p, p, new PenguinPower(p, baseMagicNumber)));

        // Deal damage
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));

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