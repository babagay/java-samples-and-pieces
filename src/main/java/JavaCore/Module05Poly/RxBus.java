package JavaCore.Module05Poly;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

// https://blog.metova.com/how-to-use-rxjava-as-an-event-bus/

public class RxBus {
    
    private static final RxBus INSTANCE = new RxBus();
    
//    private final Subject<Object, Object> mBusSubject = new <>( PublishSubject.create());
    
    public static RxBus getInstance() {
        return INSTANCE;
    }
}
