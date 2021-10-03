package me.billzo.billzovillagers.utils;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MeatManager {
  public ItemStack getVillagerMeat(boolean wanderingTrader) {
    return new ItemBuilder(Material.COOKED_BEEF)
        .setName("§d" + (wanderingTrader ? "Wandering Trader Meat" : "Villager Meat"))
        .setLore("§7" + (wanderingTrader ? "Wandering traders are turkey flavoured?" : "Villagers are chicken flavoured?"))
        .setKeyString("meat_type", "villager")
        .build();
  }

  public boolean isVillagerMeat(ItemStack item) {
    String meatType = item
        .getItemMeta()
        .getPersistentDataContainer()
        .get(new NamespacedKey(BillzoVillagers.getPlugin(), "meat_type"), PersistentDataType.STRING);
    return meatType.equals("villager");
  }
}
