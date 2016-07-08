package ch8.q82;

public class Employee {

    private String firstname;
    private EmployeeType type;
    private Employee manager;

    public Employee(String firstname, EmployeeType type, Employee manager) {
        this.setFirstname(firstname);
        this.setType(type);
        this.manager = manager;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    enum EmployeeType {
        MANAGER, DIRECTOR, RESPONDENT
    }

}
