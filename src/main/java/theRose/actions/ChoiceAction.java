package theRose.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ChoiceAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean upgrade;

    private final ArrayList<AbstractCard> choices;

    public ChoiceAction(boolean upgrade, ArrayList<AbstractCard> choices) {
        this.upgrade = upgrade;

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = ActionType.CARD_MANIPULATION;

        this.choices = choices;
    }


    public void update() {
        CardGroup tmp;
        if (this.duration == Settings.ACTION_DUR_MED){
            tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : this.choices) {
                if (this.upgrade) {
                    c.upgrade();
                }
                tmp.addToTop(c);
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose", false);
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0){
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                c.unhover();
                if (this.p.hand.size() == 10){
                    this.p.createHandIsFullDialog();
                    this.p.discardPile.addToTop(c);
                }
                else{
                    this.p.hand.addToTop(c);
                }
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
