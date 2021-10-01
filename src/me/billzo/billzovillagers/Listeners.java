package me.billzo.billzovillagers;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Listeners implements Listener {
  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    FileConfiguration config = BillzoVillagers.getPlugin().getConfig();

    Entity entity = event.getRightClicked();
    if((entity instanceof Villager && config.getBoolean("leashing.villager")) ||
        (entity instanceof ZombieVillager && config.getBoolean("leashing.zombie_villager")) ||
        (entity instanceof WanderingTrader && config.getBoolean("leashing.wandering_trader"))) {
      LivingEntity livingEntity = (LivingEntity) entity;
      if(!livingEntity.isLeashed()) {
        HumanEntity player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if(!heldItem.isSimilar(new ItemStack(Material.LEAD)))
          return;

        event.setCancelled(true);
        livingEntity.setLeashHolder(player);
        if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
          ItemStack newHeldItem = heldItem;
          newHeldItem.setAmount(newHeldItem.getAmount() - 1);
          if(newHeldItem.getAmount() <= 0)
            newHeldItem = new ItemStack(Material.AIR);

          player.getInventory().setItemInMainHand(newHeldItem);
        }
      }
    }
  }

  @EventHandler
  public void onEntityBreed(EntityBreedEvent event) {
    LivingEntity child = event.getEntity();
    LivingEntity mother = event.getMother();
    LivingEntity father = event.getFather();

    if(!(child instanceof Villager))
      return;
    if(mother.getCustomName() == null || father.getCustomName() == null)
      return;

    String[] firstNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.first").toArray(new String[0]);
    if(firstNames.length < 1)
      return;

    String childFirstName = firstNames[new Random().nextInt(firstNames.length)];

    String motherLastName = mother.getCustomName().substring(mother.getCustomName().lastIndexOf(" ") + 1);
    String fatherLastName = father.getCustomName().substring(father.getCustomName().lastIndexOf(" ") + 1);
    String childLastName;

    if(motherLastName.equals(fatherLastName)) {
      childLastName = String.format("%s-%s", motherLastName, motherLastName);
    } else {
      childLastName = new Random().nextInt(2) == 1 ? motherLastName : fatherLastName;
    }

    child.setCustomName(String.format("%s %s", childFirstName, childLastName));
  }

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
