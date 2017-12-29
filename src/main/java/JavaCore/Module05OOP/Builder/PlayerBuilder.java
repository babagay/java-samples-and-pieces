package JavaCore.Module05OOP.Builder;

import JavaCore.Module05OOP.Factory.EnchancedFactory;
import JavaCore.Module05OOP.Factory.ExtraFactory;
import JavaCore.Module05OOP.Factory.PlayerFactory;
import JavaCore.Module05OOP.Factory.SimpleFactory;
import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.PlayerExtra;

import java.util.HashMap;
import java.util.stream.Collectors;


public class PlayerBuilder
{
    final String PLAYER_GROUP_SIMPLE = "simple";
    final String PLAYER_GROUP_ENCHANCED = "enchanced";
    final String PLAYER_GROUP_EXTRA = "extra";

    private static PlayerFactory simpleFactory;
    private static PlayerFactory enchancedFactory;
    private static PlayerFactory extraFactory;

    private HashMap<String, HashMap<String, String>> allowedPlayerTypes;

    private String playerMnemonicType;

    private HashMap<String, Object> params = null;

    private static PlayerBuilder instance;

    public PlayerBuilder setMnemonicType(String playerMnemoType) throws Exception
    {
        playerMnemonicType = playerMnemoType;

        initTypes();

        if ( getPlayerDetails( playerMnemoType ) == null )
        {
            throw new Exception( "Тип [" + playerMnemoType + "] не поддерживается" );
        }

        return this;
    }

    public PlayerBuilder setParams( HashMap<String, Object> params)
    {
        this.params = params;

        return this;
    }

    // [!] Можно передавать параметр Class<P> type
    public static <P extends Player> P getPlayer (String playerMnemonicType)
    {
        if(instance == null)
            instance = new PlayerBuilder();

        try
        {
            return (P) instance.setMnemonicType( playerMnemonicType ).getPlayer();
        }
        catch ( Exception e )
        {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Create player by mnemonic type
     */
    public Player getPlayer() throws Exception
    {
        HashMap<String, String> playerDetails = getPlayerDetails( playerMnemonicType );

        Player player;

        switch ( playerDetails.get( "playerGroup" ) )
        {
            case PLAYER_GROUP_SIMPLE:
                if ( params == null )
                    player = getSimpleFactory().create( playerDetails.get( "vendor" ) );
                else
                    player = getSimpleFactory().create( playerDetails.get( "vendor" ), params );
                break;
            case PLAYER_GROUP_ENCHANCED:
                if ( params == null )
                    player = getEnchancedFactory().create( playerDetails.get( "vendor" ) );
                else
                    player = getEnchancedFactory().create( playerDetails.get( "vendor" ), params );
                break;
            case PLAYER_GROUP_EXTRA:
                if ( params == null )
                    player = getExtraFactory().create( playerDetails.get( "vendor" ) );
                else
                    player = getExtraFactory().create( playerDetails.get( "vendor" ), params );
                break;
            default:
                throw new Exception( "Недопустимая группа [" + playerDetails.get( "playerGroup" ) + "]" );
        }

        return player;
    }

    private void initTypes()
    {
        allowedPlayerTypes = new HashMap<>();

        HashMap<String, String> map = new HashMap<>();

        map.put( "vendor", "Elenberg" );
        map.put( "playerGroup", PLAYER_GROUP_SIMPLE );
        map.put( "OS", "Windows" );
        allowedPlayerTypes.put( "Elenberg", map );

        map = new HashMap<>();
        map.put( "vendor", "Xiaomi" );
        map.put( "playerGroup", PLAYER_GROUP_SIMPLE );
        map.put( "OS", "Android" );
        allowedPlayerTypes.put( "Xiaomi", map );

        map = new HashMap<>();
        map.put( "vendor", "Sony" );
        map.put( "playerGroup", PLAYER_GROUP_ENCHANCED );
        map.put( "OS", "Android" );
        allowedPlayerTypes.put( "Sony", map );

        map = new HashMap<>();
        map.put( "vendor", "LG" );
        map.put( "playerGroup", PLAYER_GROUP_ENCHANCED );
        map.put( "OS", "Android" );
        allowedPlayerTypes.put( "LG", map );

        map = new HashMap<>();
        map.put( "vendor", "Pioneer" );
        map.put( "playerGroup", PLAYER_GROUP_EXTRA );
        map.put( "OS", "Android" );
        allowedPlayerTypes.put( "Pioneer", map );

        map = new HashMap<>();
        map.put( "vendor", "Panasonic" );
        map.put( "playerGroup", PLAYER_GROUP_EXTRA );
        map.put( "OS", "Android" );
        allowedPlayerTypes.put( "Panasonic", map );

        map = new HashMap<>();
        map.put( "vendor", "Digital" );
        map.put( "playerGroup", PLAYER_GROUP_EXTRA );
        allowedPlayerTypes.put( "Digital", map );
    }

    private HashMap<String, String> getPlayerDetails(String playerType)
    {
        HashMap<String, String> details = null;

        try
        {
            details = allowedPlayerTypes.entrySet().stream()
                    .filter( m -> m.getKey().equals( playerType ) )
                    .collect( Collectors.toList() )
                    .get( 0 )
                    .getValue();
        }
        finally
        {
            return details;
        }
    }

    private PlayerFactory getSimpleFactory()
    {
        if ( simpleFactory == null )
        {
            simpleFactory = new SimpleFactory();
        }

        return simpleFactory;
    }

    private PlayerFactory getEnchancedFactory()
    {
        if ( enchancedFactory == null )
        {
            enchancedFactory = new EnchancedFactory<PlayerExtra>();
        }

        return enchancedFactory;
    }

    private PlayerFactory getExtraFactory()
    {
        if ( extraFactory == null )
        {
            extraFactory = new ExtraFactory<PlayerExtra>();
        }

        return extraFactory;
    }
}
