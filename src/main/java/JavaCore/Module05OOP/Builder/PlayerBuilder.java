package JavaCore.Module05OOP.Builder;

import JavaCore.Module05OOP.Factory.EnchancedFactory;
import JavaCore.Module05OOP.Factory.ExtraFactory;
import JavaCore.Module05OOP.Factory.PlayerFactory;
import JavaCore.Module05OOP.Factory.SimpleFactory;
import JavaCore.Module05OOP.Player.Player;

import java.util.HashMap;
import java.util.stream.Collectors;

// todo заинжектить PlayList - билдер создает его сам или принимает?

public class PlayerBuilder {

    final String PLAYER_GROUP_SIMPLE = "simple";
    final String PLAYER_GROUP_ENCHANCED = "enchanced";
    final String PLAYER_GROUP_EXTRA = "extra";

    private static PlayerFactory simpleFactory;
    private static PlayerFactory enchancedFactory;
    private static PlayerFactory extraFactory;

    private HashMap<String, HashMap<String, String>> allowedPlayerTypes;

    private String playerMnemonicType;

    public PlayerBuilder setMnemonicType(String playerMnemoType) throws Exception {
        playerMnemonicType = playerMnemoType;

        initTypes();

        if (getPlayerDetails(playerMnemoType) == null) {
            throw new Exception("Тип [" + playerMnemoType + "] не поддерживается");
        }

        return this;
    }

    /**
     * Create player by mnemonic type
     */
    public Player getPlayer() throws Exception {
        HashMap<String, String> playerDetails = getPlayerDetails(playerMnemonicType);

        Player player = null;

        switch (playerDetails.get("playerGroup")) {
            case PLAYER_GROUP_SIMPLE:
                player = getSimpleFactory().create(playerDetails.get("vendor"));
                break;
        }

        return player;
    }

    private void initTypes() {
        allowedPlayerTypes = new HashMap<>();

        HashMap<String, String> map = new HashMap<>();

        map.put("vendor", "Elenberg");
        map.put("playerGroup", PLAYER_GROUP_SIMPLE);
        map.put("OS", "Windows");
        allowedPlayerTypes.put("Elenberg", map);

        map = new HashMap<>();
        map.put("vendor", "Xiaomi");
        map.put("playerGroup", PLAYER_GROUP_SIMPLE);
        map.put("OS", "Android");
        allowedPlayerTypes.put("Xiaomi", map);
    }

    private HashMap<String, String> getPlayerDetails(String playerType) {
        HashMap<String, String> details = null;

        try {
            details = allowedPlayerTypes.entrySet().stream()
                    .filter(m -> m.getKey().equals(playerType))
                    .collect(Collectors.toList())
                    .get(0)
                    .getValue();
        } finally {
            return details;
        }
    }

    private PlayerFactory getSimpleFactory() {
        if (simpleFactory == null) {
            simpleFactory = new SimpleFactory();
        }

        return simpleFactory;
    }

    private PlayerFactory getEnchancedFactory() {
        if (enchancedFactory == null) {
            enchancedFactory = new EnchancedFactory();
        }

        return enchancedFactory;
    }

    private PlayerFactory getExtraFactory() {
        if (extraFactory == null) {
            extraFactory = new ExtraFactory();
        }

        return extraFactory;
    }
}
