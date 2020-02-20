package theRose.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbstractDefaultCard extends CustomCard{
    public int SecondMagicNumber; // New modifiable stat
    public int BaseSecondMagicNumber;
    public boolean upgradedSecondMagicNumber; // True if card is upgraded
    public boolean isSecondMagicNumberModified; // True if card is modified

    public AbstractDefaultCard(final String id,
                               final String name,
                               final String img,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Initialize default values
        isCostModified = false;
        isCostModifiedForTurn = false;

        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade (when click on card to upgrade)
        super.displayUpgrades();
        if (upgradedSecondMagicNumber) { // If card is upgraded
            SecondMagicNumber = BaseSecondMagicNumber; // Show how the number changes out of combat
            isSecondMagicNumberModified = true; // True, to highlight the number changing to green
        }
    }

    public void upgradeSecondMagicNumber(int amount) { // Handles upgrading
        BaseSecondMagicNumber += amount; // Upgrade the number by amount
        SecondMagicNumber = BaseSecondMagicNumber;
        upgradedSecondMagicNumber = true; // Trigger flag (now upgraded)
    }

    public void upgrade(){
        assert true; // Leave to be overwritten later
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        assert true; // Leave to be overwritten later
    }
}
