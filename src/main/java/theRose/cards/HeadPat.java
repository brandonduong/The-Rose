package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import static theRose.ModInitializer.makeCardPath;

public class HeadPat extends AbstractDynamicCard {

    /*
     * HeadPat: Deal !D! damage. Apply !M! Passivity. Add a copy of this card to your discard pile.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(HeadPat.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int STACKS = 3; // For ever 3 Bribe
    private static final int UPGRADE_STACK = 2; // Add 1
    private static final int DAMAGE = 1; // 1 Damage

    // /STAT DECLARATION/

    public HeadPat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = STACKS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal Damage
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Apply Passivity
        this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, this.baseMagicNumber), this.baseMagicNumber));

        // Add copy to discard
        this.addToBot(new MakeTempCardInDiscardAction(this, 1));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STACK);
            initializeDescription();
        }
    }
}