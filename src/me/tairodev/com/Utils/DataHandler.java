package me.tairodev.com.Utils;

import me.tairodev.com.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DataHandler {

    private Main plugin;

    public void Files(){
        plugin = Main.getInstance();
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        File playerData = new File(plugin.getDataFolder(), "player-data.yml");

        if(!playerData.exists()){
            try(InputStream in = plugin.getResourceAsStream("player-data.yml")){
                Files.copy(in, playerData.toPath());
            }catch(IOException x){
                x.printStackTrace();
            }
        }

        File messages = new File(plugin.getDataFolder(), "messages.yml");

        if(!messages.exists()){
            try(InputStream in = plugin.getResourceAsStream("messages.yml")){
                Files.copy(in, messages.toPath());
            }catch (IOException x){
                x.printStackTrace();
            }
        }

        File config = new File(plugin.getDataFolder(), "config.yml");

        if(!config.exists()){
            try(InputStream in = plugin.getResourceAsStream("config.yml")){
                Files.copy(in, config.toPath());
            }catch(IOException x){
                x.printStackTrace();
            }
        }

        File whitelist = new File(plugin.getDataFolder(), "whitelist.yml");

        if(!whitelist.exists()){
            try(InputStream in = plugin.getResourceAsStream("whitelist.yml")){
                Files.copy(in, whitelist.toPath());
            }catch(IOException x){
                x.printStackTrace();
            }
        }

        File blockedcmds = new File(plugin.getDataFolder(), "blockedcmds.yml");

        if(!blockedcmds.exists()){
            try(InputStream in = plugin.getResourceAsStream("blockedcmds.yml")){
                Files.copy(in, blockedcmds.toPath());
            }catch (IOException x){
                x.printStackTrace();
            }
        }

    }

}
