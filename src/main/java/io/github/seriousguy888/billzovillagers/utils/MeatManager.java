package io.github.seriousguy888.billzovillagers.utils;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
    ItemMeta meta = item.getItemMeta();
    if(meta == null)
      return false;

    String meatType = meta
        .getPersistentDataContainer()
        .get(new NamespacedKey(BillzoVillagers.getPlugin(), "meat_type"), PersistentDataType.STRING);

    if(meatType == null)
      return false;
    return meatType.equals("villager");
  }
}
