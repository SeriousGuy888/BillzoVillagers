package io.github.seriousguy888.billzovillagers.commands;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleVillagerDeathMessagesCommand implements CommandExecutor {
  BillzoVillagers plugin;
  public ToggleVillagerDeathMessagesCommand() {
    plugin = BillzoVillagers.getPlugin();
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender,
                           @NotNull Command command,
                           @NotNull String commandLabel,
                           String[] args) {
    if(!(sender instanceof Player player)) {
      sender.sendMessage("The console cannot use this command!");
      return true;
    }

    Boolean enabled = plugin.villagerDeathMessagesEnabled.get(player);

    player.sendMessage(ChatColor.YELLOW
        + "You will "
        + (enabled ? "no longer" : "now")
        + " see villager death messages in chat.");
    plugin.villagerDeathMessagesEnabled.put(player, !enabled);

    return true;
  }
}
