package theRose.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theRose.ModInitializer;
import theRose.actions.ReduceFlightAction;
import theRose.characters.TheRose;
import theRose.powers.FoodEatenPower;
import theRose.powers.PassivityPower;
import theRose.powers.PenguinPower;

import java.util.concurrent.ThreadLocalRandom;

import static theRose.ModInitializer.makeCardPath;

public class LunchLadyRose extends AbstractDynamicCard {

    /*
     * Sharing is Caring: Apply !M! Bribe. Apply a random Food item.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(LunchLadyRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BUFF = 9; // Bribe stacks
    private static final int UPGRADE_BUFF = 3; // Add 2 Bribe stacks

    // /STAT DECLARATION/

    public LunchLadyRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Apply Passivity
        this.addToBot(new ApplyPowerAction(m, p, new PassivityPower(m, p, this.magicNumber), this.magicNumber));

        // Apply a random food item
        // Insane amount of work
        AbstractCard card;
        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5); // Random num between [0, 4]

        card = foodCards[randomNum];

        // Food eaten += 1
        this.addToBot(new ApplyPowerAction(m, p, new FoodEatenPower(m, p, 1)));

        switch (randomNum) {
            case 0:
                // Donut does nothing
                this.addToBot(new TalkAction(m, "Thanks for the Donut!", 0.3F, 2.5F));

                // Reduce flight
                this.addToBot(new ReduceFlightAction(1));
                break;

            case 1:
                // Energy Drink gives Flight
                this.addToBot(new TalkAction(m, "Thanks for the Energy Drink!", 0.3F, 2.5F));
                this.addToBot(new ApplyPowerAction(m, p, new FlightPower(m, 1), 1));
                break;

            case 2:
                // Fried Chicken gives Strength
                this.addToBot(new TalkAction(m, "Thanks for the Fried Chicken!", 0.3F, 2.5F));
                this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, 1), 1));

                // Gain 1 Stacks of Lose Strength Power
                this.addToBot(new ApplyPowerAction(m, p, new LoseStrengthPower(m, 1), 1));
                break;

            case 3:
                // Hamburger gives Block + food eaten stacks
                this.addToBot(new TalkAction(m, "Thanks for the Hamburger!", 0.3F, 2.5F));
                this.addToBot(new GainBlockAction(m, p, 3));
                this.addToBot(new ApplyPowerAction(m, p, new FoodEatenPower(m, p, 1)));
                break;

            case 4:
                // Sushi gives Dexterity
                this.addToBot(new TalkAction(m, "Thanks for the Sushi!", 0.3F, 2.5F));
                this.addToBot(new ApplyPowerAction(m, p, new DexterityPower(m, 1), 1));

                // Gain 1 Stacks of Lose Dexterity Power
                this.addToBot(new ApplyPowerAction(m, p, new LoseDexterityPower(m, 1), 1));
                break;
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BUFF);
            initializeDescription();
        }
    }
}