
package pnlp;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import java.io.IOException;

/**
 *
 * @author rgaudin
 */
public class SMSSender {

    private static final int smsPort = 0;

    public boolean send(String number, String message) {

        String address = "sms://" + number + ":" + smsPort;

        MessageConnection smsconn = null;

        try {
            System.out.println("address: "+address);
            /** Open the message connection. */
            smsconn = (MessageConnection) Connector.open (address);

            TextMessage txtmessage = (TextMessage) smsconn.newMessage (MessageConnection.TEXT_MESSAGE);
            txtmessage.setAddress (address);
            txtmessage.setPayloadText (message);
            smsconn.send (txtmessage);
        }
        catch (Throwable t) {
            System.out.println ("Send caught: ");
            t.printStackTrace ();
            return false;
        }

        if (smsconn != null) {
            try {
                smsconn.close ();
            }
            catch (IOException ioe) {
                System.out.println ("Closing connection caught: ");
                ioe.printStackTrace ();
            }
        }
        return true;
    }

}
