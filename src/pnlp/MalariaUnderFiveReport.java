
package pnlp;

import javax.microedition.rms.*;
import java.io.*;
import java.util.*;

/**
 * Stores data associated with Under5 report.
 * Includes validation logic and internal storage/reload.
 * Data is stored in RMS in a dedicated DB of exaclty 1 row.
 * All data is public but use of <code>setAll</code> method
 * is encouraged.
 * @author rgaudin
 */
public class MalariaUnderFiveReport implements ReportPartInterface {

    private String database = "under_five";
    private RecordStore recordstore = null;
    private Vector _errors = new Vector();

    public int total_consultation = -1;
    public int total_malaria_cases = -1;
    public int total_simple_malaria_cases = -1;
    public int total_severe_malaria_cases = -1;
    public int total_tested_malaria_cases = -1;
    public int total_confirmed_malaria_cases = -1;
    public int total_acttreated_malaria_cases = -1;
    public int total_inpatient = -1;
    public int total_malaria_inpatient = -1;
    public int total_death = -1;
    public int total_malaria_death = -1;
    public int total_distributed_bednets = -1;

    public MalariaUnderFiveReport() {
        try {
            this.initDB();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * initialize the DB
     * creates an row in the DB if it doesn't exist.
     * @throws <code>RecordStoreException</code> RMS can't open DB.
     */
    private void initDB() throws RecordStoreException {
        recordstore = RecordStore.openRecordStore(this.database, true );
        RecordEnumeration recordEnumeration = recordstore.enumerateRecords(null, null, false);
        recordstore.closeRecordStore();
         if (recordEnumeration.numRecords() < 1) {
             this.saveInStore(true);
        }
    }

    /*
     * fills object with value from DB.
     * @return <code>true</true> if load was successful
     * <code>false</code> otherwise.
     */
    public boolean loadFromStore() {

      try
      {
        // open record store
        recordstore = RecordStore.openRecordStore(this.database, true );

        // record is internally a byte array
        byte[] byteInputData = new byte[1024];

        // we'll retrieve data in a stream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteInputData);
        DataInputStream inputDataStream = new DataInputStream(inputStream);
        
        // actually retrieve data
        recordstore.getRecord(1, byteInputData, 0);

        total_consultation = inputDataStream.readInt();
        total_malaria_cases = inputDataStream.readInt();
        total_simple_malaria_cases = inputDataStream.readInt();
        total_severe_malaria_cases = inputDataStream.readInt();
        total_tested_malaria_cases = inputDataStream.readInt();
        total_confirmed_malaria_cases = inputDataStream.readInt();
        total_acttreated_malaria_cases = inputDataStream.readInt();
        total_inpatient = inputDataStream.readInt();
        total_malaria_inpatient = inputDataStream.readInt();
        total_death = inputDataStream.readInt();
        total_malaria_death = inputDataStream.readInt();
        total_distributed_bednets = inputDataStream.readInt();

        // close stream
        inputStream.reset();
        inputStream.close();
        inputDataStream.close();

        // close connection
        recordstore.closeRecordStore();
      }
      catch ( Exception error)
      {
          return false;
      }
      return true;
    }

    /*
     * saves content into DB.
     * @param add whether to add a record or not (update)
     * @return <code>true</true> if save was successful
     * <code>false</code> otherwise.
     */
    public boolean saveInStore(boolean add) {
      
      try
      {
        // open record store
        recordstore = RecordStore.openRecordStore(this.database, true );

        // record is internaly a byte array
        byte[] outputRecord;

        // store all data in a stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream outputDataStream = new DataOutputStream(outputStream);

        // add all fields to the stream
        outputDataStream.writeInt(total_consultation);
        outputDataStream.writeInt(total_malaria_cases);
        outputDataStream.writeInt(total_simple_malaria_cases);
        outputDataStream.writeInt(total_severe_malaria_cases);
        outputDataStream.writeInt(total_tested_malaria_cases);
        outputDataStream.writeInt(total_confirmed_malaria_cases);
        outputDataStream.writeInt(total_acttreated_malaria_cases);
        outputDataStream.writeInt(total_inpatient);
        outputDataStream.writeInt(total_malaria_inpatient);
        outputDataStream.writeInt(total_death);
        outputDataStream.writeInt(total_malaria_death);
        outputDataStream.writeInt(total_distributed_bednets);

        // finish preparing stream
        outputDataStream.flush();
        outputRecord = outputStream.toByteArray();

        // actual record storage
        if (add) {
            recordstore.addRecord(outputRecord, 0, outputRecord.length);
        } else {
            recordstore.setRecord(1, outputRecord, 0, outputRecord.length);
        }

        // close stream
        outputStream.reset();
        outputStream.close();
        outputDataStream.close();

        // close connection
        recordstore.closeRecordStore();
      }
      catch ( Exception error)
      {
          return false;
      }
      return true;
    }

    /*
     * Overrides <code>saveInStore()</code> without parameter
     * Almost every call will use this override. Uses <code>false</code>
     * @return the value of <code>saveInStore(false)</code>
     */
    public boolean saveInStore() {
        return this.saveInStore(false);
    }

    /*
     * generates a string to be used in SMS
     * @return long <code>String</true> describing all data
     * formatted for SMS sending.
     */
    public String toSMSFormat() {
        String sep = Constants.SPACER;
        return total_consultation + sep +
               total_malaria_cases + sep +
               total_simple_malaria_cases + sep +
               total_severe_malaria_cases + sep +
               total_tested_malaria_cases + sep +
               total_confirmed_malaria_cases + sep +
               total_acttreated_malaria_cases + sep +
               total_inpatient + sep +
               total_malaria_inpatient + sep +
               total_death + sep +
               total_malaria_death + sep +
               total_distributed_bednets;
    }

    /*
     * checks whether data is legit
     * checks all logic algorithms and fills the <code>_errors</code> variable.
     * @return <code>true</true> if all data is valid
     * <code>false</code> otherwise.
     */
    public boolean dataIsValid() {

        _errors = new Vector();

        if (!(dataIsComplete())) {
            _errors.addElement("Tous les champs ne sont pas remplis");
        }

        // various tests checking whether provided number are legit.
        if (total_malaria_cases > total_consultation) {
            _errors.addElement("Cas de Palu supérieur au total toutes causes");
        }

        if (total_simple_malaria_cases > total_consultation) {
            _errors.addElement("Cas de Palu simple supérieur au total toutes causes");
        }

        if (total_severe_malaria_cases > total_consultation) {
            _errors.addElement("Cas de Palu grave supérieur au total toutes causes");
        }

        if (total_simple_malaria_cases > total_malaria_cases) {
            _errors.addElement("Cas de Palu simple supérieur au total suspectés");
        }

        if (total_severe_malaria_cases > total_malaria_cases) {
            _errors.addElement("Cas de Palu grave supérieur au total suspectés");
        }

        if (total_tested_malaria_cases > total_malaria_cases) {
            _errors.addElement("Cas de Palu testés supérieur au total suspectés");
        }

        if (total_confirmed_malaria_cases > total_malaria_cases) {
            _errors.addElement("Cas de Palu confirmés supérieur au total suspectés");
        }

        if ((total_simple_malaria_cases + total_severe_malaria_cases) > total_malaria_cases) {
            _errors.addElement("Cas de Palu simple + grave supérieurs au total suspectés");
        }

        if (total_confirmed_malaria_cases > total_tested_malaria_cases) {
            _errors.addElement("Cas de Palu confirmés supérieur au total testés");
        }

        if ((total_simple_malaria_cases + total_severe_malaria_cases) != total_confirmed_malaria_cases) {
            _errors.addElement("Cas de Palu simple + grave supérieurs au total confirmés");
        }

        if (total_acttreated_malaria_cases > total_tested_malaria_cases) {
            _errors.addElement("Cas de Palu traités supérieur au total testés");
        }

        if (total_acttreated_malaria_cases > total_confirmed_malaria_cases) {
            _errors.addElement("Cas de Palu traités supérieur au total confirmés");
        }

        if (total_malaria_inpatient > total_inpatient) {
            _errors.addElement("Hospitalisations Palu supérieur aux hospit. toutes causes");
        }

        if (total_malaria_death > total_death) {
            _errors.addElement("Décès Palu supérieur aux décès toutes causes");
        }
        
        if (_errors.size() == 0) {
            return true;
        }
        return false;
    }

    /*
     * List all errors on data
     * List all errors detected by <code>isValidData</code>.
     * @return Array of <code>String</true> containing error messages
     * If <code>isValidData</code> has never been called, array is empty
     */
    public String[] errors() {
        String[] all_errors = new String[_errors.size()];
        int i = 0;
        for(Enumeration en = _errors.elements(); en.hasMoreElements();) {
            all_errors[i] = (String) en.nextElement();
            i++;
        }
        return all_errors;
    }

    /*
     * Current error message
     * @return <code>String</true> containing the current error message
     * It's the first one in the list.
     */
    public String errorMessage() {
        return (String) _errors.firstElement();
    }

    /*
     * set all variables at once.
     * @param total_consultation number of consultations all causes
     * @param total_malaria_cases number of suspected malaria cases
     * @param total_simple_malaria_cases number of simple malaria cases
     * @praram total_severe_malaria_cases number of severe malaria cases
     * @param total_tested_malaria_cases number of tested malaria cases
     * @param total_confirmed_malaria_cases number of confirmed malaria cases
     * @param total_acttreated_malaria_cases number of ACT treated malaria cases
     * @param total_inpatient number of inpatient all causes
     * @param total_malaria_impatient number of Malaria related inpatient
     * @param total_death number of death all causes
     * @param total_malaria_death number of Malaria related death
     * @param total_distributed_bednets number of bednets distributed
     */
    public void setAll(int total_consultation,
                  int total_malaria_cases,
                  int total_simple_malaria_cases,
                  int total_severe_malaria_cases,
                  int total_tested_malaria_cases,
                  int total_confirmed_malaria_cases,
                  int total_acttreated_malaria_cases,
                  int total_inpatient,
                  int total_malaria_impatient,
                  int total_death,
                  int total_malaria_death,
                  int total_distributed_bednets) {
        this.total_consultation = total_consultation;
        this.total_malaria_cases = total_malaria_cases;
        this.total_simple_malaria_cases = total_simple_malaria_cases;
        this.total_severe_malaria_cases = total_severe_malaria_cases;
        this.total_tested_malaria_cases = total_tested_malaria_cases;
        this.total_confirmed_malaria_cases = total_confirmed_malaria_cases;
        this.total_acttreated_malaria_cases = total_acttreated_malaria_cases;
        this.total_inpatient = total_inpatient;
        this.total_malaria_inpatient = total_malaria_impatient;
        this.total_death = total_death;
        this.total_malaria_death = total_malaria_death;
        this.total_distributed_bednets = total_distributed_bednets;
    }

    public boolean dataIsComplete() {
        if (this.total_consultation != -1 &&
            this.total_malaria_cases != -1 &&
            this.total_simple_malaria_cases != -1 &&
            this.total_severe_malaria_cases != -1 &&
            this.total_tested_malaria_cases != -1 &&
            this.total_confirmed_malaria_cases != -1 &&
            this.total_acttreated_malaria_cases != -1 &&
            this.total_inpatient != -1 &&
            this.total_malaria_inpatient != -1 &&
            this.total_death != -1 &&
            this.total_malaria_death != -1 &&
            this.total_distributed_bednets != -1) {
                return true;
        }
        return false;
    }

    public boolean delete() {
        try {
            RecordStore.deleteRecordStore(this.database);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
