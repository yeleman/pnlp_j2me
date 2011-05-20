
package pnlp;

import javax.microedition.rms.*;
import java.io.*;

/**
 *
 * @author rgaudin
 */
public class MalariaUnderFiveReport implements ReportPartInterface {

    private String database = "under_five";
    private RecordStore recordstore = null;

    private int total_consultation;
    private int total_malaria_cases;
    private int total_simple_malaria_cases;
    private int total_severe_malaria_cases;
    private int total_tested_malaria_cases;
    private int total_confirmed_malaria_cases;
    private int total_acttreated_malaria_cases;
    private int total_inpatient;
    private int total_malaria_impatient;
    private int total_death;
    private int total_malaria_death;
    private int total_distributed_bednets;

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
        total_malaria_impatient = inputDataStream.readInt();
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
        /*alert = new Alert("Error Writing",
                 error.toString(), null, AlertType.WARNING);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);*/
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
	outputDataStream.writeInt(total_malaria_impatient);
	outputDataStream.writeInt(total_death);
	outputDataStream.writeInt(total_malaria_death);
	outputDataStream.writeInt(total_distributed_bednets);

        // finish preparing stream
        outputDataStream.flush();
        outputRecord = outputStream.toByteArray();

        // actual record storage
        recordstore.addRecord(outputRecord, 0, outputRecord.length);

        // close stream
        outputStream.reset();
        outputStream.close();
        outputDataStream.close();

        // close connection
        recordstore.closeRecordStore();
      }
      catch ( Exception error)
      {
        /*alert = new Alert("Error Writing",
                 error.toString(), null, AlertType.WARNING);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);*/
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
               total_malaria_impatient + sep + total_death + sep +
               total_malaria_death + sep + total_distributed_bednets;
    }

    public boolean dataIsValid() {
        return true;
    }

    public String[] errors() {
        String[] _errors = new String[20];
        return _errors;
    }

    public String errorMessage() {
        return "OK";
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
        this.total_malaria_impatient = total_malaria_impatient;
        this.total_death = total_death;
        this.total_malaria_death = total_malaria_death;
        this.total_distributed_bednets = total_distributed_bednets;
    }

}
