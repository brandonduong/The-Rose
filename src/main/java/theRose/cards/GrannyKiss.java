package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import static theRose.ModInitializer.makeCardPath;

public class GrannyKiss extends AbstractDynamicCard {

    /*
     * Granny Kiss: Double (Triple) the enemy's Passivity. Exhaust."
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(GrannyKiss.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MULTIPLIER = 1;
    private static final int UPGRADE_MULTIPLIER = 1;

    // /STAT DECLARATION/

    public GrannyKiss() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MULTIPLIER;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int current = 0;
        if (m.hasPower("theRose:Passivity")) {
            current = m.getPower("theRose:Passivity").amount;
        }

        // Apply Passivity
        this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, current * magicNumber), current * magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTIPLIER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}