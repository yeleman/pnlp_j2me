
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

    public int stockout_act_children = -1;
    public int stockout_act_youth = -1;
    public int stockout_act_adult = -1;
    public int stockout_artemether = -1;
    public int stockout_quinine = -1;
    public int stockout_serum = -1;
    public int stockout_bednet = -1;
    public int stockout_rdt = -1;
    public int stockout_sp = -1;

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

        stockout_act_children = inputDataStream.readInt();
        stockout_act_youth = inputDataStream.readInt();
        stockout_act_adult = inputDataStream.readInt();
        stockout_artemether = inputDataStream.readInt();
        stockout_quinine = inputDataStream.readInt();
        stockout_serum = inputDataStream.readInt();
        stockout_bednet = inputDataStream.readInt();
        stockout_rdt = inputDataStream.readInt();
        stockout_sp = inputDataStream.readInt();

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
        outputDataStream.writeInt(stockout_act_children);
        outputDataStream.writeInt(stockout_act_youth);
        outputDataStream.writeInt(stockout_act_adult);
        outputDataStream.writeInt(stockout_artemether);
        outputDataStream.writeInt(stockout_quinine);
        outputDataStream.writeInt(stockout_serum);
        outputDataStream.writeInt(stockout_bednet);
        outputDataStream.writeInt(stockout_rdt);
        outputDataStream.writeInt(stockout_sp);

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
        String sep = " ";
        return stockout_act_children + sep + stockout_act_youth + sep +
               stockout_act_adult + sep + stockout_artemether + sep +
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

        if (!(dataIsComplete())) {
            _errors.addElement("Tous les champs ne sont pas remplis");
        }

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
     * @param stockout_artemether Has Artemether ran out?
     * @param stockout_quinine Has Quinine ran out?
     * @param stockout_serum Has Serum ran out?
     * @param stockout_bednet Has bednets ran out?
     * @param stockout_rdt Has RDT ran out?
     * @param stockout_sp Has SP ran out?
     */
    public void setAll(int stockout_act_children,
                       int stockout_act_youth,
                       int stockout_act_adult,
                       int stockout_artemether,
                       int stockout_quinine,
                       int stockout_serum,
                       int stockout_bednet,
                       int stockout_rdt,
                       int stockout_sp) {
        this.stockout_act_children = stockout_act_children;
        this.stockout_act_youth = stockout_act_youth;
        this.stockout_act_adult = stockout_act_adult;
        this.stockout_artemether = stockout_artemether;
        this.stockout_quinine = stockout_quinine;
        this.stockout_serum = stockout_serum;
        this.stockout_bednet = stockout_bednet;
        this.stockout_rdt = stockout_rdt;
        this.stockout_sp = stockout_sp;
    }

    public boolean dataIsComplete() {
        if (stockout_act_children != -1 &&
            stockout_act_youth != -1 &&
            stockout_act_adult != -1 &&
            stockout_artemether != -1 &&
            stockout_quinine != -1 &&
            stockout_serum != -1 &&
            stockout_bednet != -1 &&
            stockout_rdt != -1 &&
            stockout_sp != -1) {
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
