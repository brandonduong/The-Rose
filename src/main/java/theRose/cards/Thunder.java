package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class Thunder extends AbstractDynamicCard {

    /*
     * Thunder: Unplayable. Whenever you draw this card, deal !D! damage to a random enemy, apply !M! Vulnerable, and Draw 1 card.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(Thunder.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int DAMAGE = 7;
    private static final int BUFF = 1;
    private static final int DRAW = 1;

    // /STAT DECLARATION/

    public Thunder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BUFF;
        SecondMagicNumber = BaseSecondMagicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void triggerWhenDrawn() {
        // Get random target
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null,
                true, AbstractDungeon.cardRandomRng);

        if (this.target != null) {
            // Deal damage
            this.addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage),
                    AbstractGameAction.AttackEffect.LIGHTNING));

            // Thunder effects
            AbstractDungeon.effectList.add(new LightningEffect(target.drawX, target.drawY));
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);

            // Apply vulnerable
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player,
                    new VulnerablePower(target, magicNumber, false), magicNumber));
        }

        this.addToBot(new DrawCardAction(SecondMagicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = "Can not be played.";
        return false;
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