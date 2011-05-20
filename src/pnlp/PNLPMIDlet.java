package pnlp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import pnlp.MalariaUnderFiveReport.*;

public class PNLPMIDlet extends MIDlet implements CommandListener {

    private Command exitCommand;
    private Display display;

    public PNLPMIDlet() {
        display = Display.getDisplay(this);
        exitCommand = new Command("Quitter", Command.EXIT, 0);
    }

    public void startApp() {

        // store some arbitrary data in the RS
        MalariaUnderFiveReport under_five = new MalariaUnderFiveReport();
        under_five.setAll(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048);
        under_five.saveInStore();

        // load those same data
        MalariaUnderFiveReport under_five_a = new MalariaUnderFiveReport();
        under_five_a.loadFromStore();

        // display loaded data
        TextBox t = new TextBox("PNLP Data", under_five_a.toSMSFormat(), 256, 0);

        t.addCommand(exitCommand);
        t.setCommandListener(this);

        display.setCurrent(t);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        } 
    }

}
