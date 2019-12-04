package SamplesAndPieces.DesognPatterns.Singleton;

/**
 * ленивая реализация
 * Низкая производительность (при каждом обращении отрабатывает synchronized, хотя, это нужно лишь на этапе создания)
 */
public class ClassicSynchronized
{
    private static ClassicSynchronized instance;

    private ClassicSynchronized(){}

    public static synchronized ClassicSynchronized getInstance(){
        if (instance == null)
            instance = new ClassicSynchronized();

        return instance;
    }
}

