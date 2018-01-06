package SamplesAndPieces.DesignPatterns.Behavioral.Command.Receiver;

public class Console
{
    private String text;

    public Console(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public void insertText(String str, Integer pos)
    {
        String start = text.substring( 0, pos );
        String tail = text.substring( pos, text.length() );

        text = start + str + tail;
    }

    public void flush()
    {
        System.out.println(text);

//        text = "";
    }

    @Override
    public String toString()
    {
        return "Console{" +
                "text='" + text + '\'' +
                '}';
    }
}
