package com.utitech.carwash.controller.request;

public class WasherData {
    private int field1;
    private int field2;
    private int field3;
    private boolean status1;
    private boolean status2;
    private boolean status3;

    // Constructor
    public WasherData(int field1, int field2, int field3,
                      boolean status1, boolean status2, boolean status3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
    }

    // Getters (needed for JSON serialization)
    public int getField1() { return field1; }
    public int getField2() { return field2; }
    public int getField3() { return field3; }
    public boolean isStatus1() { return status1; }
    public boolean isStatus2() { return status2; }
    public boolean isStatus3() { return status3; }
}