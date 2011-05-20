/*
 * 
 */

package pnlp;

/**
 *
 * @author rgaudin
 */
public interface ReportPartInterface {
    public boolean loadFromStore();
    public boolean saveInStore();
    public String toSMSFormat();
    public boolean dataIsValid();
    public String[] errors();
    public String errorMessage();
}
