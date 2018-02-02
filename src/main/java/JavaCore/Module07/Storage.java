package JavaCore.Module07;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    @SerializedName( "Fruits" )
    private ArrayList<Fruit> fruits;
    
    public ArrayList<Fruit> getFruits ()
    {
        return fruits;
    }
}
