
package pnlp;

import javax.microedition.rms.*;
import java.io.*;
import java.util.*;


/**
 *s
 * @author rgaudin
 */
public class MalariaUnderFiveReport implements ReportPartInterface {

    private String database = "under_five";
    private RecordStore recordstore = null;
    //private Hashtable _errors;
    private Vector _errors = new Vector();

    public int total_consultation;
    public int total_malaria_cases;
    public int total_simple_malaria_cases;
    public int total_severe_malaria_cases;
    public int total_tested_malaria_cases;
    public int total_confirmed_malaria_cases;
    public int total_acttreated_malaria_cases;
    public int total_inpatient;
    public int total_malaria_inpatient;
    public int total_death;
    public int total_malaria_death;
    public int total_distributed_bednets;

    public MalariaUnderFiveReport() {
        try {
            this.initDB();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    private void initDB() throws RecordStoreException {
        recordstore = RecordStore.openRecordStore(this.database, true );
        RecordEnumeration recordEnumeration = recordstore.enumerateRecords(null, null, false);
         if (recordEnumeration.numRecords() < 1) {
            recordstore.addRecord(null, 0, 0);
        }
        recordstore.closeRecordStore();
        
    }

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

    public boolean saveInStore() {
      
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
        //recordstore.addRecord(outputRecord, 0, outputRecord.length);
        recordstore.setRecord(1, outputRecord, 0, outputRecord.length);

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

    public String toSMSFormat() {
        String sep = " ";
        return total_consultation + sep + total_malaria_cases + sep + 
               total_simple_malaria_cases + sep + total_severe_malaria_cases +
               sep + total_tested_malaria_cases + sep +
               total_confirmed_malaria_cases + sep +
               total_acttreated_malaria_cases + sep + total_inpatient + sep +
               total_malaria_inpatient + sep + total_death + sep +
               total_malaria_death + sep + total_distributed_bednets;
    }

    public boolean dataIsValid() {
        if (total_malaria_cases > total_consultation) {
            _errors.addElement("Cas de Palu superieur au total");
        }
        
        if (_errors.size() == 0) {
            return true;
        }
        return false;
    }

    public String[] errors() {
        String[] all_errors = new String[_errors.size()];
        int i = 0;
        for(Enumeration en = _errors.elements(); en.hasMoreElements();) {
            all_errors[i] = (String) en.nextElement();
            i++;
        }
        return all_errors;
    }

    public String errorMessage() {
        return (String) _errors.firstElement();
    }

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
        this.total_simple_malaria_cases = total_malaria_cases;
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

}
