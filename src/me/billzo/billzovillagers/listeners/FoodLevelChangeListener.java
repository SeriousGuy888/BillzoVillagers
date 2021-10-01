package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class FoodLevelChangeListener implements Listener {
  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    HumanEntity player = event.getEntity();
    ItemStack itemStack = event.getItem();
    if(itemStack == null)
      return;

    String meatType = itemStack
        .getItemMeta()
        .getPersistentDataContainer()
        .get(new NamespacedKey(BillzoVillagers.getPlugin(), "meat_type"), PersistentDataType.STRING);

    if(meatType.equals("villager")) {
      event.setCancelled(true);
      itemStack.setAmount(itemStack.getAmount() - 1);
      player.setFoodLevel(player.getFoodLevel() + 10); // steak restores 8
      player.setSaturation(Math.min(player.getSaturation() + 14, player.getFoodLevel())); // steak restores 12.8
    }
  }
}
