package JavaCore.Module07;

public enum Sort {
    
    APPLE {
        @Override
        public String toString ()
        {
            return "Apple";
        }
    },
    
    BANANA {
        @Override
        public String toString ()
        {
            return "Banana";
        }
    };
    
    Sort(){
    
    }
}
