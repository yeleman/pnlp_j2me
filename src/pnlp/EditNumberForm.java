
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.Configuration.*;
import pnlp.Constants.*;
import pnlp.HelpForm.*;

/**
 *
 * @author rgaudin
 */
public class EditNumberForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SAVE = new Command ("Enreg.", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);
    
    private Configuration config;

    private TextField numberField;
    PNLPMIDlet midlet;

public EditNumberForm(PNLPMIDlet midlet) {
    super("Paramètres transmission");
    this.midlet = midlet;
    
    config = new Configuration();

    String phone_number = "";
    
    phone_number = config.get("server_number");
    if (phone_number.equals("")) {
        phone_number = Constants.server_number;
    }

    numberField = new TextField ("Numéro du serveur", phone_number, 8, TextField.PHONENUMBER);
    append(numberField);
    addCommand(CMD_EXIT);
    addCommand(CMD_SAVE);
    addCommand(CMD_HELP);
    this.setCommandListener (this);
  }

    public void commandAction(Command c, Displayable d) {
         if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "number");
            this.midlet.display.setCurrent(h);
        }

        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        if (c == CMD_SAVE) {
            Alert alert;
            if (config.set("server_number", numberField.getString(), false)) {
                String saved = config.get("server_number");
                alert = new Alert ("Succès !", "Le nouveau numéro a été enregistré: " + saved, null, AlertType.CONFIRMATION);
            } else {
                alert = new Alert ("FAIL!", "fail to save", null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
            }
            this.midlet.display.setCurrent (alert, this);
        }
    }
}
