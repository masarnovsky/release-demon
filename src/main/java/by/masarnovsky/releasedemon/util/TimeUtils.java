package by.masarnovsky.releasedemon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {
    private static final LocalDate DEFAULT = LocalDate.of(1970, 1, 1);
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    private TimeUtils() {
    }

    public static LocalDate convertStringToDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            logger.info("exception while parsing data, return default");
        }

        return DEFAULT;
    }
}
