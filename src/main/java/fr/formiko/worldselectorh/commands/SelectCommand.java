package fr.formiko.worldselectorh.commands;

import fr.formiko.worldselectorh.Selector;
import fr.formiko.worldselectorh.WorldSelectorHPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SelectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 4) {
            WorldSelectorHPlugin.setSelector(new Selector(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                    Integer.parseInt(args[3])));
            sender.sendMessage("Select " + WorldSelectorHPlugin.getSelector().getBlocksCount() + " blocks.");
            return true;
        } else {
            sender.sendMessage("Usage: /select <x1> <z1> <x2> <z2>");
            return false;
        }
    }

}
