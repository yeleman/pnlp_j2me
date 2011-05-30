package pnlp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import pnlp.MalariaUnderFiveReport.*;
import pnlp.EditNumberForm.*;
import pnlp.ChangePasswordForm.*;
import pnlp.HelpForm.*;
import pnlp.MalariaUnderFiveForm.*;
import pnlp.UpdateOrNewForm.*;

public class PNLPMIDlet extends MIDlet implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Quitter", Command.EXIT, 1);
    private static final Command CMD_BACK = new Command ("Quitter", Command.BACK, 1);
    private static final Command CMD_PASSWD = new Command ("Mot de passe", Command.SCREEN, 3);
    private static final Command CMD_SRVNUM = new Command ("Numéro serveur", Command.SCREEN, 4);
    private static final Command CMD_SRVNUM_SAVE = new Command ("Enregistrer", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);
    
    public Display display;
    List mainMenu;
    
    private Image[] imageArray = null;
    private Configuration config;

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
        config = new Configuration();
        
        String[] mainMenu_items = {"Moins de 5ans", "5ans et plus", "Femmes enceintes", "Ruptures de stock", "Envoyer"};
        mainMenu = new List("Rapport mensuel PNLP", Choice.IMPLICIT, mainMenu_items, null);
        mainMenu.setCommandListener (this);
        mainMenu.addCommand (CMD_BACK);
        mainMenu.addCommand (CMD_EXIT);
        mainMenu.addCommand (CMD_HELP);
        mainMenu.addCommand (CMD_PASSWD);
        mainMenu.addCommand (CMD_SRVNUM);

        if (config.get("last_report").equalsIgnoreCase("true")) {
            UpdateOrNewForm update_form = new UpdateOrNewForm(this);
            display.setCurrent(update_form);
        } else {
            display.setCurrent(mainMenu);
        }
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
                    // under 5
                    case 0:
                        MalariaUnderFiveForm form = new MalariaUnderFiveForm(this);
                        display.setCurrent (form);
                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                    }
            }
        }

        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this, this.mainMenu, "mainmenu");
            display.setCurrent(h);
        }

        if (c == CMD_SRVNUM) {
            EditNumberForm f = new EditNumberForm(this);
            display.setCurrent(f);
        }

        if (c == CMD_PASSWD) {
            ChangePasswordForm f = new ChangePasswordForm(this);
            display.setCurrent(f);
        }

        if (c == CMD_EXIT || c == CMD_BACK) {
            destroyApp(false);
            notifyDestroyed();
        } 
    }

}
