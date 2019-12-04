package SamplesAndPieces.DesognPatterns.Singleton;

import javax.inject.Singleton;

/**
 * On Demand Holder idiom - используется холдер-класс
 *
 *  Ленивая инициализация
 *  Высокая производительность
 *
 *  Не возможно использовать для non-static полей класс
 */
public class InnerClassIdiom
{
    public static class SingletonHolder {
        public static final InnerClassIdiom HOLDER_INSTANCE = new InnerClassIdiom();
    }

    public static InnerClassIdiom getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
