package algonquin.cst2335.finalproject.data;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.ui.CurrencyResult;

/**
 * This is the control room for the interface the user will see. IT holds and manages all the info
 * and data on the apps screen (Fragment and activities), layout will change as will when screen has
 * been rotated.
 * This entire class will also work with the DAO. It will load a list of the conversions the user has
 * already done in the main. You can even select to remove it or undo the remove if needed back.
 *
 */
public class CurrencyViewModel extends AndroidViewModel {

    /**
     * Live data object for currencies, will be presented in a list
     */
    private LiveData<List<Currency>> currencies;
    /**
     * DAO object set up for the currency
     */
    private CurrencyDAO currencyDAO;
    /**
     * The database set up for the currency
     */
    private CurrencyDatabase db;

    /**
     * The currently selected currency
     */
    private final MutableLiveData<Currency> selectedCurrency = new MutableLiveData<>();

    /**
     * This will initialize the current viewModel, the database, and the dao
     * @param application set up for the currency database creation
     */
        public CurrencyViewModel(Application application) {
            super(application);
            db = Room.databaseBuilder(application, CurrencyDatabase.class, "currencyDB").build();
            currencyDAO = db.DAO();
            currencies = currencyDAO.getAllCurrencies();
        }

    /**
     * Will return a list of all the live data in a list format
     * @return currency will be returned in a list
     */
        public LiveData<List<Currency>> getCurrencies() {
            return currencies;
        }

    /**
     * A new conversion will be inserted inside of the database
     * @param currency Conversion will be inserted into the database
     */
        public void insert(Currency currency) {
            new Thread(() -> {
                currencyDAO.insert(currency);
                Log.d("CurrencyViewModel", "Inserted new currency: " + currency);
            }).start();
        }

    /**
     * Will delete a conversion in the list from the database
     * @param currency value can be deleted from database
     */
        public void delete(Currency currency) {
            new Thread(() -> {
                currencyDAO.delete(currency);
            }).start();
        }

    /**
     * This will set a value for each conversion selected
     * @param currency value set for selected conversion
     */
        public void selectCurrency(Currency currency) {
            selectedCurrency.setValue(currency);
        }

    /**
     * Will return all the data upon the selected conversion
     * @return LiveData of currently selected conversion
     */

        public LiveData<Currency> getSelectedCurrency() {
           return selectedCurrency;
        }
}



