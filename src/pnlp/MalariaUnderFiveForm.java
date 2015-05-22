
package pnlp;

import javax.microedition.lcdui.*;
import pnlp.Configuration.*;
import pnlp.Constants.*;
import pnlp.HelpForm.*;
import pnlp.MalariaUnderFiveReport.*;

/**
 * J2ME Under 5 Form
 * Displays all fields required for under 5yo section
 * Checks that those are all filled up
 * Checks for data errors
 * Saves into the DB.
 * @author rgaudin
 */
public class MalariaUnderFiveForm extends Form implements CommandListener {

    private static final Command CMD_EXIT = new Command (Strings.BACK_LABEL, Command.BACK, 1);
    private static final Command CMD_SAVE = new Command (Strings.SAVE_LABEL, Command.OK, 1);
    private static final Command CMD_HELP = new Command (Strings.HELP_LABEL, Command.HELP, 2);
    private static final int MAX_SIZE = 5; // max no. of chars per field.

    public PNLPMIDlet midlet;

    private Configuration config;

    private TextField total_consultation;
    private TextField total_malaria_cases;
    private TextField total_simple_malaria_cases;
    private TextField total_severe_malaria_cases;
    private TextField total_tested_malaria_cases;
    private TextField total_confirmed_malaria_cases;
    private TextField total_acttreated_malaria_cases;
    private TextField total_inpatient;
    private TextField total_malaria_inpatient;
    private TextField total_death;
    private TextField total_malaria_death;
    private TextField total_distributed_bednets;

public MalariaUnderFiveForm(PNLPMIDlet midlet) {
    super("Moins de 5 ans.");
    this.midlet = midlet;

    config = new Configuration();

    // creating al fields (blank)
    total_consultation = new TextField(Strings.total_consultation_label, null, MAX_SIZE, TextField.NUMERIC);
    total_malaria_cases = new TextField(Strings.total_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_simple_malaria_cases = new TextField(Strings.total_simple_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_severe_malaria_cases = new TextField(Strings.total_severe_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_tested_malaria_cases = new TextField(Strings.total_tested_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_confirmed_malaria_cases = new TextField(Strings.total_confirmed_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_acttreated_malaria_cases = new TextField(Strings.total_acttreated_malaria_cases_label, null, MAX_SIZE, TextField.NUMERIC);
    total_inpatient = new TextField(Strings.total_inpatient_label , null, MAX_SIZE, TextField.NUMERIC);
    total_malaria_inpatient = new TextField(Strings.total_malaria_inpatient_label, null, MAX_SIZE, TextField.NUMERIC);
    total_death = new TextField(Strings.total_death_label, null, MAX_SIZE, TextField.NUMERIC);
    total_malaria_death = new TextField(Strings.total_malaria_death_label, null, MAX_SIZE, TextField.NUMERIC);
    total_distributed_bednets = new TextField(Strings.total_distributed_bednets_label, null, MAX_SIZE, TextField.NUMERIC);

    // if user requested to continue an existing report
    if (config.get("has_data").equalsIgnoreCase("true")) {
        // create an report object from store
        MalariaUnderFiveReport report = new MalariaUnderFiveReport();
        report.loadFromStore();

        // assign stored value to each fields.
        total_consultation.setString(valueForField(report.total_consultation));
        total_malaria_cases.setString(valueForField(report.total_malaria_cases));
        total_simple_malaria_cases.setString(valueForField(report.total_simple_malaria_cases));
        total_severe_malaria_cases.setString(valueForField(report.total_severe_malaria_cases));
        total_tested_malaria_cases.setString(valueForField(report.total_tested_malaria_cases));
        total_confirmed_malaria_cases.setString(valueForField(report.total_confirmed_malaria_cases));
        total_acttreated_malaria_cases.setString(valueForField(report.total_acttreated_malaria_cases));
        total_inpatient.setString(valueForField(report.total_inpatient));
        total_malaria_inpatient.setString(valueForField(report.total_malaria_inpatient));
        total_death.setString(valueForField(report.total_death));
        total_malaria_death.setString(valueForField(report.total_malaria_death));
        total_distributed_bednets.setString(valueForField(report.total_distributed_bednets));
    }

    // add fields to forms
    append(total_consultation);
    append(total_malaria_cases);
    append(total_tested_malaria_cases);
    append(total_confirmed_malaria_cases);
    append(total_simple_malaria_cases);
    append(total_severe_malaria_cases);
    append(total_acttreated_malaria_cases);
    append(total_inpatient);
    append(total_malaria_inpatient);
    append(total_death);
    append(total_malaria_death);
    append(total_distributed_bednets);

    addCommand(CMD_EXIT);
    addCommand(CMD_SAVE);
    addCommand(CMD_HELP);
    this.setCommandListener (this);
}

    /*
     * converts internal <code>int</code> data to <code>String</code> for field
     * @param value the number to display on field
     * @return the <code>String</code> to attach to the field.
     */
    private String valueForField(int value) {
        if (value == -1) {
            return "";
        }
        return String.valueOf(value);
    }

    /*
     * Whether all required fields are filled
     * @return <code>true</code> is all fields are filled
     * <code>false</code> otherwise.
     */
    public boolean isComplete() {
        // all fields are required to be filled.
        if (total_consultation.getString().length() == 0 ||
            total_malaria_cases.getString().length() == 0 ||
            total_simple_malaria_cases.getString().length() == 0 ||
            total_severe_malaria_cases.getString().length() == 0 ||
            total_tested_malaria_cases.getString().length() == 0 ||
            total_confirmed_malaria_cases.getString().length() == 0 ||
            total_acttreated_malaria_cases.getString().length() == 0 ||
            total_inpatient.getString().length() == 0 ||
            total_malaria_inpatient.getString().length() == 0 ||
            total_death.getString().length() == 0 ||
            total_malaria_death.getString().length() == 0 ||
            total_distributed_bednets.getString().length() == 0) {
            return false;
        }
        return true;
    }

    public void commandAction(Command c, Displayable d) {
        // help command displays Help Form.
        if (c == CMD_HELP) {
            HelpForm h = new HelpForm(this.midlet, this, "under_five");
            this.midlet.display.setCurrent(h);
        }

        // exit commands comes back to main menu.
        if (c == CMD_EXIT) {
            this.midlet.display.setCurrent(this.midlet.mainMenu);
        }

        // save command
        if (c == CMD_SAVE) {

            Alert alert;

            // check whether all fields have been completed
            // if not, we alert and don't do anything else.
            if (!this.isComplete()) {
                alert = new Alert("Données manquantes", "Tous les champs doivent être remplis!", null, AlertType.ERROR);
                alert.setTimeout(Alert.FOREVER);
                this.midlet.display.setCurrent (alert, this);
                return;
            }

            // create a report object from values
            MalariaUnderFiveReport under_five = new MalariaUnderFiveReport();
            under_five.setAll(Integer.parseInt(total_consultation.getString()), Integer.parseInt(total_malaria_cases.getString()), Integer.parseInt(total_simple_malaria_cases.getString()), Integer.parseInt(total_severe_malaria_cases.getString()), Integer.parseInt(total_tested_malaria_cases.getString()), Integer.parseInt(total_confirmed_malaria_cases.getString()), Integer.parseInt(total_acttreated_malaria_cases.getString()), Integer.parseInt(total_inpatient.getString()), Integer.parseInt(total_malaria_inpatient.getString()), Integer.parseInt(total_death.getString()), Integer.parseInt(total_malaria_death.getString()), Integer.parseInt(total_distributed_bednets.getString()));
            // check for errors and display first error
            if (!under_five.dataIsValid()) {
                alert = new Alert("Données incorrectes!", under_five.errorMessage(), null, AlertType.ERROR);
                alert.setTimeout(Alert.FOREVER);
                this.midlet.display.setCurrent (alert, this);
                return;
            }

            // data appears to be valid now. Let's save it.
            under_five.saveInStore();
            // refresh menu as we've changed data.
            this.midlet.refreshMenu();

            // mark report in progress
            config.set("has_data", "true");

            // Confirm data is OK and go to main menu
            alert = new Alert("Enregistré", "Les données des moins de 5ans ont été enregistrées", null, AlertType.CONFIRMATION);
            alert.setTimeout(Alert.FOREVER);
            this.midlet.display.setCurrent (alert, this.midlet.mainMenu);
        }
    }
}
