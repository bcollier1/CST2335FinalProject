package algonquin.cst2335.finalproject.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bear {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "width")
    private String width;

    @ColumnInfo(name = "height")
    private String height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
