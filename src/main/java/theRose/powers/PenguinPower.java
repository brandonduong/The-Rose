package theRose.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRose.ModInitializer;

public class PenguinPower extends AbstractPower {

    public static final String POWER_ID = ModInitializer.makeID("PenguinFlight");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public PenguinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Flight";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("flight");
        this.priority = 50;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        this.updateDescription();
    }

    public float atDamageFinalReceive(float damage, DamageType type) {
        return this.calculateDamageTakenAmount(damage, type);
    }

    public float modifyBlock(float blockAmount) {
        return blockAmount * 0.75F;
    } // Reduce block by 25%

    private float calculateDamageTakenAmount(float damage, DamageType type) {
        return type != DamageType.HP_LOSS && type != DamageType.THORNS ? damage * 0.75F : damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        Boolean willLive = this.calculateDamageTakenAmount((float) damageAmount, info.type) < (float) this.owner.currentHealth;
        if (info.owner != null && info.type != DamageType.HP_LOSS && info.type != DamageType.THORNS && damageAmount > 0 && willLive) {
            this.flash();
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Flight", 1));
        }

        return damageAmount;
    }

    public void onRemove() {
        // this.addToBot(new ChangeStateAction((AbstractMonster)this.owner, "GROUNDED"));
    }

}
