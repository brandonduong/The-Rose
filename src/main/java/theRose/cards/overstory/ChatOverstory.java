package theRose.cards.overstory;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.cards.Chat;

public class ChatOverstory extends Chat {

    /*
     * Chat: If an enemy does not intend to attack, apply !M! Passivity.
     */

    public ChatOverstory() {
        super();
        this.exhaust = true;
        this.isEthereal = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        super.upgrade();
    }
}