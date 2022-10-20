package com.cartrawler.assessment.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CarFilterWithoutLibrary {
    private List<CarResult> sortedCorporateCars;
    private List<CarResult> sortedNonCorporateCars;

   public List<CarResult> sortWithoutFilter(final Set<CarResult> cars){
       /** Create corporate car list **/
    List<String> corporateCarList = new ArrayList<>();
    corporateCarList.add("AVIS");
    corporateCarList.add("BUDGET");
    corporateCarList.add("ENTERPRISE");
    corporateCarList.add("FIREFLY");
    corporateCarList.add("HERTZ");
    corporateCarList.add("SIXT");
    corporateCarList.add("THRIFTY");
    List<CarResult> corporateCars = new ArrayList<>();
    List<CarResult> nonCorporateCars = new ArrayList<>();
    /**Populate the car result values into corporate and non-corporate groups respectively**/
        for(CarResult car:cars){
            if(corporateCarList.contains(car.getSupplierName())){
                corporateCars.add(car);
            }
            else{
                nonCorporateCars.add(car);
            }
        }

       sortedCorporateCars = new ArrayList<>();
       /**Generate mini, economic, compact, and other car lists and sort them based on the rental cost
        * for corporate groups**/
       List<CarResult> miniCarsCorporate =  splitBySippCodeAndSortByRentalCost(corporateCars,'M');
       List<CarResult> economyCarsCorporate = splitBySippCodeAndSortByRentalCost(corporateCars,'E');
       List<CarResult> compactCarsCorporate = splitBySippCodeAndSortByRentalCost(corporateCars,'C');
       List<CarResult> otherCarsCorporate =  splitBySippCode(corporateCars);
       /**Append all lists together into sorted corporate car group**/

       sortedCorporateCars = fillLists(sortedCorporateCars,miniCarsCorporate);
       sortedCorporateCars = fillLists(sortedCorporateCars,economyCarsCorporate);
       sortedCorporateCars = fillLists(sortedCorporateCars,compactCarsCorporate);
       sortedCorporateCars = fillLists(sortedCorporateCars,otherCarsCorporate);


        sortedNonCorporateCars = new ArrayList<>();

       /**Generate mini, economic, compact, and other car lists  and sort them based on the
        * rental cost for non-corporate group **/
       List<CarResult> miniCarsNonCorporate =  splitBySippCodeAndSortByRentalCost(nonCorporateCars,'M');
       List<CarResult> economyCarsNonCorporate = splitBySippCodeAndSortByRentalCost(nonCorporateCars,'E');
      List<CarResult> compactCarsNonCorporate = splitBySippCodeAndSortByRentalCost(nonCorporateCars,'C');
       List<CarResult> otherCarsNonCorporate =  splitBySippCode(nonCorporateCars);

        /**Append all lists together into sorted non-corporate car group**/
       sortedNonCorporateCars = fillLists(sortedNonCorporateCars,miniCarsNonCorporate);
       sortedNonCorporateCars = fillLists(sortedNonCorporateCars,economyCarsNonCorporate);
       sortedNonCorporateCars = fillLists(sortedNonCorporateCars,compactCarsNonCorporate);
       sortedNonCorporateCars = fillLists(sortedNonCorporateCars,otherCarsNonCorporate);



       /**Append sorted corporate and non corporate group list together**/
       List<CarResult> resultantList = new ArrayList<>();
       resultantList = fillLists(resultantList,sortedCorporateCars);
       resultantList = fillLists(resultantList,sortedNonCorporateCars);



    return resultantList;

    }

    /**
     * Populate one list values into another list
     * @param resultList
     * @param list
     * @return
     */
    public List<CarResult> fillLists(List<CarResult> resultList, List<CarResult> list){
           for(CarResult car: list){
               resultList.add(car);
           }
           return resultList;
    }

    /**
     * Get the car values that belongs to other categories of the Sipp code
     * @param carResults
     * @return
     */
    public List<CarResult> splitBySippCode(List<CarResult> carResults){
        List<CarResult> resultCars = new ArrayList<>();
        for(CarResult car: carResults){
            if(car.getSippCode().charAt(0) !='M'&& car.getSippCode().charAt(0) !='E'&&car.getSippCode().charAt(0) !='C'){
                resultCars.add(car);
            }
        }
        return selectionSort(resultCars);

    }

    /**
     * Get the car values that belongs to categories of the Sipp code(M or E or C)
     * @param carResults
     * @param ch
     * @return
     */
    public List<CarResult> splitBySippCodeAndSortByRentalCost(List<CarResult> carResults, Character ch){
        List<CarResult> resultCars = new ArrayList<>();
        for(CarResult car: carResults){
            if(car.getSippCode().charAt(0) == ch ){
                resultCars.add(car);
            }
        }
        return selectionSort(resultCars);

    }

    /**
     * selection sort method to sort the list
     * @param carResultList
     * @return
     */
    public List<CarResult> selectionSort(List<CarResult> carResultList){
        for (int i = 0; i < carResultList.size() - 1; i++)
        {
            for (int j = i + 1; j < carResultList.size(); j++)
            {
                if (carResultList.get(i).getRentalCost() > carResultList.get(j).getRentalCost()) {

                    CarResult temp = carResultList.get(j);
                    carResultList.set(j, carResultList.get(i));
                    carResultList.set(i, temp);
                }
            }
        }
        return carResultList;
    }

    /**
     * Optional sort method to remove all FuelType.FULLFULL cars that are priced above the
     * median price within their groups
     * @param cars
     * @return
     */
    public List<CarResult> optionalSort(Set<CarResult> cars){
        //Reusing the sorting method in order to generate sorted corporate and non corporate groups
        if(sortedCorporateCars == null &&sortedNonCorporateCars == null)
             sortWithoutFilter(cars);
       List<Double> corporateRentalCostList = new ArrayList<>();
       //Get the rental cost values of the sorted corporate cars
       for(CarResult car: sortedCorporateCars){
           corporateRentalCostList.add(car.getRentalCost());
       }
       //calculate the median cost for the rental cost of the sorted corporate cars
        double corporateRentalCostMedian = calculateMedianForRentalCost(corporateRentalCostList);

        List<Double> nonCorporateRentalCostList = new ArrayList<>();
        //Get the rental cost values of the sorted non-corporate cars
        for(CarResult car: sortedNonCorporateCars){
            nonCorporateRentalCostList.add(car.getRentalCost());
        }

        //calculate the median for the rental cost of the sorted non-corporate cars
        double nonCorporateRentalCostMedian = calculateMedianForRentalCost(nonCorporateRentalCostList);

        List<CarResult> corporateCarResults = new ArrayList<>();
        //Logic to remove all FuelType.FULLFULL cars that are priced above
        //the median price within their groups
        for(CarResult car: sortedCorporateCars){
            if(car.getRentalCost() < corporateRentalCostMedian && !car.getFuelPolicy().equals(CarResult.FuelPolicy.FULLFULL))
            {
                corporateCarResults.add(car);
            }
        }
        List<CarResult> nonCorporateCarResults = new ArrayList<>();
        //Logic to remove all FuelType.FULLFULL cars that are priced above
        //the median price within their groups
        for(CarResult car: sortedNonCorporateCars){
            if(car.getRentalCost() < nonCorporateRentalCostMedian && !car.getFuelPolicy().equals(CarResult.FuelPolicy.FULLFULL))
            {
                nonCorporateCarResults.add(car);
            }
        }
        ///Append two lists and return as a resultant list
        List<CarResult> optionalResult = new ArrayList<>();
        optionalResult =  fillLists(optionalResult,corporateCarResults);
        optionalResult = fillLists(optionalResult,nonCorporateCarResults);


        return optionalResult;
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
