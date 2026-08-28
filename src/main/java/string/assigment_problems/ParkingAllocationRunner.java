package src.main.java.string.assigment_problems;

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to slot " + slotNo);
        }
    }
}

public class ParkingAllocationRunner {

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].occupiedCount < slots[i].capacity) {
                return slots[i];
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot != null) {
            slot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] run1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(run1, "TN09AB1234");

        ParkingSlot[] run2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(run2, "TN09AB1234");
    }
}