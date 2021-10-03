package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.utils.VillagerRegistrationUtil;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityBreedListener implements Listener {
  @EventHandler
  public void onEntityBreed(EntityBreedEvent event) {
    if(!(event.getEntity() instanceof Villager))
      return;

    Villager child = (Villager) event.getEntity();
    Villager mother = (Villager) event.getMother();
    Villager father = (Villager) event.getFather();

    new VillagerRegistrationUtil().nameBredVillager(child, mother, father);
  }
}