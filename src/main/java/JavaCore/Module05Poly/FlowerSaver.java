package JavaCore.Module05Poly;


import JavaCore.Module05Poly.Interface.Flower;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// todo singleton
public enum FlowerSaver
{
    
    ;
    
    public final FlowerSaver get(){
        return this;
    }
    // todo save bouket to file
    public static final void save (String path, Flower[] bouquet){
    
        String newline = System.getProperty("line.separator");
        
        String text = "";
        
        text += "asd" + newline + "asd";
    
        BufferedWriter bufferedWriter = null;
        
    
        try {
            bufferedWriter = new BufferedWriter( new FileWriter( new File( path ), false ) );
            bufferedWriter.write( text );
            bufferedWriter.flush();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
     
    }
    
    
}
