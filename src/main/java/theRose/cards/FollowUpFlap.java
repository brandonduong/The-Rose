package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class FollowUpFlap extends AbstractDynamicCard {

    /*
     * FollowUpFlap: Deal !D! damage to a random enemy !M! times. Add !theRose:SecondMagic! Flipper Flap to your hand.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FollowUpFlap.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

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
    private static final int DAMAGE = 4;
    private static final int CREATE = 1;
    private static final int TIMES = 2;
    private static final int UPGRADE_TIMES = 1;

    // /STAT DECLARATION/

    public FollowUpFlap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = TIMES;
        BaseSecondMagicNumber = CREATE;

        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Create damage actions
        for (int i = 0; i < baseMagicNumber; i++) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(p, baseDamage),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        // Create a Flipper Flap
        AbstractCard card = new FlipperFlap();
        if (this.upgraded) {
            card.upgrade();
        }
        this.addToBot(new MakeTempCardInHandAction(card));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_TIMES);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}