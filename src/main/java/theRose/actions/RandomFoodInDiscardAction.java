package theRose.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRose.ModInitializer;
import theRose.cards.*;

import java.util.concurrent.ThreadLocalRandom;

public class RandomFoodInDiscardAction extends AbstractGameAction {
    // Make a random Food card in hand
    private AbstractPlayer p;
    private final boolean upgrade;

    public RandomFoodInDiscardAction(boolean upgrade, int amount) {
        this.upgrade = upgrade; // True if create random Upgraded Food item

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;

    }


    public void update() {
        AbstractCard card;
        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5); // Random num between [0, 4]

        card = foodCards[randomNum];

        if (this.upgrade) {
            card.upgrade();
        }

        this.addToBot(new MakeTempCardInDiscardAction(card, amount));
        this.isDone = true;
        return;
    }
}
