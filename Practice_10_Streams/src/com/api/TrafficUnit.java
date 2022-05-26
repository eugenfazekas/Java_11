package com.api;

import com.api.SpeedModel.RoadCondition;
import com.api.SpeedModel.TireCondition;
import com.api.Vehicle.VehicleType;


public interface TrafficUnit {
    VehicleType getVehicleType();
    int getHorsePower();
    int getWeightPounds();
    int getPayloadPounds();
    int getPassengersCount();
    double getSpeedLimitMph();
    double getTraction();
    RoadCondition getRoadCondition();
    TireCondition getTireCondition();
    int getTemperature();
}
