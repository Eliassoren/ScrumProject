package shift;

import java.util.ArrayList;

public class Shiftlist {

    private ArrayList<Shift> shiftList = new ArrayList<>();
    
    public Shiftlist(ArrayList<Shift> shiftList) {
        this.shiftList = shiftList;
    }
    
    public ArrayList<Shift> getShiftlistAll() {
        return shiftList;
    }
    
    
    
}
