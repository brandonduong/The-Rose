package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInHandAction;
import theRose.characters.TheRose;
import theRose.powers.PenguinPower;

import static theRose.ModInitializer.makeCardPath;

public class EarlyBird extends AbstractDynamicCard {

    /*
     * Early Bird: Gain !M! Flight. Deal damage equal to 2 times your current Flight. Add a random Food item to your hand.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(EarlyBird.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 2; // Give 1 Penguin Flight
    private static final int CREATE = 1; // Create random food items
    private static final int UPGRADE_BUFF = 1;
    private static final int MULTIPLIER = 2;


    // /STAT DECLARATION/

    public EarlyBird() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = BUFF;
        BaseSecondMagicNumber = SecondMagicNumber = CREATE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain flight (Penguin Power)
        this.addToBot(new ApplyPowerAction(p, p, new PenguinPower(p, magicNumber)));

        // Deal damage
        // Only apply if in flight
        if (p.hasPower("Flight")) {
            baseDamage = damage = (p.getPower("Flight").amount + magicNumber) * MULTIPLIER;
            this.calculateCardDamage(m);

            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                    damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        else {
            baseDamage = damage = (magicNumber) * MULTIPLIER;
            this.calculateCardDamage(m);

            // Deal damage
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                    damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        // Create random food items
        for (int i = 0; i < SecondMagicNumber; i++) {
            this.addToBot(new RandomFoodInHandAction(false));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BUFF);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}