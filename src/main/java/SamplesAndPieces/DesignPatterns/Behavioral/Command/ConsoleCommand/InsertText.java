package SamplesAndPieces.DesignPatterns.Behavioral.Command.ConsoleCommand;

import SamplesAndPieces.DesignPatterns.Behavioral.Command.Interface.Command;
import SamplesAndPieces.DesignPatterns.Behavioral.Command.Receiver.Console;

public class InsertText implements Command
{
    private Console receiver;

    private Integer position;

    private String text;

    @Override
    public void execute()
    {
        receiver.insertText( text, position );

        receiver.flush();
    }

    public void setString(String str)
    {
        text = str;
    }

    public void setPosition(Integer pos)
    {
        position = pos;
    }


    public void setReceiver(Console receiver)
    {
        this.receiver = receiver;
    }
}
