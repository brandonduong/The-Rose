package theRose.cards;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class AbstractDynamicCard extends AbstractDefaultCard {

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost,
                languagePack.getCardStrings(id).DESCRIPTION, type,
                color, rarity, target);

        // Automatically get card name and description from language pack
    }

}
