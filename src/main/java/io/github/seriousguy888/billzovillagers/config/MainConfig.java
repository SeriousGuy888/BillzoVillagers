package io.github.seriousguy888.billzovillagers.config;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;

import java.io.FileNotFoundException;
import java.util.List;

public class MainConfig extends ConfigReader {
    public MainConfig(BillzoVillagers plugin, String name) throws FileNotFoundException {
        super(plugin, name, true);
    }

    public boolean getMiddleNamesEnabled() {
        return config.getBoolean("naming.middle_names.enabled");
    }

    public double getMiddleNamesChancePercentage() {
        return config.getDouble("naming.middle_names.chance_percentage");
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

    public void attemptMigration(NameListConfig nameListConfig) {
        if(!(getFirstNames().isEmpty() && getLastNames().isEmpty())) {
            plugin.getLogger().info("Name list present in main config. Attempting to migrate to new location...");

            boolean backupSuccessful = this.saveBackupCopy();
            if (!backupSuccessful) {
                plugin.getLogger().warning("Backup failed; aborting migration.");
                return;
            }

            List<String> firsts = getFirstNames();
            List<String> lasts = getLastNames();

            boolean success = nameListConfig.migrateFromMainConfig(List.of(firsts, lasts));

            if(success) {
                config.set("names.first", null);
                config.set("names.last", null);
                saveToDisk();

                plugin.getLogger().info("Removed name list from main config file.");
            }
        }
    }

    private List<String> getFirstNames() {
        return config.getStringList("names.first");
    }

    private List<String> getLastNames() {
        return config.getStringList("names.last");
    }
}
