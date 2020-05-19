package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Squish extends AbstractDynamicCard {

    /*
     * Squish: If your current health is !M! times the enemy's, set their HP to 0. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Squish.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int THRESHOLD = 2; // Only work if 2 times their hp
    private static final int THRESHOLD_UPGRADE = -1; // Only work if 1 times their hp

    // /STAT DECLARATION/

    public Squish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = THRESHOLD;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.currentHealth * magicNumber <= p.currentHealth) { // If enemy health below threshold, kill
            this.addToBot(new DamageAction(m, new DamageInfo(p, p.currentHealth), AbstractGameAction.AttackEffect.SMASH));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(THRESHOLD_UPGRADE);
            initializeDescription();
        }
    }
}