package JavaCore.Module07;

import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.AtomicDouble;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * При старте поднимает базу из файла
 * <p>
 * https://stackoverflow.com/questions/2591098/how-to-parse-json  (answer 11)
 * https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html
 * <p>
 * либо
 * http://crunchify.com/json-manipulation-in-java-examples/
 * http://crunchify.com/how-to-write-json-object-to-file-in-java/
 * <p>
 * http://www.baeldung.com/java-8-streams
 * http://www.baeldung.com/java-8-lambda-expressions-tips
 * http://www.baeldung.com/java-difference-map-and-flatmap
 */
public class Groccery {
    Storage storage;
    
    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    
    private String storageFileName;
    
    public Groccery (String storageFile)
    {
        storageFileName = storageFile;
        
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
    void addFruits (String pathToJsonFile) throws FileNotFoundException
    {
        ArrayList<HashMap<String, String>> list = new ArrayList();
        
        File file = new File( pathToJsonFile );
        FileReader fileReader = new FileReader( file );
        
        JsonReader jsonReader = Json.createReader( fileReader );
        JsonObject obj = jsonReader.readObject();
        
        for ( int i = 0; i < obj.getJsonArray( "items" ).size(); i++ ) {
            int y = i;
            HashMap<String, String> map = new HashMap();
            
            obj.getJsonArray( "items" ).getJsonObject( i ).keySet().stream()
               .forEach( t -> {
                   try {
                       map.put( t, obj.getJsonArray( "items" )
                                      .getJsonObject( y )
                                      .getJsonString( t )
                                      .toString()
                                      .replaceAll( "\"", "" ) );
                   }
                   catch ( ClassCastException e ) {
                       try {
                           map.put( t, obj.getJsonArray( "items" ).getJsonObject( y ).getJsonNumber( t ).toString() );
                       }
                       catch ( Exception e1 ) {
                           map.put( t, String.valueOf( obj.getJsonArray( "items" )
                                                          .getJsonObject( y )
                                                          .getBoolean( t ) ) );
                       }
                   }
               } );
            
            list.add( map );
        }
        
        jsonReader.close();
        
        fruitsToDB( list );
        
        saveStorage();
    }
    
    /**
     * Сбросить базу в файл
     */
    void save (String pathToJsonFile)
    {
        storageFileName = pathToJsonFile;
        saveStorage();
    }
    
    /**
     * удаляют текущую информацию из коллекции и загружает новую из сохраненной версии
     */
    void load (String pathToJsonFile) throws IOException
    {
        storageFileName = pathToJsonFile;
        
        initStorage();
    }
    
    /**
     * какие продукты испортятся к заданной дате
     */
    List<Fruit> getSpoiledFruits (Date date)
    {
        LocalDateTime testDate = dateToLocalDate( date );
        
        ArrayList<Fruit> spoiled = storage.fruits.stream().filter( fruit -> {
            // дата протухания фрукта
            LocalDateTime spoiledDate = fruit.getDeliveryDate().plusDays( fruit.getShelfLive() );
            
            if ( testDate.compareTo( spoiledDate ) > 0 ) {
                // протухнет
                return true;
            }
            
            return false;
        } ).collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
        
        return spoiled;
    }
    
    /**
     * список готовых к продаже продуктов
     */
    List<Fruit> getAvailableFruits (Date date)
    {
        return storage.fruits.stream()
                             .filter( fruit ->
                                              fruit.getDeliveryDate()
                                                   .plusDays( fruit.getShelfLive() )
                                                   .compareTo( dateToLocalDate( date ) ) > 0
                                    )
                             .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
    }
    
    /**
     * продукты которые были доставлены
     * в заданную дату
     */
    List<Fruit> getAddedFruits (Date date)
    {
        LocalDateTime testDate = dateToLocalDate( date ).withHour( 0 ).withMinute( 0 );
        LocalDateTime testDate1 = dateToLocalDate( date ).plusDays( 1 ).withHour( 0 ).withMinute( 0 );
        
        return storage.fruits.stream()
                             .filter( fruit ->
                                              fruit.getDeliveryDate().isAfter( testDate ) &&
                                              fruit.getDeliveryDate().isBefore( testDate1 )
                                    )
                             .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
        
    }
    
    List<Fruit> getAddedFruits (Date date, Sort sort)
    {
        LocalDateTime testDate = dateToLocalDate( date ).withHour( 0 ).withMinute( 0 );
        LocalDateTime testDate1 = dateToLocalDate( date ).plusDays( 1 ).withHour( 0 ).withMinute( 0 );
        
        return storage.fruits.stream()
                             .filter( fruit ->
                                              fruit.getSort().equals( sort ) &&
                                              fruit.getDeliveryDate().isAfter( testDate ) &&
                                              fruit.getDeliveryDate().isBefore( testDate1 )
                                    )
                             .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
    }
    
    List<Fruit> getSpoiledFruits (Date date, Sort sort)
    {
        return storage.fruits.stream()
                             .filter( fruit ->
                                              fruit.getSort().equals( sort ) && fruit.getDeliveryDate()
                                                                                     .plusDays( fruit.getShelfLive() )
                                                                                     .compareTo( dateToLocalDate(
                                                                                             date ) ) <
                                                                                0
                                    )
                             .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
    }
    
    List<Fruit> getAvailableFruits (Date date, Sort sort)
    {
        return storage.fruits.stream()
                             .filter( fruit ->
                                              fruit.getSort().equals( sort ) && fruit.getDeliveryDate()
                                                                                     .plusDays( fruit.getShelfLive() )
                                                                                     .compareTo( dateToLocalDate(
                                                                                             date ) ) >
                                                                                0
                                    )
                             .collect( ArrayList<Fruit>::new, ArrayList::add, ArrayList::addAll );
    }
    
    
    void sell (String pathToJsonFile) throws Exception
    {
        String json = "";
        File storageFile = new File( pathToJsonFile );
        
        try ( final InputStreamReader reader = new InputStreamReader( new FileInputStream( storageFile ) ) ) {
            json = CharStreams.toString( reader );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        
        Gson gson = new GsonBuilder().create();
        
        Buyers buyers = gson.fromJson( json, Buyers.class );
        
        mapBuyersToFruits( buyers ).entrySet().stream().forEach( this::sellFruits );
        
        saveStorage();
    }
    
    private void sellFruits (HashMap.Entry<String, HashMap<Sort, Integer>> personToFruits)
    {
        if ( isThereNeededFruitsInStorage( personToFruits ) ) {
            withdrawFruits( personToFruits, LocalDateTime.now() );
        }
        else {
            System.out.println( personToFruits.getKey() + " уходит ни с чем" );
        }
    }
    
    /**
     * есть ли свежие фрукты на текущую дату в нужном количестве
     */
    private boolean isThereNeededFruitsInStorage (HashMap.Entry<String, HashMap<Sort, Integer>> person)
    {
        if ( person.getValue().size() >
             person.getValue().entrySet().stream()
                   .filter( entry ->
                                    getAvailableFruits( new Date(), entry.getKey() ).size() >= entry.getValue()
                          )
                   .collect( Collectors.toList() ).size() )
        { return false; }
        
        return true;
    }
    
    private void withdrawFruits (HashMap.Entry<String, HashMap<Sort, Integer>> person, LocalDateTime date)
    {
        System.out.println(
                person.getKey() + " покупает фрукты. Всего в наличии " + storage.fruits.size() + " единиц" );
        person.getValue().entrySet().stream()
              .forEach( entry -> storage.withdrawFruit( entry.getKey(), entry.getValue(), date ) );
        System.out.println( "...осталось ещё " + storage.fruits.size() );
    }
    
    private HashMap<String, HashMap<Sort, Integer>> mapBuyersToFruits (Buyers buyers) throws Exception
    {
        if ( buyers.buyers.size() == 0 ) { throw new Exception( "No buyers found" ); }
        
        return
                buyers.buyers.parallelStream()
                             .collect( Collectors.groupingBy(
                                     Buyer::getByerName,
                                     Collectors.groupingBy( Buyer::getType ) )
                                     )
                             .entrySet().parallelStream()
                             .collect( Collectors.toMap(
                                     entry -> entry.getKey(),
                                     entry -> entry.getValue().entrySet().stream()
                                                   .collect( Collectors.toMap(
                                                           item -> item.getKey(),
                                                           item -> new Integer( item.getValue().get( 0 ).getCount() ),
                                                           (a, b) -> b,
                                                           HashMap::new
                                                                             ) ),
                                     (old, latest) -> latest,
                                     HashMap::new
                                                       ) );
// OK
//
//        HashMap<String, HashMap<Sort, Integer>> buyersToFruits = new HashMap<>();
//
//        for ( Buyer buyer : buyers.buyers )
//        {
//            String name = buyer.byerName;
//            if ( buyersToFruits.get( name ) == null )
//            {
//                HashMap<Sort, Integer> fruitMap = new HashMap<>();
//                fruitMap.put( buyer.type, buyer.count );
//                buyersToFruits.put( buyer.byerName, fruitMap );
//            }
//            else
//            {
//                HashMap<Sort, Integer> fruitMap = buyersToFruits.get( name );
//                fruitMap.put( buyer.type, buyer.count );
//            }
//        }
//
//        return buyersToFruits;
    }
    
    private LocalDateTime dateToLocalDate (Date date)
    {
        String[] dateStr = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( date ).split( "\\s" );
        return LocalDateTime.parse( dateStr[0] + "T" + dateStr[1] + ".000" );
    }
    
    private void initStorage () throws IOException
    {
        File storageFile = new File( storageFileName );
        InputStream targetStream = new FileInputStream( storageFile );
        
        String json;
        
        try ( final InputStreamReader reader = new InputStreamReader( targetStream ) ) {
            json = CharStreams.toString( reader );
        }
        
        Gson gson = new GsonBuilder().create();
        
        storage = gson.fromJson( json, Groccery.Storage.class );
        
        storage.moneyBalance.lazySet( storage.storedBalance );
    }
    
    /**
     * пополнить базу фруктами
     */
    private void fruitsToDB (ArrayList<HashMap<String, String>> fruitList)
    {
        fruitList.stream().map( Fruit::produce ).forEach( storage::addFruit );
    }
    
    /**
     * Скинуть базу в файл
     */
    private void saveStorage ()
    {
        storage.storedBalance = storage.moneyBalance.get();
        
        String s = new Gson().toJson( storage );
        
        BufferedWriter writer;
        
        try {
            FileWriter fileWriter = new FileWriter( storageFileName );
            
            writer = new BufferedWriter( fileWriter );
            
            writer.write( s );
            
            writer.flush();
            
            writer.close();
            
        }
        catch ( IOException e ) {
            
            e.printStackTrace();
        }
    }
    
    private static boolean isFruitFreshToDate (Fruit fruit, LocalDateTime date, Sort sort)
    {
        return fruit.getSort().equals( sort ) &&
               fruit.getDeliveryDate().plusDays( fruit.getShelfLive() ).compareTo( date ) > 0;
    }

//    private void setFruitCount(String sort, int count)
//    {
//        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
//        HashMap<String, String> map = new HashMap<>();
//        map.put( "count", count + "" );
//        storage.put( sort + "Count", list );
//    }
    
    private static class Storage {
        @SerializedName("Fruits")
        private LinkedList<Fruit> fruits;
        
        @SerializedName("storedBalance")
        Double storedBalance;
        
        AtomicDouble moneyBalance;
        
        public Storage ()
        {
            moneyBalance = new AtomicDouble( 0 );
        }
        
        public LinkedList<Fruit> getFruits ()
        {
            return fruits;
        }
        
        public Double getMoneyBalance ()
        {
            return moneyBalance.get();
        }
        
        void addFruit (Fruit fruit)
        {
            fruits.add( fruit );
        }
        
        void withdrawFruit (Sort sort, int count, LocalDateTime date)
        {
            fruits.parallelStream()
                  .filter( fruit -> isFruitFreshToDate( fruit, date, sort ) )
                  .limit( count )
                  .forEach( this::removeAndInbalance );
        }
        
        void removeAndInbalance (Fruit fruit)
        {
            
            moneyBalance.addAndGet( fruit.getPrice() );
            fruits.remove( fruit );
        }
        
        @Override
        public String toString ()
        {
            StringJoiner joiner = new StringJoiner( "\n" );
            joiner.add( "Storage{" );
            
            fruits.stream().map( Fruit::toString ).forEach( i -> joiner.add( i ) );
            
            joiner.add( "}" );
            
            return joiner.toString();
        }
    }
    
    private static class Buyers {
        @SerializedName("clients")
        ArrayList<Buyer> buyers;
    }
    
    public class Buyer {
        @SerializedName("name")
        String byerName;
        
        @SerializedName("type")
        Sort type;
        
        @SerializedName("count")
        int count;
        
        public Sort getType ()
        {
            return type;
        }
        
        public String getByerName ()
        {
            return byerName;
        }
        
        public int getCount ()
        {
            return count;
        }
    }
    
}
