package me.billzo.billzovillagers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Listeners implements Listener {
  private final JavaPlugin plugin;
  public Listeners(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    FileConfiguration config = plugin.getConfig();
    if(!config.getBoolean("leashing.villager"))
      return;

    Entity entity = event.getRightClicked();
    if(!(entity instanceof Villager))
      return;

    Villager villager = (Villager) entity;
    if(!villager.isLeashed()) {
      HumanEntity player = event.getPlayer();
      ItemStack heldItem = player.getInventory().getItemInMainHand();
      if(!heldItem.isSimilar(new ItemStack(Material.LEAD)))
        return;

      event.setCancelled(true);
      villager.setLeashHolder(player);
      if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
        ItemStack newHeldItem = heldItem;
        newHeldItem.setAmount(newHeldItem.getAmount() - 1);
        if(newHeldItem.getAmount() <= 0)
          newHeldItem = new ItemStack(Material.AIR);

        player.getInventory().setItemInMainHand(newHeldItem);
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

    String[] firstNames = plugin.getConfig().getStringList("names.first").toArray(new String[0]);
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
}
