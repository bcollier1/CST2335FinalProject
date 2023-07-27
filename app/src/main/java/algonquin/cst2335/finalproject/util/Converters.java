package algonquin.cst2335.finalproject.util;

import androidx.room.TypeConverter;

import java.sql.Date;

/**
 * Type converters for Room database.
 */
public class Converters {
    /**
     * Converts a timestamp into a Date.
     *
     * @param value The Long value to be converted.
     * @return The converted Date object or null if the input value is null.
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Converts a Date object into a timestamp.
     *
     * @param date The Date object to be converted.
     * @return The converted Long value or null if the input Date object is null.
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
