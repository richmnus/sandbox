package uk.martinus.jcip.ch3;

/**
 * Access to a private mutable variable is controlled by class methods
 * synchronized on the class object's monitor lock
 *
 */
public class JavaMonitorPattern {

    private int sharedVariable;

    public JavaMonitorPattern() {
    }

    public synchronized void setSharedVariable(int i) {
        sharedVariable = i;
    }

    public synchronized int getSharedVariable() {
        return sharedVariable;
    }

}
