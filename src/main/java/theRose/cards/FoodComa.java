package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;

import static theRose.ModInitializer.makeCardPath;

public class FoodComa extends AbstractDynamicCard {

    /*
     * Food Coma: Increase your Food Eaten counter by !M!. Deal damage to all enemies equal to your Food Eaten counter. End your turn.
     */


    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FoodComa.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int BUFF = 4;

    // /STAT DECLARATION/


    public FoodComa() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Food eaten += 1
        this.addToBot(new ApplyPowerAction(p, p, new FoodEatenPower(p, p, magicNumber)));

        baseDamage = p.getPower("theRose:FoodEatenPower").amount;

        // Deal damage
        this.addToBot(
                new DamageAllEnemiesAction(p, baseDamage + magicNumber, damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

}