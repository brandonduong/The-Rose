package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.actions.ChoiceAction;
import theRose.characters.TheRose;

import java.util.ArrayList;
import java.util.Iterator;

import static theRose.ModInitializer.makeCardPath;

public class PickyEater extends AbstractDynamicCard {

    /*
     * Picky Eater: Choose one: Create 1 (Upgraded) Donut, Energy Drink, Fried Chicken, Hamburger, or Sushi, in your hand.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(PickyEater.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int CHOOSE = 1; // How many random food cards to create

    // /STAT DECLARATION/

    public PickyEater() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOOSE;
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Create list of choices
        ArrayList<AbstractCard> foodChoices = new ArrayList();
        foodChoices.add(new Donut());
        foodChoices.add(new EnergyDrink());
        foodChoices.add(new FriedChicken());
        foodChoices.add(new Hamburger());
        foodChoices.add(new Sushi());

        if (this.upgraded) {
            Iterator choices = foodChoices.iterator();

            while(choices.hasNext()) {
                AbstractCard c = (AbstractCard)choices.next();
                c.upgrade();
            }
        }

        // Create choice action
        this.addToBot(new ChoiceAction(this.upgraded, foodChoices));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}