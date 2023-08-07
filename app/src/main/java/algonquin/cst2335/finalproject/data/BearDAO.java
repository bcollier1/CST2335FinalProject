package algonquin.cst2335.finalproject.data;

/*
 * Filename: BearDAO.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: July 31, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BearDAO {

    @Insert
    long insertImage(Bear bear);
    @Query("Select * From Bear")
    List<Bear> getAllImages();

    @Query("Delete from Bear")
    void deleteAllImages();
}
