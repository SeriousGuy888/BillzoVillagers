package io.github.seriousguy888.villagermeat.listeners;

import io.github.seriousguy888.villagermeat.utils.VillagerNamer;
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

    new VillagerNamer().nameBredVillager(child, mother, father);
  }
}