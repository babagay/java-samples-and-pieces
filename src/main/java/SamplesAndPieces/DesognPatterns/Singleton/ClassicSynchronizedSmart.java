package SamplesAndPieces.DesognPatterns.Singleton;

/**
 * Double Checked Locking & volatile realization
 *
 * Ленивая инициализация
 * Высокая производительность
 *
 * volatile - потому что после выделения памяти и инициализации указателя на объект, но еще ДО того как он сконструирован,
 * другой поток может считать это поле instance, подумать, что оно пустое и создать вторую копию синглтна
 */
public class ClassicSynchronizedSmart
{
    private static volatile ClassicSynchronizedSmart instance;

    public static ClassicSynchronizedSmart getInstance()
    {
        ClassicSynchronizedSmart localInstance = instance;

        if (localInstance == null)
        {
            synchronized (ClassicSynchronizedSmart.class)
            {
                localInstance = instance;

                if (localInstance == null)
                {
                    instance = localInstance = new ClassicSynchronizedSmart();
                }
            }
        }

        return localInstance;
    }

}
