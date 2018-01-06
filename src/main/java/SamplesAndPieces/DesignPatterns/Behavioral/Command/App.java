package SamplesAndPieces.DesignPatterns.Behavioral.Command;

import SamplesAndPieces.DesignPatterns.Behavioral.Command.ConsoleCommand.InsertText;
import SamplesAndPieces.DesignPatterns.Behavioral.Command.Interface.Command;
import SamplesAndPieces.DesignPatterns.Behavioral.Command.Invoker.ConsoleCommandManager;
import SamplesAndPieces.DesignPatterns.Behavioral.Command.Receiver.Console;

import javax.xml.ws.spi.Invoker;

/**
 * todo http://www.quizful.net/post/command_lambda
 * https://github.com/domnikl/DesignPatternsPHP/tree/master/Behavioral/Command
 * http://designpatternsphp.readthedocs.io/ru/latest/Behavioral/Command/README.html
 */
public class App
{
    public static void main(String[] args)
    {
        // Объект, которым мы будем управлять с помощью команд
        Console receiver = new Console( "DESIGNSHOP" );

        // Команда 1
        InsertText insertTextCommand = new InsertText();
        insertTextCommand.setReceiver( receiver );
        insertTextCommand.setString( "pattern" );
        insertTextCommand.setPosition( 6 );

        // Команда 2
        InsertText insertTextCommand2 = new InsertText();
        insertTextCommand2.setReceiver( receiver );
        insertTextCommand2.setString( "prefix_" );
        insertTextCommand2.setPosition( 0 );

        // Создаём командира и передаем ему команды
        ConsoleCommandManager invoker = new ConsoleCommandManager();
        invoker.set( insertTextCommand );
        invoker.set( insertTextCommand2 );

        // Команда 3: создаётся через лямбду, без использования отдельного класса
        invoker.set( () -> {
            receiver.insertText( "-+", 6 );
            receiver.flush();
        } );

        // Запуск пакета команд
        invoker.run();
    }
}
