package fr.formiko.worldselectorh;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldSelectorHPlugin extends JavaPlugin {
    private static Selector selector;
    @Override
    public void onEnable() {
        this.getCommand("select").setExecutor(new fr.formiko.worldselectorh.commands.SelectCommand());
        // TODO add start and pause commands
    }

    public static Selector getSelector() { return selector; }
    public static void setSelector(Selector selector) { WorldSelectorHPlugin.selector = selector; }
}
