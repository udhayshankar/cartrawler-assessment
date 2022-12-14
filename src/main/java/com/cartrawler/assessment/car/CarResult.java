package com.cartrawler.assessment.car;

import java.util.Objects;

public class CarResult {
    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final FuelPolicy fuelPolicy;
    
	public enum FuelPolicy {
        FULLFULL,
        FULLEMPTY};
    
    public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sipp;
        this.rentalCost = cost;
        this.fuelPolicy = fuelPolicy;
    }
    
    public String getDescription() {
        return this.description;        
    }
    
    public String getSupplierName() {
        return this.supplierName;        
    }
    
    public String getSippCode() {
        return this.sippCode;        
    }
    
    public double getRentalCost() {
        return this.rentalCost;        
    }
    
    public FuelPolicy getFuelPolicy() {
        return this.fuelPolicy;
    }
    
    public String toString() {
        return this.supplierName + " : " +
            this.description + " : " +
            this.sippCode + " : " +
            this.rentalCost + " : " +
            this.fuelPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarResult)) return false;
        CarResult carResult = (CarResult) o;
        return getDescription().equals(carResult.getDescription()) && getSupplierName().equals(carResult.getSupplierName()) && getSippCode().equals(carResult.getSippCode()) && getFuelPolicy() == carResult.getFuelPolicy();
    }

    /** Generates unique hashcode value based on the description, model, supplier, SIPP, FuelPolicy that removes duplicates when creating object on the class **/
    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getSupplierName(), getSippCode(), getFuelPolicy());
    }
}
