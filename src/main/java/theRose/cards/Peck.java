package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Peck extends AbstractDynamicCard {

    /*
     * Peck: Apply 1 (2) Vulnerable. If in flight, apply 1 (2) Weak. Deal damage equal to your current Flight.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Peck.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 1; // Vulnerable stacks
    private static final int UPGRADE_BUFF = 1;
    private static final int BUFF_2 = 1; // Weak stacks
    private static final int UPGRADE_BUFF_2 = 1;

    // /STAT DECLARATION/

    public Peck() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
        SecondMagicNumber = BaseSecondMagicNumber = BUFF_2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Always apply vulnerable
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.baseMagicNumber, false), this.baseMagicNumber));

        // Only apply weak if in flight
        if (p.hasPower("Flight")) {
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.SecondMagicNumber, false), this.SecondMagicNumber));

            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, p.getPower("Flight").amount, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BUFF);
            upgradeSecondMagicNumber(UPGRADE_BUFF_2);
            initializeDescription();
        }
    }
}