package com.cartrawler.assessment;

import com.cartrawler.assessment.car.CarFilter;
import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.Cars;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarFilterTest {

    private CarFilter carFilter;
    private List<Double> rentalCostList;

    @BeforeAll
    void setUp() {
         rentalCostList = new ArrayList<>();
        rentalCostList.add(22.09);
        rentalCostList.add(33.67 );
        rentalCostList.add(44.89);
        rentalCostList.add(55.99);
        rentalCostList.add(92.09);
        rentalCostList.add(67.09);
        carFilter = new CarFilter();
    }


    @Test
    void sort() {
        List<CarResult> list =carFilter.sort(Cars.CARS);
        Assertions.assertEquals(list.size(),239);
    }

    @Test
    void optionalStepSort() {

        List<CarResult> list =carFilter.optionalStepSort(Cars.CARS);
        Assertions.assertEquals(list.size(),73);
    }

    @Test
    void calculateMedianForRentalCost() {
        Double value = carFilter.calculateMedianForRentalCost(rentalCostList);
        Assertions.assertEquals(value,50.44);
    }


}