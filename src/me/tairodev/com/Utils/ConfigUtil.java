package me.tairodev.com.Utils;

import me.tairodev.com.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {

    private final Main plugin = Main.getInstance();

    private File playerData = new File(plugin.getDataFolder(), "player-data.yml");
    private Configuration configuration;

    private File messages = new File(plugin.getDataFolder(), "messages.yml");
    private Configuration messagesConfiguration;

    private File config = new File(plugin.getDataFolder(), "config.yml");
    private Configuration configConfiguration;

    private File whitelist = new File(plugin.getDataFolder(), "whitelist.yml");
    private Configuration whitelistConfiguration;

    public void savePlayerData() throws IOException{
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(plugin.getDataFolder(), "player-data.yml"));
    }

    public Configuration getData() throws IOException {
        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerData);
        return configuration;
    }

    public void saveMessages() throws IOException{
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesConfiguration, new File(plugin.getDataFolder(), "messages.yml"));
    }

    public Configuration getMessages() throws IOException{
        messagesConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messages);
        return messagesConfiguration;
    }

    public void saveConfig() throws IOException{
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configConfiguration, new File(plugin.getDataFolder(), "config.yml"));
    }

    public Configuration getConfig() throws IOException{
        configConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        return configConfiguration;
    }

    public void saveWhitelist() throws IOException{
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(whitelistConfiguration, new File(plugin.getDataFolder(), "whitelist.yml"));
    }

    public Configuration getWhitelist() throws IOException{
        whitelistConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(whitelist);
        return whitelistConfiguration;
    }

}