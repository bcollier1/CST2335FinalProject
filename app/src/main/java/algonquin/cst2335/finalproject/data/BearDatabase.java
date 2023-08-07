package algonquin.cst2335.finalproject.data;

/*
 * Filename: BearDatabase.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: July 31, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bear.class}, version = 1)
public abstract class BearDatabase extends RoomDatabase {
    public abstract BearDAO bearDAO();
}

