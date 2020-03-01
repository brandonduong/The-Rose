package theRose.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.BribePower;

import static theRose.ModInitializer.makeCardPath;

public class SharingIsCaring extends AbstractDynamicCard {

    /*
     * Sharing is Caring: Apply 3 Bribe for the number of Food items in your hand. Apply all Food items.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(SharingIsCaring.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 3; // Bribe stacks
    private static final int UPGRADE_BUFF = 2; // Add 2 Bribe stacks

    // /STAT DECLARATION/

    public SharingIsCaring() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        CardGroup foodCards = p.hand;
        for (AbstractCard c : foodCards.group) {
            if (c.tags.contains(CustomTags.FOOD)) {
                // Apply Bribe
                this.addToBot(new ApplyPowerAction(m, p, new BribePower(m, p, this.baseMagicNumber), this.baseMagicNumber));
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