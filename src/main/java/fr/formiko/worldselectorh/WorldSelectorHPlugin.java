package fr.formiko.worldselectorh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class WorldSelectorHPlugin extends JavaPlugin {
    private static Selector selector;
    private static final String SELECTOR_FILE_PATH = "plugins/WorldSelectorH/selector.data";
    @Override
    public void onEnable() {
        this.getCommand("select").setExecutor(new fr.formiko.worldselectorh.commands.SelectCommand());
        if (new File(SELECTOR_FILE_PATH).exists()) {
            loadSelector();
        }
    }
    @Override
    public void onDisable() { saveSelector(); }

    public static Selector getSelector() { return selector; }
    public static void setSelector(Selector selector) { WorldSelectorHPlugin.selector = selector; }
    public static void resetSelector() { setSelector(new Selector(getSelector())); }


    public boolean saveSelector() {
        new File(SELECTOR_FILE_PATH).getParentFile().mkdirs();
        try (BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(SELECTOR_FILE_PATH)))) {
            out.writeObject(selector);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean loadSelector() {
        try (BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(SELECTOR_FILE_PATH)))) {
            selector = (Selector) in.readObject();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
