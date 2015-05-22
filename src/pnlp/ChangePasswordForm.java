
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.PNLPMIDlet.*;
import pnlp.Configuration.*;
import pnlp.SMSSender.*;

/**
 * J2ME Form allowing user to change password via SMS
 * Requests username, old password and username
 * then forges and sends an SMS to server.
 * @author rgaudin
 */
public class ChangePasswordForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command (Strings.BACK_LABEL, Command.BACK, 1);
    private static final Command CMD_SEND = new Command (Strings.OK_LABEL, Command.OK, 1);
    private static final Command CMD_HELP = new Command (Strings.HELP_LABEL, Command.HELP, 2);

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

    /*
     *
     * @return <code>true</code> if fields are properly field for sending
     * <code>false</code> otherwise.
     */
    public boolean canSubmit() {
        if (usernameField.getString().length() >= Constants.username_min_length &&
            oldpasswordField.getString().length() >= Constants.password_min_length &&
            newpasswordField.getString().length() >= Constants.password_min_length) {
            return true;
        }
        return false;
    }

    /*
     * formats request of password change as SMS text
     * @return SMS text to be sent to request password change
     */
    public String toSMSFormat() {
        String sep = Constants.KEYWORD;
        String sms_text = Constants.KEY_CHANGE_PASSWD + sep +
                          usernameField.getString() + sep +
                          oldpasswordField.getString() + sep +
                          newpasswordField.getString();
        return sms_text;
    }

    public void commandAction(Command c, Displayable d) {

        // Help command displays Help Form
        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "passwd");
            this.midlet.display.setCurrent(h);
        }

        // Exit command goes back to Main menu.
        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        // Send command builds and send SMS or displays errors.
        if (c == CMD_SEND) {
            Alert alert;

            if (!canSubmit()) {
                alert = new Alert ("Informations incorrectes.", "L'identifiant ou les mots de passe ne sont pas renseignés correctement.", null, AlertType.ERROR);
                this.midlet.display.setCurrent (alert, this);
                return;
            }

            SMSSender sms = new SMSSender();
            String number = config.get("server_number");

            if (sms.send(number, this.toSMSFormat())) {
                alert = new Alert ("Demande envoyée !", "Vous allez recevoir une confirmation du serveur.", null, AlertType.CONFIRMATION);
            } else {
                alert = new Alert ("Échec d'envoi SMS", "Impossible d'envoyer la demande par SMS.", null, AlertType.WARNING);
            }

            // save username
            config.set("username", usernameField.getString());

            this.midlet.display.setCurrent (alert, this);
        }
    }
}
