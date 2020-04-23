package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.LoseThornsPower;

import static theRose.ModInitializer.makeCardPath;

public class FluffyFurFlex extends AbstractDynamicCard {

    /*
     * Fluffy Fur Flex: Gain !B! Block. Gain !M! Thorns. At the end of your turn, lose !M! Thorns.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FluffyFurFlex.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_BLOCK = 4;
    private static final int BUFF = 4; // Thorns

    // /STAT DECLARATION/

    public FluffyFurFlex() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain block
        this.addToBot(new GainBlockAction(p, p, block));

        // Gain thorns
        this.addToBot(new ApplyPowerAction(p, p,
                new ThornsPower(p, baseMagicNumber)));

        // Gain losethorns
        this.addToBot(new ApplyPowerAction(p, p,
                new LoseThornsPower(p, p, baseMagicNumber)));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}