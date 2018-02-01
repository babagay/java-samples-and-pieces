package JavaCore.Module07;


import com.google.gson.Gson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * При старте поднимает базу из файла
 *
 * https://stackoverflow.com/questions/2591098/how-to-parse-json  (answer 11)
 * https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html
 *
 * либо
 * http://crunchify.com/json-manipulation-in-java-examples/
 * http://crunchify.com/how-to-write-json-object-to-file-in-java/
 */
public class Groccery
{
    private HashMap<String, ArrayList<HashMap<String,Object>>> storage;

    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );

    public Groccery()
    {
        initStorage();
    }

    /**
     * Поднимает файл поставки  JsonFile
     * и вносит фрукты из него в базу
     */
    void addFruits(String pathToJsonFile) throws FileNotFoundException
    {
        ArrayList<HashMap<String,String>> list = new ArrayList(  );

        String fileName = getBasePath() + "delivery01.json";
        File file = new File( fileName );
        FileReader fileReader = new FileReader( file );

        JsonReader jsonReader = Json.createReader(fileReader);
        JsonObject obj = jsonReader.readObject();

        for ( int i = 0; i < obj.getJsonArray( "items" ).size(); i++ )
        {
            int y = i;
            HashMap<String,String> map = new HashMap(  );

            obj.getJsonArray( "items" ).getJsonObject( i ).keySet().stream()
                    .forEach( t -> {
                        try
                        {
                            map.put( t, obj.getJsonArray( "items" ).getJsonObject( y ).getJsonString( t ).toString().replaceAll( "\"","" ) );
                        }
                        catch ( ClassCastException e )
                        {
                            try
                            {
                                map.put( t, obj.getJsonArray( "items" ).getJsonObject( y ).getJsonNumber( t ).toString() );
                            }
                            catch ( Exception e1 )
                            {
                                map.put( t, String.valueOf( obj.getJsonArray( "items" ).getJsonObject( y ).getBoolean( t ) ) );
                            }
                        }
                    } );

            list.add( map );
        }

        jsonReader.close();

        // Добавить в сторадж
        fruitsToDB( list );

        // Сохранить сторадж на диск
        saveStorage();
    }

    /**
     * Сбросить базу в файл
     */
    void save(String pathToJsonFile){

    }

    /**
     * удаляют текущую информацию из коллекции и загружает новую из сохраненной версии
     */
    void load(String pathToJsonFile){

    }

    /**
     *  какие продукты испортятся к заданной дате
     */
    List<Fruit> getSpoiledFruits(Date date){
        return null;
    }

    /**
     *  список готовых к продаже продуктов
     */
    List<Fruit> getAvailableFruits(Date date){
        return null;
    }

    private String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
                FL + "Module07" + FL;
    }

    private void initStorage(){
        storage = new HashMap<>(  );
        storage.put( "Fruits", new ArrayList<>() );

        // todo загрузить из файла GSON
    }

    /**
     * пополнить базу фруктами
     */
    private void fruitsToDB(ArrayList<HashMap<String,String>> fruitList){

        fruitList.stream().forEach( item -> {

            HashMap<String,Object> map = new HashMap<>(  );
            item.entrySet().stream().forEach( entry -> {
                if( entry.getKey().equals( "shelfLive" ) )
                    map.put(  entry.getKey(), Integer.parseInt(  entry.getValue() ) );
                else if (  entry.getKey().equals( "price" ) )
                    map.put(  entry.getKey(), Integer.valueOf(  entry.getValue() ) );
                else
                    map.put(  entry.getKey(), entry.getValue() );
            } );
            storage.get( "Fruits" ).add( map );
        } );
    }

    private void setFruitCount(String sort, int count){
        ArrayList<HashMap<String, Object>> list = new ArrayList<>(  );
        HashMap<String, String> map = new HashMap<>(  );
        map.put( "count", count + "" );
        storage.put( sort + "Count", list );
    }


    private void saveStorage(){

        String s = new Gson().toJson( storage );

        BufferedWriter writer;

        try {
            FileWriter fileWriter = new FileWriter( getBasePath() + "storage.json" );

            writer = new BufferedWriter( fileWriter );

            writer.write( s );

            writer.flush();

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }





























    /**
     * [3]
     * Перегрузить имеющиеся методы spoiledFruits и availableFruits.

     На прием еще одного параметра - вид фрукта

     List<Fruit> getSpoiledFruits(Date date, Type type)

     List<Fruit> getAvailableFruits(Date date, Type type)

     Работают как и прежде, но теперь фильтруют только по заданному типу фрукта



     Добавить метод который возвращает продукты которые были доставлены в заданную дату List<Fruit> getAddedFruits(Date date) и его переопределенная версия - List<Fruit> getAddedFruits(Date date, Type type)
     */

    /**
     * [4]
     * Необходимо учитывать продукты которые были проданы. Для этого добавим метод void sell(String pathToJsonFile). Метод принимает путь к файлу с джейсоном который хранит записи о клиентах который хотели купить продукты в заданный день.



     Если продукты продукты присутствуют на складе в заданном количестве - сделка происходит и товары удаляются со склада, а на счет лавки зачисляются деньги за продукты и продукты со склада удаляются.

     В противном случае сделка не происходит и клиент уходит ни с чем, а продукты остаются не тронутыми.



     Необходимо добавить числовое значение moneyBalance которое хранит текущий баланс денег лавки. Должен сохраняться и загружаться при вызовах методов save и load.



     {

     "clients": [{

     "name": "Вася",

     "type": "Apple",

     "count": 100

     },

     {

     "name": "Джон",

     "type": "Apple",

     "count": 500

     },

     {

     "name": "Джон",

     "type": "Banana",

     "count": 1

     }

     ]

     }

     // Вася хочет купить 100 яблок, а Джон хочет купить 500 яблок и 1 банан.
     */

    /**
     * [5]
     * После выполненных задач, ваш друг разбогател и открыл много лавок с фруктами. Теперь он просит вас помочь с менеджерингом всех сразу.



     Необходимо создать класс Company. Который имеет внутри себя

     коллекцию лавок

     moneyBalance - баланс компании

     Методы
     void save(String pathToJsonFile) сохраняет всю информацию компании

     void load(String pathToJsonFile) загружает всю информацию компании

     геттер лавки по индексу из коллекции

     int getCompanyBalance() возвращает сумму балансов всех лавок

     List<Fruit> getSpoiledFruits(Date date)
     работает также, но в масштабах компании (по всем лавкам)

     List<Fruit> getAvailableFruits(Date date)
     работает также, но в масштабах компании (по всем лавкам)

     List<Fruit> getAddedFruits(Date date)
     работает также, но в масштабах компании (по всем лавкам)

     List<Fruit> getSpoiledFruits(Date date, Type type)
     работает также, но в масштабах компании (по всем лавкам)

     List<Fruit> getAvailableFruits(Date date, Type type)
     работает также, но в масштабах компании (по всем лавкам)

     List<Fruit> getAddedFruits(Date date, Type type)
     работает также, но в масштабах компании (по всем лавкам)
     */
}
