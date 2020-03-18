package theRose.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class Fly extends AbstractDynamicCard {

    /*
     * Fly High to the Sky!: Gain !B! Block. Gain !M! Penguin Flight
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Fly.class.getSimpleName());
    public static final String IMG = makeCardPath("Fly.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int BUFF = 1; // Give 1 Penguin Flight
    private static final int BUFF_UPGRADE = 1; // Add 1 to upgrade

    // /STAT DECLARATION/

    public Fly() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BUFF;
        baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain block
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

        // Gain flight (Penguin Power)
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new PenguinPower(p, baseMagicNumber)));

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