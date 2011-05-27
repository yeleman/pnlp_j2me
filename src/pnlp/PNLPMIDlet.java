package pnlp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import pnlp.MalariaUnderFiveReport.*;
import pnlp.EditNumberForm.*;

public class PNLPMIDlet extends MIDlet implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Quitter", Command.EXIT, 1);
    private static final Command CMD_BACK = new Command ("Retour", Command.BACK, 1);
    private static final Command CMD_PASSWD = new Command ("Mot de passe", Command.SCREEN, 2);
    private static final Command CMD_SRVNUM = new Command ("Numéro serveur", Command.SCREEN, 3);
    private static final Command CMD_SRVNUM_SAVE = new Command ("Enregistrer", Command.OK, 1);
    
    public Display display;
    List mainMenu;
    
    private Image[] imageArray = null;

    public PNLPMIDlet() {
        display = Display.getDisplay(this);

    }

    public void startApp() {

        // store some arbitrary data in the RS
        //MalariaUnderFiveReport under_five = new MalariaUnderFiveReport();
        //under_five.setAll(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048);
        //under_five.saveInStore();

        // load those same data
        //MalariaUnderFiveReport under_five_a = new MalariaUnderFiveReport();
        //under_five_a.loadFromStore();

        // display loaded data
        //TextBox t = new TextBox("PNLP Data", under_five_a.toSMSFormat(), 256, 0);

        //t.addCommand(exitCommand);
        //st.setCommandListener(this);
        String[] mainMenu_items = {"Moins de 5ans", "5ans et plus", "Femmes enceintes", "Ruptures de stock", "Envoyer"};

        mainMenu = new List("Rapport mensuel PNLP", Choice.IMPLICIT, mainMenu_items, null);
        mainMenu.addCommand (CMD_BACK);
        mainMenu.addCommand (CMD_EXIT);
        mainMenu.addCommand (CMD_PASSWD);
        mainMenu.addCommand (CMD_SRVNUM);
        mainMenu.setCommandListener (this);

        display.setCurrent(mainMenu);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        if (s.equals (mainMenu)) {
            // in the main list
            if (c == List.SELECT_COMMAND) {
                
                    switch (((List) s).getSelectedIndex ()) {
                    case 0:
                        Alert alert = new Alert ("Ooch", "We got a problem here", null, AlertType.WARNING);
                        alert.setTimeout(Alert.FOREVER);
                        display.setCurrent (alert, mainMenu);
                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                    }
            }
        }

        if (c == CMD_SRVNUM) {
            EditNumberForm e = new EditNumberForm(this);
            display.setCurrent(e);
        }

        if (c == CMD_EXIT) {
            destroyApp(false);
            notifyDestroyed();
        } 
    }

}
