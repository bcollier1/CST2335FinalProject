package algonquin.cst2335.finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This class is our DAO implementation. Its a interface class which will hold the necessary data
 * needed to add into the database. The DAO will insert a new conversion into the database, it can
 * also be deleted if needed. We also have the option of retrieving the entire list of conversions
 * needed, which will also be the same for the ID
 */
@Dao
public interface CurrencyDAO {
    /**
     * Inserting a new currency converted into the database
     * @param currency new insert of conversion into the database
     */
    @Insert
    void insert(Currency currency);

    /**
     * Delete a conversion inside of the databse
     * @param currency can delete an item inside of the databse
     */
    @Delete
    void delete(Currency currency);

    /**
     * LiveData of converted currency will show up in a table inside of the database
     * @return Conversions inside of the database
     */
    @Query("SELECT * FROM conversion_table")
    LiveData<List<Currency>> getAllCurrencies();

    /**
     * LiveData of the currency will show the list of data by ID in the database
     * @param id Id of converted item inside of the database
     * @return will return the database ID
     */
    @Query("SELECT * FROM conversion_table WHERE ID = :id")
    LiveData<Currency> getCurrencyById(int id);
}
