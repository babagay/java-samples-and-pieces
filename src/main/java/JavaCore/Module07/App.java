package JavaCore.Module07;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class App
{
    
    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    
    public static void main(String[] args) throws FileNotFoundException, ParseException
    {
        // Открыть лавку
        Groccery groccery = new Groccery(getBasePath() + "storage.json");
       
        // Пополнить фруктами
        // groccery.addFruits(getBasePath() + "delivery04.json");

        // Взять фрукты, которые протухнут к 3-му марта 2018
        List<Fruit> spoiledFruitsList = groccery.getSpoiledFruits( new SimpleDateFormat( "dd/MM/yyyy" ).parse( "07/03/2018" ) );

        // фрукты, которые к 3-му марта 2018 будут свежими
        List<Fruit> freshFruits = groccery.getAvailableFruits( new SimpleDateFormat( "dd/MM/yyyy" ).parse( "07/03/2018" ) );




        System.out.println("OK");


    }
    
    private static String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
               FL + "Module07" + FL;
    }
}
