/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pnlp;

import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStoreException;
import pnlp.Configuration.*;

/**
 *
 * @author rgaudin
 */
public class EditNumberForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_SAVE = new Command ("Enreg.", Command.OK, 1);
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
        phone_number = "64614444";
    }

    numberField = new TextField ("Numéro du serveur", phone_number, 8, TextField.NUMERIC);
    append(numberField);
    addCommand(CMD_EXIT);
    addCommand(CMD_SAVE);
    this.setCommandListener (this);
  }

    public void commandAction(Command c, Displayable d) {
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