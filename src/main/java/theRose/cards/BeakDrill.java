package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class BeakDrill extends AbstractDynamicCard {

    /*
     * Beak Drill: Deal 1 damage 4 (5) times.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BeakDrill.class.getSimpleName());
    public static final String IMG = makeCardPath("BeakDrill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = -1;
    private static final int DAMAGE = 1;
    private static final int TIMES = 4;
    private static final int UPGRADE_TIMES = 1;

    public int specialDamage;

    // /STAT DECLARATION/

    public BeakDrill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = TIMES;

        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy times 2.
        int effect = EnergyPanel.totalCount * magicNumber;

        // Cycle slash effects
        AbstractGameAction.AttackEffect[] slashes = {AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL};
        // For each energy, create 2 damage action.
        for (int i = 0; i < effect; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            slashes[i % 3]));
        }
        EnergyPanel.totalCount = 0; // Use up all energy
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_TIMES);
            initializeDescription();
        }
    }
}