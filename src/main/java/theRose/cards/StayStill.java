package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theRose.ModInitializer;
import theRose.characters.TheRose;
import theRose.powers.PassivityPower;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class StayStill extends AbstractDynamicCard {

    /*
     * Stay Still: Can only be played if no Attack was played this turn. Apply !M! Passivity to all enemies. End your turn.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(StayStill.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int STACKS = 9;
    private static final int UPGRADE_STACK = 3;

    // /STAT DECLARATION/

    public StayStill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = STACKS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Apply Passivity to all enemies

        Iterator monster_list = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        AbstractMonster monster;
        while(monster_list.hasNext()) {
            monster = (AbstractMonster)monster_list.next();
            this.addToBot(new ApplyPowerAction(monster, p, new PassivityPower(m, p, this.baseMagicNumber), this.baseMagicNumber));
        }

        // End turn
        this.addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            // Can only use if no attack played this turn
            if (!ModInitializer.playedAttack) {
                canUse = true;
            }
            else {
                canUse = false;
                this.cantUseMessage = "Can only be used if no attack played.";
            }

            return canUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STACK);
            initializeDescription();
        }
    }
}