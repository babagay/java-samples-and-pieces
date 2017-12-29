package JavaCore.Module05OOP;


import java.util.HashMap;

public class AppState
{
    HashMap<String,Object> map;

    private AppState()
    {
        map = new HashMap<>(  );
    }

    private static AppState instance;

    public static AppState getInstance()
    {
        if(instance == null)
            instance = new AppState();

        return instance;
    }

    public void put(String key, Object val){
        map.put( key, val );
    }

    public Object get(String key)
    {
        return map.get( key );
    }

}
