package io.github.seriousguy888.billzovillagers.config;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;

import java.util.List;

public class MainConfig extends ConfigReader {
    public MainConfig(BillzoVillagers plugin, String name) {
        super(plugin, name, true);
    }

    public boolean getMiddleNamesEnabled() {
        return config.getBoolean("naming.middle_names.enabled");
    }

    public double getMiddleNamesChancePercentage() {
        return config.getDouble("naming.middle_names.chance_percentage");
    }

    public List<String> getFirstNames() {
        return config.getStringList("names.first");
    }

    public List<String> getLastNames() {
        return config.getStringList("names.last");
    }

    public int getFoodPoints() {
        return config.getInt("villagermeat.food_points");
    }

    public float getSaturationPoints() {
        return (float) config.getDouble("villagermeat.saturation_points");
    }

    public String getDiscordSrvChannelName() {
        return config.getString("discordsrv.channel_name", "");
    }

    public boolean canLeashVillager() {
        return config.getBoolean("leashing.villager", true);
    }

    public boolean canLeashWanderingTrader() {
        return config.getBoolean("leashing.wandering_trader", true);
    }

    public boolean canLeashZombieVillager() {
        return config.getBoolean("leashing.zombie_villager", false);
    }
}
