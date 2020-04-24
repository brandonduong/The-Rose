package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.GlassKnife;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import static theRose.ModInitializer.makeCardPath;

public class Chat extends AbstractDynamicCard {

    /*
     * Chat: If an enemy does not intend to attack, apply !M! Passivity.
     * Increase this card's Passivity by !theRose:SecondMagic! for this combat.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Chat.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int STACKS = 7; // Apply 7 Passivity
    private static final int INCREASE = 2; // += 2
    private static final int UPGRADE_INCREASE = 2; // Add 2

    // /STAT DECLARATION/

    public Chat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = STACKS;
        BaseSecondMagicNumber = SecondMagicNumber = INCREASE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Only do effect if monster doesn't intend to do damage
        if (m != null && m.getIntentBaseDmg() <= 0) {
            // Apply Passivity
            this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, this.baseMagicNumber), this.baseMagicNumber));

            // Increase amount of stacks to give next use
            this.baseMagicNumber += this.BaseSecondMagicNumber;
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_INCREASE);
            initializeDescription();
        }
    }
}