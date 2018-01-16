 ### FlowerLoader
 
 [singleton]
 * https://habrahabr.ru/post/129494/
 * http://www.mantonov.com/2011/02/4-thread-safe-java.html
   
 [Вложенные классы]
 * https://juja.com.ua/java/inner-and-nested-classes/
 
 [Функциональные интерфейсы]
 * https://javanerd.ru/%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D1%8B-java/%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5-%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81%D1%8B-%D0%B2-java-8/
 * https://jsehelper.blogspot.com/2016/05/java-8-1.html
 * http://javanese.online/%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D1%8B_JVM-%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F/%D0%9E%D0%9E%D0%9F/%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81%D1%8B/
 * https://metanit.com/java/tutorial/9.3.php
  
 todo: проверить варианты d simpleGenerator():
 * Object[] d = flo.parallelStream().collect( Collectors.toCollection( ArrayList::new ) ).toArray();
 * Object[] a = flo.toArray();
 
 
 ### FlowerSaver
 синглтон-фабрика, для хранения одного единственного элемента
 https://habrahabr.ru/post/321152/
  
 * todo сделать, чтобы bufferedWriter отпускал файл (lock?) 
 
 ### FlowerStore
 * sellSequence() fixme: не работает конструкция  case FlowerType.get( ROSE )
 * Если bouquet.txt отсутсвует, происходит отвал 
    из-за того, что FlowerLoader.load начинает работу, когда bouquet.txt еще занят.
 
 ### RxBus
 
 ### FlowerStoreTestNG
 * flowerLoaderTest отваливается, если файл bouquet.txt не существует
 