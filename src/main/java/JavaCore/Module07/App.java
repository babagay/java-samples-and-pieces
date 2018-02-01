package JavaCore.Module07;

import JavaCore.Module05Poly.Garden.Rose;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class App
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Groccery groccery = new Groccery();

        groccery.addFruits("");
    }
}
