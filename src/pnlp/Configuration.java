
package pnlp;

import javax.microedition.rms.*;
import java.io.*;
import pnlp.Constants.*;

/**
 *
 * @author rgaudin
 */
public class Configuration {

    private String username;
    private String server_number;    
    private String last_report;

    private int username_index = 0;
    private int server_number_index = 1;
    private int last_report_index = 2;

    private static final String database = "configuration";
    private RecordStore recordstore = null;

    public Configuration() {
        RecordEnumeration recordEnumeration = null;
        try {
            recordstore = RecordStore.openRecordStore(Configuration.database, true);
            recordEnumeration = recordstore.enumerateRecords(null, null, false);
            recordstore.closeRecordStore();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (recordEnumeration.numRecords() < 3) {
            this.set("username", "", true);
            this.set("server_number", "xxx", true);
            this.set("last_report", "false", true);
            this.set("server_number", Constants.server_number, false);
        }
    }

    public String get(String variable) {

        String value = "";

        int index = this.index_for(variable);
        if (index < 0) {
            return value;
        }

        try
        {
        // open record store
        recordstore = RecordStore.openRecordStore(Configuration.database, true );

        // record is internally a byte array
        byte[] byteInputData = new byte[1024];

        // we'll retrieve data in a stream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteInputData);
        DataInputStream inputDataStream = new DataInputStream(inputStream);

        // actually retrieve data
        recordstore.getRecord(index, byteInputData, 0);

        value = inputDataStream.readUTF();

        // close streams
        inputStream.reset();
        inputStream.close();
        inputDataStream.close();

        // close connection
        recordstore.closeRecordStore();
      }
      catch (Exception error)
      {
          return value;
      }
      return value;
    }

    private int index_for(String variable) {
        int index;
        if (variable.equals("username")) {
            index = username_index;
        } else if (variable.equals("server_number")) {
            index = server_number_index;
        } else if (variable.equals("last_report")) {
            index = last_report_index;
        } else {
            index = -1;
        }
        return index;
    }

    public boolean set(String variable, String value, boolean add) {

        int index = this.index_for(variable);
        if (index < 0) {
            return false;
        }

        try
        {
        // open record store
        recordstore = RecordStore.openRecordStore(Configuration.database, true );

        // record is internaly a byte array
        byte[] outputRecord;

        // store all data in a stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream outputDataStream = new DataOutputStream(outputStream);

        // add all fields to the stream
        outputDataStream.writeUTF(value);

        // finish preparing stream
        outputDataStream.flush();
        outputRecord = outputStream.toByteArray();

        // actual record storage
        if (add) {
            recordstore.addRecord(outputRecord, 0, outputRecord.length);
        } else {
            recordstore.setRecord(index, outputRecord, 0, outputRecord.length);
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
}
