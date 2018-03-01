package JavaCore.Module07;

import com.google.gson.annotations.SerializedName;

/**
 * Sort.BANANA.name() // BANANA
 * Sort.BANANA.toString() // Banana
 * Sort.BANANA.getName() // Banana
 * Sort.BANANA // Banana
 */
public enum Sort
{
    @SerializedName("Apple")
    APPLE( "Apple" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Banana")
    BANANA( "Banana" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Raspberries")
    RASPBERRIES( "Raspberries" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Peach")
    PEACH( "Peach" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Quince")
    QUINCE( "Quince" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Avocado")
    AVOCADO( "Avocado" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            },
    @SerializedName("Grapefruit")
    GRAPEFRUIT( "Grapefruit" )
            {
                @Override
                public String toString()
                {
                    return getName();
                }
            };

    String getName()
    {
        return fruitName;
    }

    private String fruitName;

    Sort(String name)
    {
        fruitName = name;
    }
}
