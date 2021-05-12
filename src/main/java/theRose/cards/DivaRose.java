package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.DivaRosePower;
import theRose.powers.PassivityPower;

import static theRose.ModInitializer.makeCardPath;

public class DivaRose extends AbstractDynamicCard {

    /*
     * Diva Rose: Apply !M! Passivity. When this enemy flees, apply its Passivity to all enemies. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(DivaRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int STACKS = 6; // Passivity
    private static final int UPGRADE_STACK = 3;
    private static final int BUFF = 1;

    // /STAT DECLARATION/

    public DivaRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
        BaseSecondMagicNumber = SecondMagicNumber = BUFF;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new DivaRosePower(m, p, SecondMagicNumber)));

        // Apply Passivity
        this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, magicNumber), magicNumber));
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