
package pnlp;

/**
 * MalariaReport sub parts
 * Report is composed of 4 parts.
 * Each includes its own logic and checks
 * @author rgaudin
 */
public interface ReportPartInterface {
    public boolean loadFromStore();
    public boolean saveInStore();
    public String toSMSFormat();
    public boolean dataIsValid();
    public boolean dataIsComplete();
    public String[] errors();
    public String errorMessage();
}
