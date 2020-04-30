package theRose.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.sun.org.apache.xpath.internal.operations.Mod;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInHandAction;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class RefrigeratorRaid extends AbstractDynamicCard {

    /*
     * Refrigerator Raid: Gain !M! Intangible. Fill your hand with (Upgraded) Food items. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(RefrigeratorRaid.class.getSimpleName());
    public static final String IMG = makeCardPath("RefrigeratorRaid.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int BUFF = 1;

    // /STAT DECLARATION/

    public RefrigeratorRaid() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain intangible
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber)));

        // Fill hand with random Food cards
        int create = BaseMod.DEFAULT_MAX_HAND_SIZE - p.hand.size() + 1;
        for(int i = 0; i < create; i++) {
            this.addToBot(new RandomFoodInHandAction(this.upgraded));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}