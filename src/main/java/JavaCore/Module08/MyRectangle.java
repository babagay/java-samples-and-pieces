package JavaCore.Module08;

import javafx.scene.shape.Rectangle;

import java.util.Map;

public class MyRectangle extends Rectangle
{
    String name;

    Map<String, Integer> vector;

    public MyRectangle(double x, double y, double width, double height, String name)
    {
        super(x,y,width,height);

        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setVector(Map<String, Integer> vector)
    {
        this.vector = vector;
    }
}
