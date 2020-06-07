package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import static theRose.ModInitializer.makeCardPath;

public class LunchLadyRose extends AbstractDynamicCard {

    /*
     * Sharing is Caring: Apply !M! Bribe for the number of Food items in your hand. Exhaust all Food items.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(LunchLadyRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 5; // Bribe stacks
    private static final int UPGRADE_BUFF = 2; // Add 2 Bribe stacks

    // /STAT DECLARATION/

    public LunchLadyRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        CardGroup foodCards = p.hand;
        for (AbstractCard c : foodCards.group) {
            if (c.tags.contains(CustomTags.FOOD)) {
                // Apply Passivity
                this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, this.baseMagicNumber), this.baseMagicNumber));

                // Exhaust all Food items
                this.addToBot(new ExhaustSpecificCardAction(c, foodCards));
            }
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