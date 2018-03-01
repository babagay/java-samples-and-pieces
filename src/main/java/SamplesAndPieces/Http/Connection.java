package SamplesAndPieces.Http;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
    
    public static void main (String[] args) throws IOException
    {
        URL url = new URL( "ya.ru" );
    
        URLConnection conn = url.openConnection();
        
        conn.connect();
        conn.setRequestProperty( "method", "GET" ); // [?]
        
        InputStream inputStream = conn.getInputStream();
    
        InputStreamReader reader = new InputStreamReader( inputStream );
    
        // [A]
        BufferedInputStream bufferedStream = new BufferedInputStream( inputStream );
     
        // [B]
        BufferedReader bufferedReader = new BufferedReader( reader );
        
        int r = 0;
        while ( (r = bufferedReader.read()) != -1 ){
            System.out.println( (char)r );// cast to char is needed
        }
    }
}
