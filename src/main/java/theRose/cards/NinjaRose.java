package theRose.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
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

public class NinjaRose extends AbstractDynamicCard {

    /*
     * NinjaRose: Choose one: Create any (Upgraded) "Rose" card in your hand. Ethereal. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(NinjaRose.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int CHOOSE = 1; // How many to choose

    // /STAT DECLARATION/

    public NinjaRose() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOOSE;
        this.isEthereal = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create list of choices
        ArrayList<AbstractCard> roseChoices = new ArrayList();
        roseChoices.add(new AgentRose());
        roseChoices.add(new BabyRose());
        roseChoices.add(new BigBrainRose());
        roseChoices.add(new BRose());
        roseChoices.add(new ChefRose());
        roseChoices.add(new DivaRose());
        // roseChoices.add(new ChefRose());
        roseChoices.add(new LunchLadyRose());
        roseChoices.add(new NinjaRose());
        roseChoices.add(new StealthRose());
        roseChoices.add(new WitchRose());


        if (this.upgraded) {
            Iterator choices = roseChoices.iterator();

            while(choices.hasNext()) {
                AbstractCard c = (AbstractCard)choices.next();
                c.upgrade();
            }
        }

        // Create choice action
        this.addToBot(new ChoiceAction(this.upgraded, roseChoices));
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