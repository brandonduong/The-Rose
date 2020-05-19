package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInHandAction;
import theRose.actions.ReduceFlightAction;
import theRose.characters.TheRose;
import theRose.powers.LoseArtifactPower;
import theRose.powers.LosePenguinPower;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class LucidDream extends AbstractDynamicCard {

    /*
     * Lucid Dream: Gain !M! Flight. At the end of your turn, lose !M! Flight.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(LucidDream.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BUFF = 2;
    private static final int UPGRADE_BUFF = 1;
    private static final int DRAW = 1;

    // /STAT DECLARATION/

    public LucidDream() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain Flight
        this.addToBot(new ApplyPowerAction(p, p, new PenguinPower(p, magicNumber), magicNumber));

        // Gain Stacks of Lose Flight
        this.addToBot(new ApplyPowerAction(p, p, new LosePenguinPower(p, p, magicNumber), magicNumber));

        // Draw a card
        this.addToBot(new DrawCardAction(p, DRAW));
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