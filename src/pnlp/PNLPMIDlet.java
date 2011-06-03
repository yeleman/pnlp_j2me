package pnlp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import pnlp.MalariaUnderFiveReport.*;
import pnlp.EditNumberForm.*;
import pnlp.ChangePasswordForm.*;
import pnlp.HelpForm.*;
import pnlp.MalariaUnderFiveForm.*;
import pnlp.MalariaOverFiveForm.*;
import pnlp.MalariaPregnantWomenForm.*;
import pnlp.MalariaStockOutsForm.*;
import pnlp.UpdateOrNewForm.*;
import pnlp.SendReportForm.*;

/*
 * J2ME Midlet allowing user to fill and submit Malaria reports
 * @author rgaudin
 */
public class PNLPMIDlet extends MIDlet implements CommandListener {

    private static final Command CMD_EXIT = new Command ("Quitter", Command.EXIT, 1);
    private static final Command CMD_BACK = new Command ("Quitter", Command.BACK, 1);
    private static final Command CMD_PASSWD = new Command ("Mot de passe", Command.SCREEN, 3);
    private static final Command CMD_SRVNUM = new Command ("Numéro serveur", Command.SCREEN, 4);
    private static final Command CMD_SRVNUM_SAVE = new Command ("Enregistrer", Command.OK, 1);
    private static final Command CMD_HELP = new Command ("Aide", Command.HELP, 2);
    
    public Display display;
    public List mainMenu;
    private Image[] imageArray = null;
    private Configuration config;

    public PNLPMIDlet() {
        display = Display.getDisplay(this);
    }

    public void startApp() {

        config = new Configuration();        

        String[] mainMenu_items = {"U.5", "O.5", "F.E", "S.O", "Envoyer"};
        mainMenu = new List("Rapport mensuel PNLP", Choice.IMPLICIT, mainMenu_items, null);

        // setup menu
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

        // changes label names to proper one + OK if applicable
        refreshMenu();
    }

    /*
     * Rewrites names of Main Menu item based on completion
     */
    public void refreshMenu() {
        // create a report object to access data
        MalariaReport report = new MalariaReport();

        // rewrite menu item names based on validation
        mainMenu.set(0, menuItemText("under_five", report.under_five.dataIsValid()), null);
        mainMenu.set(1, menuItemText("over_five", report.over_five.dataIsValid()), null);
        mainMenu.set(2, menuItemText("pregnant_women", report.pregnant_women.dataIsValid()), null);
        mainMenu.set(3, menuItemText("stock_outs", report.stock_outs.dataIsValid()), null);
    }

    /*
     * Provides a name for menu item fields with completion details
     * Adds [OK] in front of label names if <code>is_ok</code> is <code>true</code>
     * @param slug the slug of the section
     * @param is_ok whether or not to ass [OK] extra text
     * @return a string to be set as menu item label
     */
    private String menuItemText(String slug, boolean is_ok) {
        String name;
        String ok_part = "[OK] ";
        if (slug.equalsIgnoreCase("under_five"))
            name = "Moins de 5 ans";
        else if (slug.equalsIgnoreCase("over_five"))
            name = "5ans et plus";
        else if (slug.equalsIgnoreCase("pregnant_women"))
            name = "Femmes enceintes";
        else if (slug.equalsIgnoreCase("stock_outs"))
            name = "Ruptures de stock";
        else
            name = "";
        
        if (is_ok)
            return ok_part + name;
        else
            return name;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {

        // if it originates from the MainMenu list
        if (s.equals (mainMenu)) {
            // and is a select command
            if (c == List.SELECT_COMMAND) {
                
                    switch (((List) s).getSelectedIndex ()) {

                    // under 5
                    case 0:
                        MalariaUnderFiveForm u5_form = new MalariaUnderFiveForm(this);
                        display.setCurrent (u5_form);
                        break;

                    // over 5
                    case 1:
                        MalariaOverFiveForm o5_form = new MalariaOverFiveForm(this);
                        display.setCurrent (o5_form);
                        break;

                    // pregnant women
                    case 2:
                        MalariaPregnantWomenForm pw_form = new MalariaPregnantWomenForm(this);
                        display.setCurrent (pw_form);
                        break;

                    // stock outs
                    case 3:
                        MalariaStockOutsForm so_form = new MalariaStockOutsForm(this);
                        display.setCurrent (so_form);
                        break;

                    // send form
                    case 4:
                        SendReportForm send_form = new SendReportForm(this);
                        display.setCurrent(send_form);
                        break;
                    }
            }
        }

        // help command displays Help Form.
        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this, this.mainMenu, "mainmenu");
            display.setCurrent(h);
        }

        // srvnum command displays Edit Number Form.
        if (c == CMD_SRVNUM) {
            EditNumberForm f = new EditNumberForm(this);
            display.setCurrent(f);
        }

        // passwd command displays Change Password Form.
        if (c == CMD_PASSWD) {
            ChangePasswordForm f = new ChangePasswordForm(this);
            display.setCurrent(f);
        }

        // exit commands exits application completely.
        if (c == CMD_EXIT || c == CMD_BACK) {
            destroyApp(false);
            notifyDestroyed();
        } 
    }
}