
package pnlp;
import javax.microedition.lcdui.*;
import pnlp.PNLPMIDlet.*;
import pnlp.Configuration.*;
import pnlp.MalariaUnderFiveReport.*;
import pnlp.MalariaOverFiveReport.*;
import pnlp.MalariaPregnantWomenReport.*;
import pnlp.MalariaStockOutsReport.*;

/**
 * J2ME Form allowing choice between new report or continuation
 * if continue, stores choice in config.
 * @author rgaudin
 */
public class UpdateOrNewForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command (Strings.EXIT_LABEL, Command.BACK, 1);
    private static final Command CMD_SAVE = new Command ("Valider", Command.OK, 1);
    private static final Command CMD_HELP = new Command (Strings.HELP_LABEL, Command.HELP, 2);

    private PNLPMIDlet midlet;

    private StringItem text;
    private ChoiceGroup selection;

    private Configuration config;

    public UpdateOrNewForm(PNLPMIDlet midlet) {
        super("Que voulez-vous faire ?");
        this.midlet = midlet;

        config = new Configuration();

        text = new StringItem(null, "Souhaitez vous reprendre le dernier rapport ou créer un nouveau?");
        String[] choices = {"Reprendre précédent", "Créer nouveau"};
        selection = new ChoiceGroup("Je souhaite:", ChoiceGroup.EXCLUSIVE, choices, null);

        append(text);
        append(selection);
        
        addCommand(CMD_SAVE);
        addCommand(CMD_EXIT);
        addCommand(CMD_HELP);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        // Help command displays Help Form
        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "update_or_new");
            this.midlet.display.setCurrent(h);
        }

        // exit command goes back to main menu.
        if (c == CMD_EXIT) {
            this.midlet.destroyApp(false);
            this.midlet.notifyDestroyed();
        }

        // save command stores choice in DB (if continue) or errors then
        // goes to main menu
        if (c == CMD_SAVE) {
            // continue existing does nothing.
            if (selection.getSelectedIndex() == 0) {
                Alert alert = new Alert("Poursuite de rapport", "Les dernières données ont été chargées.", null, AlertType.INFO);
                alert.setTimeout(3000);
                this.midlet.display.setCurrent(alert, this.midlet.mainMenu);
            } else {
                config.set("has_data", "false");
                // remove all report databases
                MalariaUnderFiveReport u5 = new MalariaUnderFiveReport();
                u5.delete();

                MalariaOverFiveReport o5 = new MalariaOverFiveReport();
                o5.delete();

                MalariaPregnantWomenReport pw = new MalariaPregnantWomenReport();
                pw.delete();

                MalariaStockOutsReport so = new MalariaStockOutsReport();
                so.delete();

                this.midlet.refreshMenu();
                this.midlet.display.setCurrent (this.midlet.mainMenu);
            }
        }
    }
}
