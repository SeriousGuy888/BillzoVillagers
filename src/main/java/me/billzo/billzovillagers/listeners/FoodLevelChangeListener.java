package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.BillzoVillagers;
import me.billzo.billzovillagers.utils.MeatManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class FoodLevelChangeListener implements Listener {
  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    Player player = (Player) event.getEntity();
    ItemStack item = event.getItem();
    if(item == null)
      return;

    if(new MeatManager().isVillagerMeat(item)) {
      event.setCancelled(true);
      item.setAmount(item.getAmount() - 1);
      player.setFoodLevel(Math.min(player.getFoodLevel() + 10, 20)); // steak restores 8
      player.setSaturation(Math.min(player.getSaturation() + 14, player.getFoodLevel())); // steak restores 12.8
    }
  }
}