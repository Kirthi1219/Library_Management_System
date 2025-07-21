package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator{

    private static final int FINE_PER_DAY=10;

    public static double calculateFine(LocalDate dueDate, LocalDate returnDate){
        if (returnDate!=null && returnDate.isAfter(dueDate)) {
            long daysLate=ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysLate*FINE_PER_DAY;
        }
        return 0.0;
    }

    public static double calculateFineTillToday(LocalDate dueDate){
        LocalDate today=LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysLate=ChronoUnit.DAYS.between(dueDate, today);
            return daysLate*FINE_PER_DAY;
        }
        return 0.0;
    }
}
