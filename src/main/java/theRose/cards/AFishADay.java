package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;
import theRose.powers.LoseArtifactPower;

import static theRose.ModInitializer.makeCardPath;

public class AFishADay extends AbstractDynamicCard {

    /*
     * A Fish A Day: Gain !M! Artifact. At the end of this turn, lose !M! Artifact. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(AFishADay.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BUFF = 1;
    private static final int UPGRADE_BUFF = 1;

    // /STAT DECLARATION/

    public AFishADay() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain 2 Strength
        this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, baseMagicNumber), baseMagicNumber));

        // Gain 2 Stacks of Lose Artifact Power
        this.addToBot(new ApplyPowerAction(p, p, new LoseArtifactPower(p, p, baseMagicNumber), baseMagicNumber));

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