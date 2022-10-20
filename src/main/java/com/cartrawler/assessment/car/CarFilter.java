package com.cartrawler.assessment.car;



import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarFilter {
    private List<CarResult> sortedCorporateCars;
    private List<CarResult> sortedNonCorporateCars;

    public List<CarResult> sort(final Set<CarResult> cars) {

        /** Create corporate car list **/
        List<String> corporateCarList = new ArrayList<>();
        corporateCarList.add("AVIS");
        corporateCarList.add("BUDGET");
        corporateCarList.add("ENTERPRISE");
        corporateCarList.add("FIREFLY");
        corporateCarList.add("HERTZ");
        corporateCarList.add("SIXT");
        corporateCarList.add("THRIFTY");


        /**Generate map using the list of corporate groups
         * "True" key  in the map indicates corporate car list
         * "False" key in the map indicates non corporate car list
         * **/
       Map<Boolean, List<CarResult>> carPartition = cars.stream().collect(Collectors.partitioningBy(car->corporateCarList.contains(car.getSupplierName())));


        List<CarResult> corporateCars = carPartition.get(true);
        List<CarResult> nonCorporateCars = carPartition.get(false);

        /*** Sorting the corporate cars  “mini”, “economy”,
        *“compact” and “other” based on SIPP beginning with M, E, C respectively.
         Also  sort the rental cost price in ascending order
        ***/
             sortedCorporateCars = corporateCars.stream().sorted(
                  Comparator.comparing((CarResult c)-> c.getSippCode().charAt(0) != 'M')
                 .thenComparing((CarResult c)-> c.getSippCode().charAt(0) != 'E')
                 .thenComparing((CarResult c)-> c.getSippCode().charAt(0) != 'C')
                          .thenComparing(CarResult::getRentalCost)
                ).collect(Collectors.toList());


        /*** Sorting the Non corporate cars  “mini”, “economy”,
         “compact” and “other” based on SIPP beginning with M, E, C respectively.

         Also  sort low-to-high on rental cost price.
         ***/
            sortedNonCorporateCars = nonCorporateCars.stream().sorted(
                Comparator.comparing((CarResult c)-> c.getSippCode().charAt(0) != 'M')
                        .thenComparing((CarResult c)-> c.getSippCode().charAt(0) != 'E')
                        .thenComparing((CarResult c)-> c.getSippCode().charAt(0) != 'C')
                        .thenComparing(CarResult::getRentalCost)
        ).collect(Collectors.toList());

        /**Concatenate sorted corporate and non-corporate group list together**/
        List<CarResult> resultantList = Stream.concat(sortedCorporateCars.stream(), sortedNonCorporateCars.stream())
                .collect(Collectors.toList());

        return resultantList;

    }

    /**
     * Remove all FuelType.FULLFULL cars that are priced above the median price within their groups
     * @param cars
     * @return
     */
    public List<CarResult> optionalStepSort(Set<CarResult> cars){
        //Reusing the sorting method in order to generate sorted corporate and non corporate groups
        if(sortedCorporateCars == null &&sortedNonCorporateCars == null)
            sort(cars);
        double corporateRentalCostMedian = calculateMedianForRentalCost(sortedCorporateCars.stream().map(c-> c.getRentalCost()).collect(Collectors.toList()));

        List<CarResult> corporatecarResults = sortedCorporateCars.stream().filter(s->s.getRentalCost()<corporateRentalCostMedian&&!s.getFuelPolicy().equals(CarResult.FuelPolicy.FULLFULL))
                .collect(Collectors.toList());


        double nonCorporateRentalCostMedian = calculateMedianForRentalCost(sortedNonCorporateCars.stream().map(c-> c.getRentalCost()).collect(Collectors.toList()));


        List<CarResult> nonCorporateCarResults = sortedNonCorporateCars.stream().filter(s->s.getRentalCost()<nonCorporateRentalCostMedian&&!s.getFuelPolicy().equals(CarResult.FuelPolicy.FULLFULL))
                .collect(Collectors.toList());



        List<CarResult> resultantList = Stream.concat(corporatecarResults.stream(), nonCorporateCarResults.stream())
                .collect(Collectors.toList());

        return resultantList;
    }

    /**
     * Method to calculate the median of the rental cost of corporate and non-corporate cars.
     * @param rentalCostList
     * @return
     */
    public double calculateMedianForRentalCost(List<Double> rentalCostList){
        double median;
        if (rentalCostList.size() % 2 == 0)

            median = (rentalCostList.get(rentalCostList.size()/2) + rentalCostList.get(rentalCostList.size()/2 -1))/2;
        else
            median = rentalCostList.get(rentalCostList.size()/2);
        return median;
    }
}
