
package pnlp;
import javax.microedition.lcdui.*;
import pnlp.PNLPMIDlet.*;
import pnlp.Configuration.*;
import pnlp.SMSSender.*;

/**
 *
 * @author rgaudin
 */
public class ChangePasswordForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SEND = new Command ("Enreg.", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);

    private PNLPMIDlet midlet;

    private TextField usernameField;
    private TextField oldpasswordField;
    private TextField newpasswordField;

    private Configuration config;

    public ChangePasswordForm(PNLPMIDlet midlet) {
        super("Changement de mot de passe");
        this.midlet = midlet;

        config = new Configuration();

        String username = "";

        username = config.get("username");

        usernameField = new TextField ("Identifiant", username, 8, TextField.ANY);
        oldpasswordField = new TextField ("Ancien mot de passe", "", 8, TextField.ANY);
        newpasswordField = new TextField ("Nouveau mot de passe", "", 8, TextField.ANY);

        append(usernameField);
        append(oldpasswordField);
        append(newpasswordField);

        addCommand(CMD_EXIT);
        addCommand(CMD_SEND);
        addCommand(CMD_HELP);
        this.setCommandListener (this);

    }

    public boolean canSubmit() {
        if (usernameField.getString().length() >= 8) {
            return true;
        }
        return false;
    }

    public String toSMSFormat() {
        String sep = " ";
        String sms_text = "palu passwd" + sep + usernameField.getString() + sep + oldpasswordField.getString() + sep + newpasswordField.getString();
        return sms_text;
    }

    public void commandAction(Command c, Displayable d) {

        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "passwd");
            this.midlet.display.setCurrent(h);
        }

        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        if (c == CMD_SEND) {
            Alert alert;
            SMSSender sms = new SMSSender();
            String number = config.get("server_number");
            if (sms.send(number, this.toSMSFormat())) {
             alert = new Alert ("Demande envoyée !", "Vous allez recevoir une confirmation du serveur.", null, AlertType.CONFIRMATION);
            } else {
                alert = new Alert ("Echec d'envoi SMS", "Impossible d'envoyer la demande par SMS.", null, AlertType.WARNING);
            }
            this.midlet.display.setCurrent (alert, this);
        }
    }


}
