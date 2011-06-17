
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.Configuration.*;
import pnlp.Configuration.*;

/**
 * J2ME Form displaying an intro text and submit button
 * which sends an SMS
 * @author rgaudin
 */
public class RequestHelpForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SEND = new Command ("Envoyer", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);

    private StringItem helpText;
    private Configuration config;
    PNLPMIDlet midlet;
    Displayable returnTo;

public RequestHelpForm(PNLPMIDlet midlet) {
    super("Demande d'assistance");
    this.midlet = midlet;

    config = new Configuration();

    helpText = new StringItem(null, "En cas de problème relatif à " +
                                    "l'utilisation de l'application, " +
                                    "choisissez ENVOYER pour demander " +
                                    "de l'aide.\n\n" +
                                    "Un technicien de l'ANTIM vous rappellera " +
                                    "rapidement.");

    append(helpText);
    addCommand(CMD_EXIT);
    addCommand(CMD_HELP);
    addCommand(CMD_SEND);
    this.setCommandListener (this);
  }

    public void commandAction(Command c, Displayable d) {
         // Help command displays Help Form
         if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "request_help");
            this.midlet.display.setCurrent(h);
        }

        // exit command goes back to Main Menu
        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        // sends the sms and reply feedback
        if (c == CMD_SEND) {
            Alert alert;
            SMSSender sms = new SMSSender();
            String number = config.get("server_number");
            if (sms.send(number, this.toSMSFormat())) {
                alert = new Alert ("Demande envoyée !", "Merci d'attendre l'appel d'un technicien.", null, AlertType.CONFIRMATION);
                this.midlet.display.setCurrent (alert, this.midlet.mainMenu);
            } else {
                alert = new Alert ("Échec d'envoi SMS", "Impossible d'envoyer la demande par SMS.", null, AlertType.WARNING);
                this.midlet.display.setCurrent (alert, this);
            }
        }
    }

    private String toSMSFormat() {
        return "palu aide u:" + config.get("username");
    }
}
