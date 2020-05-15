package theRose.cards.overstory;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.cards.BeakDive;

public class BeakDiveOverstory extends BeakDive {

    /*
     * Beak Dive: Can only be played when in flight. Deal !D! damage. Lose all flight.
     */

    // /STAT DECLARATION/

    public BeakDiveOverstory() {
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