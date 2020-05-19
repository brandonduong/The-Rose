package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class BeakSmash extends AbstractDynamicCard {

    /*
     * Beak Smash: Deal damage equal to !M! times your current Flight. Lose all Flight.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BeakSmash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int MULTIPLIER = 5;
    private static final int UPGRADE_MULTIPLIER = 1;

    // /STAT DECLARATION/

    public BeakSmash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MULTIPLIER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Only apply if in flight
        if (p.hasPower("Flight")) {
            baseDamage = p.getPower("Flight").amount * magicNumber;
            this.calculateCardDamage(m);

            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                    damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        // Lose all flight
        this.addToBot(new RemoveSpecificPowerAction(p, p, "Flight"));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTIPLIER);
            initializeDescription();
        }
    }
}