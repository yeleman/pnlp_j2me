
package pnlp;
import javax.microedition.lcdui.*;
import pnlp.PNLPMIDlet.*;
import pnlp.Configuration.*;

/**
 * J2ME Form allowing choice between new report or continuation
 * if continue, stores choice in config.
 * @author rgaudin
 */
public class UpdateOrNewForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SAVE = new Command ("Enreg.", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);

    private PNLPMIDlet midlet;

    private StringItem text;
    private ChoiceGroup selection;

    private Configuration config;

    public UpdateOrNewForm(PNLPMIDlet midlet) {
        super("Que voulez-vous faire ?");
        this.midlet = midlet;

        config = new Configuration();

        text = new StringItem(null, "Souhaitez vous reprendre le dernier rapport ou créer un nouveau?");
        String[] choices = {"Créer nouveau", "Reprendre précédent"};
        selection = new ChoiceGroup("Je souhaite:", ChoiceGroup.POPUP, choices, null);

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
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        // save command stores choice in DB (if continue) or errors then
        // goes to main menu
        if (c == CMD_SAVE) {
            if (selection.getSelectedIndex() == 1) {
                Alert alert = new Alert("Poursuite de rapport", "Les dernières données entrées ont été pré-chargée dans le formulaire.", null, AlertType.INFO);
                alert.setTimeout(5000);
                this.midlet.display.setCurrent(alert, this.midlet.mainMenu);
            } else {
                config.set("last_report", "false");
                this.midlet.display.setCurrent (this.midlet.mainMenu);
            }
        }
    }
}
