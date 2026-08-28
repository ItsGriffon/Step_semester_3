package src.main.java.string.assigment_problems;

class Employee {
    private String empId;
    private String empName;
    private double salary;

    public Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {
        if (getSalary() < stipendCap) {
            return getSalary();
        }
        return stipendCap;
    }
}

public class EmployeePayRunner {
    public static void main(String[] args) {
        Employee plain = new Employee("E101", "Alex", 40000);
        Employee manager = new ManagerEmployee("M201", "Sarah", 70000, 8000);
        Employee intern = new InternEmployee("I301", "John", 12000, 10000);

        Employee[] employees = { plain, manager, intern };

        for (int i = 0; i < employees.length; i++) {
            Employee emp = employees[i];

            if (emp instanceof ManagerEmployee) {
                ManagerEmployee mgr = (ManagerEmployee) emp;
                System.out.println("Manager effective pay: Rs " + mgr.effectiveSalary());
            } else if (emp instanceof InternEmployee) {
                InternEmployee itn = (InternEmployee) emp;
                System.out.println("Intern effective pay: Rs " + itn.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + emp.getSalary());
            }
        }
    }
}