package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class ShinyBeakAndMe extends AbstractDynamicCard {

    /*
     * Shiny Beak And Me!: Apply !M! Passivity and !theRose:SecondMagic! Weak to all enemies.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(ShinyBeakAndMe.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int STACKS = 5;
    private static final int BUFF = 1;
    private static final int UPGRADE_STACKS = 2;

    // /STAT DECLARATION/

    public ShinyBeakAndMe() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = STACKS;
        SecondMagicNumber = BaseSecondMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator monsters = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(monsters.hasNext()) {
            AbstractMonster mo = (AbstractMonster)monsters.next();
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, SecondMagicNumber, false),
                    SecondMagicNumber, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(mo, p, new PassivityPower(mo, p, magicNumber), magicNumber,
                    true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STACKS);
            initializeDescription();
        }
    }
}