package JavaCore.Module07;

import java.io.FileNotFoundException;

public class App
{
    
    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    
    public static void main(String[] args) throws FileNotFoundException
    {
        // Открыть лавку
        Groccery groccery = new Groccery(getBasePath() + "storage.json");
       
        // Пополнить фруктами
        // groccery.addFruits(getBasePath() + "delivery04.json");
    }
    
    private static String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
               FL + "Module07" + FL;
    }
}
