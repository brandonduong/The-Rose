package theRose.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class BigBrainRose extends AbstractDynamicCard {

    /*
     * Big Brain Rose: Scry !M!. Draw 1 card.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BigBrainRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int SCRY = 5;
    private static final int UPGRADE_SCRY = 2;
    private static final int DRAW = 1;

    // /STAT DECLARATION/

    public BigBrainRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = SCRY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Scry
        this.addToBot(new ScryAction(magicNumber));

        // Draw cards
        this.addToBot(new DrawCardAction(p, DRAW));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_SCRY);
            initializeDescription();
        }
    }
}