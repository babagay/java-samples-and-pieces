package SamplesAndPieces.DesignPatterns.Behavioral.Command.Invoker;

import SamplesAndPieces.DesignPatterns.Behavioral.Command.Interface.Command;

import java.util.ArrayList;
import java.util.List;

public class ConsoleCommandManager
{

    private List list = new ArrayList<Command>();


    public void set(Command command)
    {
        list.add( command );
    }

    /**
     * [?] Следующий код не работает:
     *      for ( Command entry : list )
     */
    public void run()
    {
        for ( Object entry : list )
        {
            Command command = (Command) entry;
            command.execute();
        }
    }
}
