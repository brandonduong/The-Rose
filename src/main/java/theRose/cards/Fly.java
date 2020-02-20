package theRose.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;
import theRose.variables.RoseSecondMagicNumber;

import static theRose.ModInitializer.makeCardPath;

public class Fly extends AbstractDynamicCard {

    /*
     * Fly: Gain 1 Penguin Flight
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Fly.class.getSimpleName());
    public static final String IMG = makeCardPath("Fly.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int BUFF = 1; // Give 1 Penguin Flight
    private static final int BUFF_UPGRADE = 1; // Add 1 to upgrade

    // /STAT DECLARATION/

    public Fly() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy times 2.
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new PenguinPower(AbstractDungeon.player, baseMagicNumber)));

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