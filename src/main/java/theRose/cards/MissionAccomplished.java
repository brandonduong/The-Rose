package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.RandomFoodInDiscardAction;
import theRose.actions.RandomFoodInHandAction;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class MissionAccomplished extends AbstractDynamicCard {

    /*
     * Mission Accomplished: Agent Rose has fulfilled her assigned duties. Gain !M! Gold. Deal !D! damage. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(MissionAccomplished.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int GOLD = 50;
    private static final int DAMAGE = 100;

    // /STAT DECLARATION/

    public MissionAccomplished() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = GOLD;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain gold
        this.addToBot(new GainGoldAction(magicNumber));

        // Speech Bubble
        this.addToBot(new TalkAction(true, "Mission Accomplished. Agent Rose out.", 0.3F, 2.5F));

        // Deal damage
        this.addToBot(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}