package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class NappyTime extends AbstractDynamicCard {

    /*
     * Nappy Time!: Gain 1 Plated Armor. End your turn.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(NappyTime.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 1;
    private static final int BUFF_UPGRADE = 1;

    // /STAT DECLARATION/

    public NappyTime() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain Plated Armor
        this.addToBot(new ApplyPowerAction(p, p,
                new PlatedArmorPower(p, magicNumber)));

        // End turn
        this.addToBot(new PressEndTurnButtonAction());
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(BUFF_UPGRADE);
            initializeDescription();
        }
    }
}