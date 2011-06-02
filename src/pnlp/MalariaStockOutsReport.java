
package pnlp;

import javax.microedition.rms.*;
import java.io.*;
import java.util.*;

/**
 * Stores data associated with Stock Outs report.
 * Includes validation logic and internal storage/reload.
 * Data is stored in RMS in a dedicated DB of exaclty 1 row.
 * All data is public but use of <code>setAll</code> method
 * is encouraged.
 * @author rgaudin
 */
public class MalariaStockOutsReport implements ReportPartInterface {

    private String database = "stock_outs";
    private RecordStore recordstore = null;
    private Vector _errors = new Vector();

    public boolean stockout_act_children;
    public boolean stockout_act_youth;
    public boolean stockout_act_adult;
    public boolean stockout_arthemeter;
    public boolean stockout_quinine;
    public boolean stockout_serum;
    public boolean stockout_bednet;
    public boolean stockout_rdt;
    public boolean stockout_sp;

    public MalariaStockOutsReport() {
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
         if (recordEnumeration.numRecords() < 1) {
            recordstore.addRecord(null, 0, 0);
        }
        recordstore.closeRecordStore();
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

        stockout_act_children = inputDataStream.readBoolean();
        stockout_act_youth = inputDataStream.readBoolean();
        stockout_act_adult = inputDataStream.readBoolean();
        stockout_arthemeter = inputDataStream.readBoolean();
        stockout_quinine = inputDataStream.readBoolean();
        stockout_serum = inputDataStream.readBoolean();
        stockout_bednet = inputDataStream.readBoolean();
        stockout_rdt = inputDataStream.readBoolean();
        stockout_sp = inputDataStream.readBoolean();

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
     * @return <code>true</true> if save was successful
     * <code>false</code> otherwise.
     */
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
        outputDataStream.writeBoolean(stockout_act_children);
        outputDataStream.writeBoolean(stockout_act_youth);
        outputDataStream.writeBoolean(stockout_act_adult);
        outputDataStream.writeBoolean(stockout_arthemeter);
        outputDataStream.writeBoolean(stockout_quinine);
        outputDataStream.writeBoolean(stockout_serum);
        outputDataStream.writeBoolean(stockout_bednet);
        outputDataStream.writeBoolean(stockout_rdt);
        outputDataStream.writeBoolean(stockout_sp);

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

    /*
     * generates a string to be used in SMS
     * @return long <code>String</true> describing all data
     * formatted for SMS sending.
     */
    public String toSMSFormat() {
        String sep = " ";
        return stockout_act_children + sep + stockout_act_youth + sep +
               stockout_act_adult + sep + stockout_arthemeter + sep +
               stockout_quinine + sep + stockout_serum + sep +
               stockout_bednet + sep + stockout_rdt + sep + stockout_sp;
    }

    /*
     * checks whether data is legit
     * checks all logic algorithms and fills the <code>_errors</code> variable.
     * @return <code>true</true> if all data is valid
     * <code>false</code> otherwise.
     */
    public boolean dataIsValid() {

        _errors = new Vector();

        // no particular test for stock outs.

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
     * @param stockout_act_children Has Children ACT ran out?
     * @param stockout_act_youth Has Youth ACT ran out?
     * @param stockout_act_adult Has Adult ACT ran out?
     * @param stockout_arthemeter Has Arthemeter ran out?
     * @param stockout_quinine Has Quinine ran out?
     * @param stockout_serum Has Serum ran out?
     * @param stockout_bednet Has bednets ran out?
     * @param stockout_rdt Has RDT ran out?
     * @param stockout_sp Has SP ran out?
     */
    public void setAll(boolean stockout_act_children,
                       boolean stockout_act_youth,
                       boolean stockout_act_adult,
                       boolean stockout_arthemeter,
                       boolean stockout_quinine,
                       boolean stockout_serum,
                       boolean stockout_bednet,
                       boolean stockout_rdt,
                       boolean stockout_sp) {
        this.stockout_act_children = stockout_act_children;
        this.stockout_act_youth = stockout_act_youth;
        this.stockout_act_adult = stockout_act_adult;
        this.stockout_arthemeter = stockout_arthemeter;
        this.stockout_quinine = stockout_quinine;
        this.stockout_serum = stockout_serum;
        this.stockout_bednet = stockout_bednet;
        this.stockout_rdt = stockout_rdt;
        this.stockout_sp = stockout_sp;
    }
}
