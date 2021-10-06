package io.github.seriousguy888.billzovillagers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandVillager implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      Inventory inventory = player.getInventory();

      ItemStack villagerSpawnEgg = new ItemStack(Material.VILLAGER_SPAWN_EGG);
      inventory.addItem(villagerSpawnEgg);
      return true;
    }

    return false;
  }
}