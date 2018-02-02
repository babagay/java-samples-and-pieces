package JavaCore.Module07;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Fruit
{
    
    @SerializedName("sort")
    private Sort sort;
 
    @SerializedName("shelfLive")
    private int shelfLive;
    
    @SerializedName("deliveryDate")
    private String deliveryDate;
    
    @SerializedName("price")
    private int price;
    
    
    public Fruit (Sort sort, int shelfLive, String deliveryDate, int price)
    {
        this.sort = sort;
        this.shelfLive = shelfLive;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }
    
    public Sort getSort ()
    {
        return sort;
    }
    
    public int getShelfLive ()
    {
        return shelfLive;
    }
    
    public String getDeliveryDate ()
    {
        return deliveryDate;
    }
    
    public int getPrice ()
    {
        return price;
    }
}
