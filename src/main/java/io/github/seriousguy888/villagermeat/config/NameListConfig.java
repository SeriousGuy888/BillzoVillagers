package io.github.seriousguy888.villagermeat.config;

import io.github.seriousguy888.villagermeat.Main;

import java.io.FileNotFoundException;
import java.util.List;

public class NameListConfig extends ConfigReader {
    public NameListConfig(Main plugin, String name) throws FileNotFoundException {
        super(plugin, name, true);
    }

    public boolean migrateFromMainConfig(List<String> personalNames, List<String> familyNames) {
        if (personalNames.isEmpty() || familyNames.isEmpty()) {
            plugin.getLogger().warning("Received invalid name list data to migrate.");
            return false;
        }

        config.set("personal_names", personalNames);
        config.set("family_names", familyNames);
        saveToDisk();

        plugin.getLogger().info("Migrated name list from main config file into name list file.");
        return true;
    }


    public List<String> getFirstNames() {
        return config.getStringList("personal_names");
    }

    public List<String> getLastNames() {
        return config.getStringList("family_names");
    }
}
