package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class FoodFit extends AbstractDynamicCard {

    /*
     * Food Fit: Deal !D! damage to all enemies. If no Food item was played this combat, deal an extra !M! damage.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(FoodFit.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 9;
    private static final int DAMAGE_BUFF = 5;
    private static final int UPGRADE_DAMAGE_BUFF = 2;

    // /STAT DECLARATION/

    public FoodFit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DAMAGE_BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Check if a food was played this combat
        Iterator cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();
        boolean foodPlayed = false;

        while(cardsPlayed.hasNext()) {
            AbstractCard card = (AbstractCard)cardsPlayed.next();
            if (card.tags.contains(CustomTags.FOOD)) {
                foodPlayed = true;
            }
        }

        // Deal damage according to condition
        if (foodPlayed) {
            this.addToBot(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        else {
            this.addToBot(new DamageAllEnemiesAction(p, baseDamage + magicNumber, damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_BUFF);
            upgradeMagicNumber(UPGRADE_DAMAGE_BUFF);
            initializeDescription();
        }
    }
}