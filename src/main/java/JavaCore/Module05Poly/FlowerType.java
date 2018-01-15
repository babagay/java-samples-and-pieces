package JavaCore.Module05Poly;

public enum FlowerType {
    
    Chamomile,
    Tulip,
    Rose;
    
    FlowerType ()
    {
    
    }
    
    public static <T extends Enum<T>> T valueOf (Class<T> enumType, String name)
    {
        return Enum.valueOf( enumType, name );
    }
    
    
    public static String get (String type)
    {
        try {
            return valueOf( type ).toString();
        }
        catch ( Throwable e ) {
            return "";
        }
    }

//    String get(String type){
//
//        switch ( type ){
//            case "Rose":
//                return ROSE
//                        .toString();
//                break;
//            case "Chamomile":
//                return Chamomile.toString();
//                break;
//            case "Tulip":
//                return Tulip.toString();
//                break;
//        }
//
//
//    }
    
    @Override
    public String toString ()
    {
        return super.toString();
    }
}
