package JavaCore.Module07;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Fruit
{

    @SerializedName("sort")
    private Sort sort;

    @SerializedName("shelfLive")
    private int shelfLive;

    @SerializedName("deliveryDate")
    private LocalDateTime deliveryDate;

    @SerializedName("price")
    private double price;

    public Fruit(Sort sort, int shelfLive, LocalDateTime deliveryDate, int price)
    {
        this.sort = sort;
        this.shelfLive = shelfLive;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }

    private Fruit()
    {
    }

    public static Fruit produce(HashMap<String, String> map)
    {
        Fruit fruit = new Fruit();

        Field field1;

        try
        {
            for ( Field field : Fruit.class.getDeclaredFields() )
            {
                if ( field.isAnnotationPresent( SerializedName.class ) )
                {

                    String value = map.get( field.getName() );
                    if ( value != null )
                    {
                        String type = field.getAnnotatedType().getType().getTypeName();
                        field1 = fruit.getClass().getDeclaredField( field.getName() );
                        switch ( type )
                        {
                            case "JavaCore.Module07.Sort":
                                field1.set( fruit, Sort.valueOf( value ) );
                                break;
                            case "int":
                                field1.set( fruit, Integer.parseInt( value ) );
                                break;
                            case "double":
                                field1.set( fruit, Double.parseDouble( value ) );
                                break;
                            case "java.time.LocalDateTime":
                                field1.set( fruit, LocalDateTime.parse( value ) );
                                break;
                        }
                    }
                }
            }
        }
        catch ( NoSuchFieldException | IllegalAccessException e )
        {
            e.printStackTrace();
        }

        return fruit;
    }

    public Sort getSort()
    {
        return sort;
    }

    public int getShelfLive()
    {
        return shelfLive;
    }

    public java.time.LocalDateTime getDeliveryDate()
    {
        return deliveryDate;
    }

    public double getPrice()
    {
        return price;
    }

    @Override
    public String toString()
    {
        return "Fruit{" +
                "sort=" + sort +
                ", shelfLive=" + shelfLive +
                ", deliveryDate=" + deliveryDate +
                ", price=" + price +
                '}';
    }
}
