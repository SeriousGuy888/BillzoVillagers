package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Random;

public class EntityDeathListener implements Listener {
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if((entity instanceof Villager) || (entity instanceof WanderingTrader)) {
      boolean wanderingTrader = entity instanceof WanderingTrader;

      ItemStack itemStack = new ItemStack(Material.COOKED_BEEF);
      itemStack.setAmount(new Random().nextInt(3) + 1);

      ItemMeta itemMeta = itemStack.getItemMeta();
      if(itemMeta == null)
        return;
      itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + (wanderingTrader ? "Wandering Trader Meat" : "Villager Meat"));
      itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + (wanderingTrader ? "Wandering traders are turkey flavoured?" : "Villagers are chicken flavoured?")));

      PersistentDataContainer data = itemMeta.getPersistentDataContainer();
      data.set(new NamespacedKey(BillzoVillagers.getPlugin(), "meat_type"), PersistentDataType.STRING, "villager");

      itemStack.setItemMeta(itemMeta);

      entity.getWorld().dropItem(entity.getLocation(), itemStack);
    }
  }
}
