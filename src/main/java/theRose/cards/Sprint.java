package theRose.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Sprint extends AbstractDynamicCard {

    /*
     * Sprint: Can only be played if no Food item was played this turn and not in Flight. Draw !M! Cards.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Sprint.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DRAW = 2;
    private static final int UPGRADE_DRAW = 1;

    // /STAT DECLARATION/

    public Sprint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Draw cards
        this.addToBot(new DrawCardAction(p, baseMagicNumber));

        // Lose all flight
        this.addToBot(new RemoveSpecificPowerAction(p, p, "Flight"));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            // Can only use if no attack played this turn and not in flight
            if (!ModInitializer.playedFood) {
                canUse = true;
            }
            else if (ModInitializer.playedFood){
                canUse = false;
                this.cantUseMessage = "Can only be used if no food consumed.";
            }

            return canUse;
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