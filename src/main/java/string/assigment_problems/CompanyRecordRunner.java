package src.main.java.string.assigment_problems;

class CompanyEmployeeRecord {
    static int totalRecords = 0;

    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public String fullProfile() {
        double pay = employee.getSalary();
        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            pay = ((InternEmployee) employee).effectiveSalary();
        }

        String slotInfo = (slot != null) ? slot.slotNo : "no parking assigned";
        return name + " | Pay: Rs " + pay + " | Slot: " + slotInfo;
    }
}

public class CompanyRecordRunner {
    public static void main(String[] args) {
        ParkingSlot slot1 = new ParkingSlot("A1", 1, 0);
        ParkingSlot slot2 = new ParkingSlot("A2", 1, 0);

        slot1.allot("TN09AB1234");
        slot2.allot("TN09CD5678");

        Employee manager = new ManagerEmployee("M101", "Divya", 70000, 8000);
        Employee plain = new Employee("E202", "Karan", 40000);
        Employee intern = new InternEmployee("I303", "Meera", 12000, 10000);

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "M101", manager, slot1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E202", plain, slot2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "I303", intern, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}