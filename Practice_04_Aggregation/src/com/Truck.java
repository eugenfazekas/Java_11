package com;

public class Truck extends Vehicle {
    private int payload, weightPounds;

    public Truck(int payloadPounds, int weightPounds, int horsePower) {
        super(weightPounds + payloadPounds, horsePower);
        this.payload = payloadPounds;
        this.weightPounds = weightPounds;
    }

    public int getPayload() {
        return this.payload;
    }

}