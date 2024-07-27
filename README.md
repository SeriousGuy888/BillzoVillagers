# VillagerMeat
delicious, [![forthebadge](https://forthebadge.com/images/badges/gluten-free.svg)](https://forthebadge.com) villager meat

## Features

### Villager Meat
- Villagers and wandering traders will drop meat on death
- The meat items (item name, lore, nutrition & saturation, custom model data) are configurable in `items.yml`

### Leashing
- You can right click villagers and wandering traders to leash them (makes transporting villagers much easier)
  - This, and leashing zombie villagers too (disabled by default) can be enabled or disabled in `config.yml`

### Naming
- Villagers get randomly assigned names (if spawned naturally or from a spawn egg)
- Villagers inherit the last name of a parent (if spawned as a result of breeding)
- Name lists can be configured in `villager_names.yml`

## Death Messages
- A death message will be broadcasted when a villager is killed
- Individual players may toggle seeing the death messages in their chat using the command `/toggle_villager_death_messages`
- Death messages have [DiscordSRV](https://www.spigotmc.org/resources/discordsrv.18494/) integration
  - The channel to which death messages should be sent can be configured in `config.yml`
