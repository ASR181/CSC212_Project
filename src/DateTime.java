```java
/**
 * Represents a date and time
 * The values cannot be changed after the object is created
 */
public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    /**
     * Creates a date and time and checks that the values are valid
     */
    public DateTime(int year, int month, int day, int hour, int minute) {

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month!");
        }

        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day!");
        }

        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour!");
        }

        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute!");
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Formats the date and time as MM/DD/YYYY HH:MM
     */
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d",
                month, day, year, hour, minute);
    }

    /**
     * Compares two dates and times in chronological order
     * Returns a negative number, zero, or a positive number
     */
    public int compareTo(IDateTime other) {

        if (year != other.getYear()) {
            if (year < other.getYear())
                return -1;
            else
                return 1;
        }

        if (month != other.getMonth()) {
            if (month < other.getMonth())
                return -1;
            else
                return 1;
        }

        if (day != other.getDay()) {
            if (day < other.getDay())
                return -1;
            else
                return 1;
        }

        if (hour != other.getHour()) {
            if (hour < other.getHour())
                return -1;
            else
                return 1;
        }

        if (minute != other.getMinute()) {
            if (minute < other.getMinute())
                return -1;
            else
                return 1;
        }

        return 0;
    }

    /*
     * Returns the formatted date and time as a string
     */
    public String toString() {
        return format();
    }
}
```
