package SamplesAndPieces.DesognPatterns.Singleton;

/**
 * Классическая реализация
 *
 *  Простая и прозрачная реализация
 *  Потокобезопасность
 *
 *  Не ленивая
 *
 *  Проблемы
 *      она не проверяет, существует ли один экземпляр на JVM, он лишь удостоверяется, что существует один экземпляр на classloader
 * 		если вы используете несколько загрузчиков класса или ваше приложение должно работать на сервере (где может быть запущено несколько экземпляров приложения в разных загрузчиках классов), то всё становится очень печально.
 * 		один из способов создать объект - сериализация/Десериализация
 * 		потокоНЕбезопасность (в случае добавления ленивой инициализации if (instance == null) instance = new Classic() )
 * 		Рефлексия позволяет обходить инкапсуляцию
 *
 * 	https://tproger.ru/translations/singleton-pitfalls/
 * 	https://habr.com/ru/post/129494/
 */
public class Classic
{
    private static final Classic instance = new Classic();

    private Classic(){}

    public Classic getInstance(){
        return instance;
    }
}
