
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.Configuration.*;
import pnlp.Constants.*;

/**
 * J2ME Form displaying a long help text
 * Instanciated with a section paramater
 * which triggers appropriate text.
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

/*
 * 
 */
private void getContentFromSection(String section) {
    String text;

    if (section.equalsIgnoreCase("passwd")) {
        text = "Renseignez votre identifiant et votre ancien mot de passe dans les champs adéquat.\n" +
               "Ensuite, indiquez le nouveau mot de passe désiré. Celui-ci doit faire au moins 3 caractères.\n" +
               "Vous recevrez un SMS du serveur confirmant ou infirmant le changement de mot de passe.\n\n" +
               "En cas de problème, contactez ANTIM.";
    } else if (section.equalsIgnoreCase("mainmenu")) {
        text = "Chaque élément de la liste correspond à une section du formulaire.\n" +
               "Entrez dans chaque et renseignez les champs en copiant le papier.\n" +
               "Dès qu'une section est complète et sans erreur, son nom contient [OK].\n\n" +
               "En cas de problème, contactez ANTIM.";
    } else if (section.equalsIgnoreCase("under_five")) {
        text = "Renseignez uniquement la zone correspondant aux enfants de moins de 5ans.\n" +
               "Elle correspond à la première colonne du formulaire papier.\n\n" +
               "En cas de problème, contactez votre District de référence.";
    } else if (section.equalsIgnoreCase("over_five")) {
        text = "Renseignez uniquement la zone correspondant aux enfants de 5ans et plus.\n" +
               "Elle correspond à la deuxième colonne du formulaire papier.\n\n" +
               "En cas de problème, contactez votre District de référence.";
    } else if (section.equalsIgnoreCase("pregnant_women")) {
        text = "Renseignez uniquement la zone correspondant aux femmes enceintes.\n" +
               "Elle correspond à la troisieme colonne du formulaire papier " +
               "ainsi qu'au bloc CPN/SP en fin de formulaire.\n\n" +
               "En cas de problème, contactez votre District de référence.";
    } else if (section.equalsIgnoreCase("stock_outs")) {
        text = "Renseignez uniquement la zone correspondant aux ruptures de stock.\n" +
               "Elle correspond à la partie droite du formulaire papier.\n\n" +
               "En cas de problème, contactez votre District de référence.";
    } else if (section.equalsIgnoreCase("edit_number")) {
        text = "Changez le numéro du serveur uniquement sur demande expresse " +
               "du PNLP ou de l'ANTIM.\n" +
               "Un mauvais numéro vous empechera de transmettre vos rapports.\n\n" +
               "En cas de problème, contactez ANTIM.";
    } else if (section.equalsIgnoreCase("update_or_new")) {
        text = "Choisissez de reprendre un rapport si vous souhaitez que les " +
               "dernières données entrées dans le logiciel soient pré-remplies.\n" +
               "Cette possibilité vous permet de renvoyer facilement un " + 
               "rapport dont l'envoi a échoué.\n" +
               "Cela vous permet aussi de continuer un rapport non terminé.\n" +
               "Si votre rapport a été correctement envoyé (confirmation du " +
               "serveur), créez un nouveau." +
               "En cas de problème, contactez ANTIM.";
    } else if (section.equalsIgnoreCase("send_report")) {
        text = "Renseignez votre identifiant, mot de passe et la période du " +
               "rapport.\n" +
               "Une fois le rapport envoyé, vous recevrez un SMS de confirmation " +
               "contenant un numéro de recu.\n" +
               "Si vous ne recevez pas ce numéro rapidement, réessayer l'envoi.\n\n" +
               "En cas de problème, contactez ANTIM.";
    } else if (section.equalsIgnoreCase("version")) {
        text = "PNLP - Version " + Constants.version + "\n\n" +
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

