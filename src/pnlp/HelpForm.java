
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.Configuration.*;
import pnlp.Constants.*;

/**
 *
 * @author rgaudin
 */
public class HelpForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Retour", Command.BACK, 1);

    private StringItem helpText;
    PNLPMIDlet midlet;
    Displayable returnTo;

public HelpForm(PNLPMIDlet midlet, Displayable d, String section) {
    super("Aide");
    this.midlet = midlet;
    this.returnTo = d;

    this.getContentFromSection(section);

    append(helpText);
    addCommand(CMD_EXIT);
    this.setCommandListener (this);
  }

private void getContentFromSection(String section) {
    String text;

    if (section.equalsIgnoreCase("passwd")) {
        text = "Renseignez votre identifiant et votre ancien mot de passe dans les champs adéquat.\n" +
               "Ensuite, indiquez le nouveau mot de passe désiré. Celui-ci doit faire au moins 3 caractères.\n" +
               "Vous recevrez un SMS du serveur confirmant ou infirmant le changement de mot de passe.\n\n" +
               "En cas de problème, contactez ANTIM.";
    } else {
        text = "Aucune aide disponible pour cet élément.";
    }
    helpText = new StringItem(null, text);
}

    public void commandAction(Command c, Displayable d) {
        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.returnTo);
        }
    }

}

