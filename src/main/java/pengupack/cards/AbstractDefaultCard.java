package pengupack.cards;

import basemod.abstracts.CustomCard;

public class AbstractDefaultCard extends CustomCard{
    public int defaultSecondMagicNumber; // New modifiable stat
    public int defaultBaseSecondMagicNumber;
    public boolean upgradedDefaultSecondMagicNumber; // True if card is upgraded
    public boolean isDefaultSecondMagicNumberModified; // True if card is modified

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
        isDefaultSecondMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade (when click on card to upgrade)
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If card is upgraded
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes out of combat
            isDefaultSecondMagicNumberModified = true; // True, to highlight the number changing to green
        }
    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // Handles upgrading
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by amount
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        upgradedDefaultSecondMagicNumber = true; // Trigger flag (now upgraded)

    }
}
