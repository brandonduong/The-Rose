package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.SpotWeaknessAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.damagedLastTurn;
import static theRose.ModInitializer.makeCardPath;

public class Angy extends AbstractDynamicCard {

    /*
     * Angy: If you took damage last turn, gain !M! Strength and Draw 1 card.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Angy.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 2;
    private static final int DRAW = 1;
    private static final int UPGRADE_BUFF = 1;

    // /STAT DECLARATION/

    public Angy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
        SecondMagicNumber = BaseSecondMagicNumber = DRAW;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Draw cards
        this.addToBot(new DrawCardAction(SecondMagicNumber));

        if (damagedLastTurn) {
            // Gain strength
            this.addToBot(new ApplyPowerAction(p, p,
                    new StrengthPower(p, magicNumber)));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BUFF);
            initializeDescription();
        }
    }
}