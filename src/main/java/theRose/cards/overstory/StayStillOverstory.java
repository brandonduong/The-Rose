package theRose.cards.overstory;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.cards.StayStill;

public class StayStillOverstory extends StayStill {

    /*
     * Stay Still: Can only be played if no Attack was played this turn. Apply !M! Passivity to all enemies. End your turn.
     */

    public StayStillOverstory() {
        super();
        this.exhaust = true;
        this.isEthereal = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        super.upgrade();
    }
}