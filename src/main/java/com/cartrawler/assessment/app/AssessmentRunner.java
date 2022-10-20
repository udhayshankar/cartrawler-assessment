package com.cartrawler.assessment.app;

import com.cartrawler.assessment.car.CarFilter;
import com.cartrawler.assessment.car.CarFilterWithoutLibrary;
import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.view.Display;


import  com.cartrawler.assessment.car.Cars;

import java.util.List;

public class AssessmentRunner {

    public static void main(String[] args) {
        Display display = new Display();
        CarFilter carFilter = new CarFilter();
        System.out.println("--------USING LIBRARY METHOD--------");
        List<CarResult> resultList = carFilter.sort(Cars.CARS);
        System.out.println("\n*******Displaying objective results*******");
        System.out.println("\nList size is:"+resultList.size());
        display.render(resultList);

        System.out.println("\n*******Displaying Optional results of Library method*******");
        List<CarResult> optionalResultList  = carFilter.optionalStepSort(Cars.CARS);
        System.out.println("\nList size is:"+optionalResultList.size());
         display.render(optionalResultList);
         System.out.println("\n------END OF USING LIBRARY METHOD-----");
         System.out.println("\n\n\n*********WITHOUT USING LIBRARY METHOD**********");
        CarFilterWithoutLibrary carFilterWithoutLibrary = new CarFilterWithoutLibrary();
        System.out.println("\n*******Displaying objective results*******");
        List<CarResult> listwithoutFilter = carFilterWithoutLibrary.sortWithoutFilter(Cars.CARS);
        System.out.println("\nList size is:"+listwithoutFilter.size());
        display.render(listwithoutFilter);
        System.out.println("\n*******Displaying Optional results*******");
        List<CarResult> list = carFilterWithoutLibrary.optionalSort(Cars.CARS);
        System.out.println("\nList size is:"+list.size());
        display.render(list);
    }


}