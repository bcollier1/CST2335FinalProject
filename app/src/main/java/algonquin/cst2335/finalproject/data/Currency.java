package algonquin.cst2335.finalproject.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This is our currency class. It will hold important information needed for our database, will keep
 * track of all the items we have created. Will be instantiated and then will be able to use them in
 * other classes, while protected in here.
 */
@Entity(tableName = "conversion_table")
public class Currency {

    /**
     * Our variable set up for the database ID for each conversion
     */
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "ID")
        private int id;

    /**
     * This variable is set for the country the user is picking from.
     */
        @ColumnInfo(name = "FromCountry")
        private String fromCountry;
    /**
     * This variable is set for the country user wants to convert to.
     */
        @ColumnInfo(name = "ToCountry")
        private String toCountry;
    /**
     * This variable is set for the amount the user has inserted to convert.
     */
        @ColumnInfo(name = "amount")
        private double amount;

    /**
     * This constructor is empty, its just a default constructor
     */
        public Currency(){
            // empty constructor
        }

    /**
     * A getter we are setting up for the ID
     * @return ID of the conversion
     */
        public int getId() {
            return id;
        }

    /**
     * A setter we are setting up for the ID
     * @param id ID of the conversion
     */
        public void setId(int id) {
            this.id = id;
        }

    /**
     * A getter we are setting up for the country user is picking FROM
     * @return will return the country FROM
     */
         public String getFromCountry() {
            return fromCountry;
        }

    /**
     * A setter we are setting up for the country user is picking FROM
     * @param fromCountry will return the country FROM
     */
        public void setFromCountry(String fromCountry) {
            this.fromCountry = fromCountry;
        }

    /**
     * A getter we are setting for the country user is converting TO
     * @return will return the country user is converting TO
     */
        public String getToCountry() {
            return toCountry;
        }

    /**
     * A setter we are setting for the country user is converting TO
     * @param toCountry will return the country user is converting TO
     */
        public void setToCountry(String toCountry) {
            this.toCountry = toCountry;
        }

    /**
     * A getter set up for the amount user will be entering
     * @return will return the amount user has put in
     */
        public double getAmount() {
            return amount;
        }

    /**
     * A setter set up for the amount the user will be entering
     * @param amount will return the amount the user has put in
     */
        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

