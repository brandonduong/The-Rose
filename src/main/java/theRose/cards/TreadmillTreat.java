package theRose.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.TreadmillTreatFollowUpAction;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class TreadmillTreat extends AbstractDynamicCard {

    /*
     * Treadmill Treat: Draw and discard the top !M! cards in your draw pile. If a card is 0-cost, play it.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(TreadmillTreat.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DRAW = 3;
    private static final int UPGRADE_DRAW = 2;

    // /STAT DECLARATION/

    public TreadmillTreat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; i++) {
            this.addToBot(new TreadmillTreatFollowUpAction());
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            initializeDescription();
        }
    }
}