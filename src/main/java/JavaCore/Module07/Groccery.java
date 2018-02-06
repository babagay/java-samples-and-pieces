package JavaCore.Module07;


import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * При старте поднимает базу из файла
 *
 * https://stackoverflow.com/questions/2591098/how-to-parse-json  (answer 11)
 * https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html
 *
 * либо
 * http://crunchify.com/json-manipulation-in-java-examples/
 * http://crunchify.com/how-to-write-json-object-to-file-in-java/
 *
 * http://www.baeldung.com/java-8-streams
 * http://www.baeldung.com/java-8-lambda-expressions-tips
 * http://www.baeldung.com/java-difference-map-and-flatmap
 *
 */
public class Groccery
{

    Storage storage;

    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );

    private String storageFileName;

    public Groccery(String storageFile)
    {
        storageFileName = storageFile;

        preInitStorage();
        
        try {
            initStorage();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    /**
     * Поднимает файл поставки  JsonFile
     * и вносит фрукты из него в базу
     */
    void addFruits(String pathToJsonFile) throws FileNotFoundException
    {
        ArrayList<HashMap<String,String>> list = new ArrayList(  );

        File file = new File( pathToJsonFile );
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
    List<Fruit> getSpoiledFruits(Date date)
    {
        LocalDateTime testDate = dateToLocalDate( date );

        ArrayList<Fruit> spoiled = storage.fruits.stream().filter( fruit -> {
            // дата протухания фрукта
            LocalDateTime spoiledDate = fruit.getDeliveryDate().plusDays( fruit.getShelfLive() );

            if ( testDate.compareTo( spoiledDate ) > 0 )
            {
                // протухнет
                return true;
            }

            return false;
        } ).collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );

        return spoiled;
    }

    /**
     *  список готовых к продаже продуктов
     */
    List<Fruit> getAvailableFruits(Date date)
    {
        return storage.fruits.stream()
                .filter( fruit ->
                        fruit.getDeliveryDate().plusDays( fruit.getShelfLive() ).compareTo( dateToLocalDate( date ) ) > 0
                )
                .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
    }

    private LocalDateTime dateToLocalDate(Date date)
    {
        String[] dateStr = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( date ).split( "\\s" );
        return LocalDateTime.parse( dateStr[0] + "T" + dateStr[1] + ".000" );
    }

    private String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
                FL + "Module07" + FL;
    }

    private void preInitStorage(){
//        storage = new HashMap<>(  );
//        storage.put( "Fruits", new ArrayList<>() );
    }
    

    private void initStorage() throws IOException
    {
        File storageFile = new File( storageFileName );
        InputStream targetStream = new FileInputStream( storageFile );
    
        String json;
        
        try ( final InputStreamReader reader = new InputStreamReader( targetStream )) {
              json = CharStreams.toString( reader );
        }
    
        Gson gson = new GsonBuilder().create();
    
          storage = gson.fromJson( json, Groccery.Storage.class );
    }

    /**
     * пополнить базу фруктами
     */
    private void fruitsToDB (ArrayList<HashMap<String, String>> fruitList)
    {
        fruitList.stream().map( Fruit::produce ).forEach( storage::addFruit );
    }

    // todo
    private void setFruitCount(String sort, int count){
        ArrayList<HashMap<String, Object>> list = new ArrayList<>(  );
        HashMap<String, String> map = new HashMap<>(  );
        map.put( "count", count + "" );
//        storage.put( sort + "Count", list );
    }


    private void saveStorage(){

        String s = new Gson().toJson( storage );

        BufferedWriter writer;

        try {
            FileWriter fileWriter = new FileWriter( storageFileName );

            writer = new BufferedWriter( fileWriter );

            writer.write( s );

            writer.flush();

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private static class Storage {


        @SerializedName( "Fruits" )
        private ArrayList<Fruit> fruits;

        public ArrayList<Fruit> getFruits ()
        {
            return fruits;
        }

        void addFruit(Fruit fruit){
            fruits.add( fruit );
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
