package algonquin.cst2335.finalproject.data;

/*
 * Filename: BearViewModel.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: July 31, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BearViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Bear>> bearImages = new MutableLiveData<>();
    public MutableLiveData<Bear> selectedImage = new MutableLiveData<>(null);

}
