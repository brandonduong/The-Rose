package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.red.ThunderClap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.actions.RandomFoodConsumeAction;
import theRose.actions.RandomFoodInHandAction;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class Hibernate extends AbstractDynamicCard {

    /*
     * Hibernate: Gain !B! Block. Consume !M! random Food items. Next turn, gain 1 !E!. End your turn.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Hibernate.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int CREATE = 1;
    private static final int ENERGY = 1;
    private static final int UPGRADE_ENERGY = 1;

    // /STAT DECLARATION/

    public Hibernate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = CREATE;
        BaseSecondMagicNumber = SecondMagicNumber = ENERGY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain block
        this.addToBot(new GainBlockAction(p, block));

        // Consume food
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new RandomFoodConsumeAction(false));
        }

        // Gain energy next turn
        this.addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, SecondMagicNumber), SecondMagicNumber));

        // End turn
        this.addToBot(new PressEndTurnButtonAction());
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_ENERGY);
            initializeDescription();
        }
    }
}