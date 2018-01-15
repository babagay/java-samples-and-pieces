package JavaCore.Module05Poly;

public enum FlowerType {

    ROSE("Rose"),

    CHAMOMILE("Chamomile"){},

    TULIP("Tulip"){};

    private final String flower;

    FlowerType (String flower)
    {
        this.flower = flower;
    }
    
//    public static <T extends Enum<T>> T valueOf (Class<T> enumType, String name)
//    {
//        return Enum.valueOf( enumType, name );
//    }

    public static String get (FlowerType type)
    {
        try {
            return type.flower;
        }
        catch ( Throwable e ) {
            return "";
        }
    }


//    public static String qoo (String type){
//        FlowerType d = FlowerType.valueOf( FlowerType.class, "Rose" );
//        return d.toString();
//    }

//    String getInstance(String type){
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
