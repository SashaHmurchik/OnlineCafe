package by.epam.cafe.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class DateParser {
    private static final Logger logger = LogManager.getLogger(DateParser.class);

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String SPLIT_REGEX = "-";

    public static LocalDate parseDate(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            logger.log(Level.FATAL, "not correct input parameter" );
            throw new RuntimeException("not correct input parameter");
        }
        String[] dateArray = date.split(SPLIT_REGEX);
        return LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
    }
}
