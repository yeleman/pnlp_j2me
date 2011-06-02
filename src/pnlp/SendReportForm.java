
package pnlp;
import javax.microedition.lcdui.*;
import pnlp.PNLPMIDlet.*;
import pnlp.Configuration.*;

/**
 * Request per-report meta data and fire SMS
 * @author rgaudin
 */
public class SendReportForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SEND = new Command ("Envoyer", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);
    private static final int MAX_SIZE = 8; // max no. of chars per field.

    private PNLPMIDlet midlet;

    private static final String[] monthList= {" --- ", "Janvier (01)", "Février (02)", "Mars (03)", "Avril (04)", "Mai (05)", "Juin (06)", "Juillet (07)", "Aout (08)", "Septembre (09)", "Octobre (10)", "Novembre (11)", "Décembre (12)"};
    private static final String[] yearList = {" --- ", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"};

    private static final StringItem intro = new StringItem(null, "Indiquez la période concernée et donnez vos identifiants pour envoyer le rapport.");
    private ChoiceGroup month;
    private ChoiceGroup year;
    private TextField username;
    private TextField password;

    private Configuration config;

    public SendReportForm(PNLPMIDlet midlet) {
        super("Envoi du rapport mensuel");
        this.midlet = midlet;

        config = new Configuration();

        month = new ChoiceGroup("Mois:", ChoiceGroup.POPUP, monthList, null);
        year = new ChoiceGroup("Année:", ChoiceGroup.POPUP, yearList, null);
        username = new TextField("Identifiant", null, MAX_SIZE, TextField.NON_PREDICTIVE);
        password = new TextField("Mot de passe", null, MAX_SIZE, TextField.SENSITIVE);

        append(intro);
        append(month);
        append(year);
        append(username);
        append(password);

        addCommand(CMD_SEND);
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

        // exit command goes back to main Menu.
        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        // send command sends the SMS or displays errors.
        if (c == CMD_SEND) {
            /*if (selection.getSelectedIndex() == 1) {
                Alert alert = new Alert("Poursuite de rapport", "Les dernières données entrées ont été pré-chargée dans le formulaire.", null, AlertType.INFO);
                alert.setTimeout(5000);
                this.midlet.display.setCurrent(alert, this.midlet.mainMenu);
            } else {
                config.set("last_report", "false", false);
                this.midlet.display.setCurrent (this.midlet.mainMenu);
            }*/
        }
    }
}
