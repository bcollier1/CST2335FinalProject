package algonquin.cst2335.finalproject.data;

/*
 * Filename: Bear.java
 * Author: Brady Collier
 * Student Number: 041070576
 * Course & Section #: 23S_CST2335_023
 * Creation date: July 31, 2023
 * Modified date: August 7, 2023
 * Due Date: August 7, 2023
 * Lab Professor: Adewole Adewumi
 * Description:
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bear {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "width")
    public String width;

    @ColumnInfo(name = "height")
    public String height;

    public Bear(){
    }

    public Bear (String n, String w, String h){
        this.name = n;
        this.width = w;
        this.height = h;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() { return width; }

    public void setWidth(String width) { this.width = width; }

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

}
