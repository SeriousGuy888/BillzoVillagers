package io.github.seriousguy888.billzovillagers.listeners;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

public class DeathMessageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        if (!(victim instanceof Villager villager))
            return;
        if (event.getFinalDamage() < villager.getHealth())
            return;

        TranslatableComponent translatedDeathMessage = getDeathMessage(event);

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (BillzoVillagers.getPlugin().villagerDeathMessagesEnabled.get(player)) {
                player.spigot().sendMessage(translatedDeathMessage);
            }
        });

        TextChannel channel = BillzoVillagers.getPlugin().getDiscordChannel();
        if (channel != null) {
            Location location = villager.getLocation();
            String worldName = location.getWorld() != null
                    ? location.getWorld().getName()
                    : "unknown world";
            String locationFooter = "At "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ() + " in world "
                    + worldName.toUpperCase();

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(new Color(0))
                    .setDescription(translatedDeathMessage.toPlainText())
                    .setFooter(locationFooter)
                    .build();
            channel.sendMessageEmbeds(embed).queue();
        }
    }

    private TranslatableComponent getDeathMessage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        Entity victim = event.getEntity();
        Entity attacker = null;
        Projectile projectile = null;

        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            attacker = damageByEntityEvent.getDamager();

            if (attacker instanceof Projectile proj && proj.getShooter() != null) {
                projectile = proj;
                attacker = (Entity) proj.getShooter();
            }
        }


        BaseComponent victimName = victim.getCustomName() != null
                ? new net.md_5.bungee.api.chat.TextComponent(victim.getCustomName())
                : new TranslatableComponent(victim.getType().getTranslationKey());

        BaseComponent attackerName = getAttackerName(attacker, projectile);

        BaseComponent weaponName = null;
        if (attacker instanceof LivingEntity) {
            EntityEquipment equipment = ((LivingEntity) attacker).getEquipment();
            if (equipment != null) {
                ItemStack heldItem = equipment.getItemInMainHand();
                if (heldItem.hasItemMeta() && Objects.requireNonNull(heldItem.getItemMeta()).hasDisplayName()) {
                    weaponName = new net.md_5.bungee.api.chat.TextComponent("[" + heldItem.getItemMeta().getDisplayName() + "]");
                }
            }
        }

        TranslatableComponent message = new TranslatableComponent();
        message.setFallback("(death message provided invalid translation key!!!)" +
                "\n> VICTIM: " + victimName.toPlainText() +
                "\n> KILLER: " + (attackerName != null ? attackerName.toPlainText() : "None") +
                "\n> WEAPON: " + (weaponName != null ? weaponName.toPlainText() : "None") +
                "\n> CAUSE: " + cause.name()
        );

        // Some death message translation keys (eg: indirectMagic) are weird and don't take an extra .player
        // suffix even when another entity caused the death, despite the fact that most death messages do do this.
        boolean canAddDotPlayer = true;

        // Most death messages can only have either the suffix .player or the suffix .item, but
        // the explosion death message, can take .player.item. This covers for that edge case.
        boolean canHaveItemAfterPlayer = false;


        switch (cause) {
            case KILL -> message.setTranslate("death.attack.genericKill");
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                message.setTranslate("death.attack.player");
                canAddDotPlayer = false;
            }
            case WORLD_BORDER -> message.setTranslate("death.attack.outsideBorder");
            case SUFFOCATION -> message.setTranslate("death.attack.inWall");
            case FALL -> message.setTranslate("death.fell.accident.generic");
            case FLY_INTO_WALL -> message.setTranslate("death.attack.flyIntoWall");
            case FIRE -> message.setTranslate("death.attack.inFire");
            case FIRE_TICK -> message.setTranslate("death.attack.onFire");
            case LAVA -> message.setTranslate("death.attack.lava");
            case DROWNING -> message.setTranslate("death.attack.drown");
            case BLOCK_EXPLOSION -> {
                message.setTranslate("death.attack.explosion");
                canHaveItemAfterPlayer = true;
            }
            case ENTITY_EXPLOSION -> {
                message.setTranslate("death.attack.explosion");
                canHaveItemAfterPlayer = true;

                if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
                    Entity damager = damageByEntityEvent.getDamager();
                    if (damager instanceof Firework) {
                        message.setTranslate("death.attack.fireworks");
                        canHaveItemAfterPlayer = false;
                    }
                }
            }
            case VOID -> message.setTranslate("death.attack.outOfWorld");
            case LIGHTNING -> message.setTranslate("death.attack.lightningBolt");
            case STARVATION -> message.setTranslate("death.attack.starve");
            case WITHER -> message.setTranslate("death.attack.wither");
            case FALLING_BLOCK -> message.setTranslate("death.attack.anvil");
            case THORNS -> message.setTranslate("death.attack.thorns");
            case DRAGON_BREATH -> message.setTranslate("death.attack.dragonBreath");
            case HOT_FLOOR -> message.setTranslate("death.attack.hotFloor");
            case CRAMMING -> message.setTranslate("death.attack.cramming");
            case FREEZE -> message.setTranslate("death.attack.freeze");
            case SONIC_BOOM -> message.setTranslate("death.attack.sonic_boom");
            case CONTACT -> {
                if (event instanceof EntityDamageByBlockEvent damageByBlockEvent) {
                    Block block = damageByBlockEvent.getDamager();
                    if (block == null) {
                        break;
                    }


                    switch (block.getType()) {
                        case CACTUS -> message.setTranslate("death.attack.cactus");
                        case SWEET_BERRY_BUSH -> message.setTranslate("death.attack.sweetBerryBush");
                        case POINTED_DRIPSTONE -> message.setTranslate("death.attack.stalagmite");
                    }
                } else {
                    message.setTranslate("death.attack.genericKill");
                }
            }
            case PROJECTILE -> {
                if (projectile == null) {
                    message.setTranslate("death.attack.thrown");
                    break;
                }

                switch (projectile.getType()) {
                    case ARROW, SPECTRAL_ARROW -> message.setTranslate("death.attack.arrow");
                    case FIREBALL, SMALL_FIREBALL -> message.setTranslate("death.attack.fireball");
                    case TRIDENT -> message.setTranslate("death.attack.trident");
                    case WITHER_SKULL -> message.setTranslate("death.attack.witherSkull");
                    case LLAMA_SPIT -> message.setTranslate("death.attack.genericKill");
                    default -> message.setTranslate("death.attack.thrown");
                }
            }
            case MAGIC, POISON -> {
                if (attacker == null) {
                    message.setTranslate("death.attack.magic");
                } else {
                    message.setTranslate("death.attack.indirectMagic");
                    canAddDotPlayer = false;
                }
            }
            default -> message.setTranslate("death.attack.generic");
        }

        // Replace %1$s in death message with victim name
        message.addWith(victimName);

        boolean needsAttackerInMessage = attackerName != null;
        boolean needsWeaponInMessage = weaponName != null;

        if (needsAttackerInMessage) {
            // Replace %2$s in death message with victim name
            message.addWith(attackerName);

            // IF
            //     this death message can accept adding ".player", AND
            //     (EITHER this death message can further accept adding ".player.item"
            //          OR a weapon (and thus ".item") won't be added regardless, so it doesn't matter)
            if (canAddDotPlayer && (canHaveItemAfterPlayer || !needsWeaponInMessage)) {
                message.setTranslate(message.getTranslate() + ".player");
            }

            if (needsWeaponInMessage) {
                message.setTranslate(message.getTranslate() + ".item");
                message.addWith(weaponName);
                // Replace %3$s in death message with weapon name
            }
        }

        return message;
    }

    @Nullable
    private BaseComponent getAttackerName(Entity attacker, Projectile projectile) {
        BaseComponent attackerName;

        if (attacker != null) {
            if (attacker instanceof Player attackerP) {
                attackerName = new net.md_5.bungee.api.chat.TextComponent(attackerP.getName());
            } else {
                attackerName = attacker.getCustomName() != null
                        ? new TextComponent(attacker.getCustomName())
                        : new TranslatableComponent(attacker.getType().getTranslationKey());
            }
        } else {
            attackerName = projectile != null
                    ? new TranslatableComponent(projectile.getType().getTranslationKey())
                    : null;
        }

        return attackerName;
    }
}
