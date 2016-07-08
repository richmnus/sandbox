package ch8.q82;

import java.text.MessageFormat;

public class Respondant extends Employee implements Respondable {

    private CallStatus status;

    enum CallStatus {
        INCALL, IDLE
    }

    public Respondant(String firstname, EmployeeType type, Respondant manager,
            CallStatus status) {
        super(firstname, type, manager);
        this.setStatus(status);
    }

    @Override
    public String dispatchCall() {
        String message;
        if (status == CallStatus.IDLE) {
            status = CallStatus.INCALL;
            return String.format("I'm %s and I'm handling the call\n",
                    getFirstname());
        }
        message = String.format(
                "I'm %s and I'm busy, I'm routing the call to %s\n",
                getFirstname(), getManager().getFirstname());
        message += "\n" + ((Respondant) getManager()).dispatchCall();
        return message;
    }

    public CallStatus getStatus() {
        return status;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
    }

}
