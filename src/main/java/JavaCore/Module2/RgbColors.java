package JavaCore.Module2;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

public class RgbColors extends Applet
{
    public static int[] getRGB(String rgb)
    {
        final int[] ret = new int[3];
        for ( int i = 0; i < 3; i++ )
        {
            ret[i] = Integer.parseInt( rgb.substring( i * 2, i * 2 + 2 ), 16 );
        }
        return ret;
    }

    public void paint(Graphics g)
    {
        System.out.print( "цвет в формате RRGGBB: " );
        Scanner in = new Scanner( System.in );
        String RRGGBB = in.nextLine();
        int[] rgbArray = getRGB( RRGGBB );

        g.setColor( new Color( rgbArray[0], rgbArray[1], rgbArray[2] ) );
        g.drawString( "Hello World!", 50, 100 );
    }

}
