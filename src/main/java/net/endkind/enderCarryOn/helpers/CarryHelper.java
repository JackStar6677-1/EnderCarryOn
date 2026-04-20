package net.endkind.enderCarryOn.helpers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.endkind.enderCarryOn.EnderCarryOn;
import net.endkind.enderCarryOn.Keys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class CarryHelper {
    private static final UUID modifierId = UUID.fromString("6f0e34c0-0b6b-4b1a-8b1a-0b6b4b1a8b1a");
    private static final AttributeModifier attributeModifier = new AttributeModifier(modifierId, "endercarryon_speed", -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1);

    private static final List<Material> allowedCarryBlocks = new ArrayList<>(Arrays.asList(
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.ENDER_CHEST,
            Material.BARREL
    ));

    public static boolean isValidCarryAttempt(PlayerInteractEvent event) {
        Block target = event.getClickedBlock();
        Player player = event.getPlayer();

        return target != null &&
                isMainHandEmpty(player) &&
                player.isSneaking() &&
                isAllowedCarryTarget(target) &&
                canBreakBlock(player, target);
    }

    public static ItemStack getCarryBlock(Block block) {
        ItemStack item = new ItemStack(block.getType());
        ItemMeta itemMeta = item.getItemMeta();
        BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;

        int customModelData = Keys.getCustomModelData(block.getType());

        blockStateMeta.setCustomModelData(customModelData);
        blockStateMeta.getPersistentDataContainer().set(Keys.CARRY, PersistentDataType.BYTE, (byte) 1);
        blockStateMeta.setBlockState(block.getState());

        item.setItemMeta(blockStateMeta);

        return setAttributeModifier(item, Attribute.GENERIC_MOVEMENT_SPEED, attributeModifier);
    }

    public static ItemStack getCarryEntityItem(org.bukkit.entity.Entity entity) {
        // Create an item representating the entity (using BARRIER or similar as base, we'll use CHEST and CMD 2000)
        ItemStack item = new ItemStack(org.bukkit.Material.CHEST);
        ItemMeta itemMeta = item.getItemMeta();

        // Save EntitySnapshot
        org.bukkit.entity.EntitySnapshot snapshot = entity.createSnapshot();
        String snapshotData = snapshot.getAsString();

        itemMeta.setCustomModelData(Keys.ENTITY_ID);
        itemMeta.getPersistentDataContainer().set(Keys.CARRY, org.bukkit.persistence.PersistentDataType.BYTE, (byte) 1);
        itemMeta.getPersistentDataContainer().set(Keys.ENTITY_DATA, org.bukkit.persistence.PersistentDataType.STRING, snapshotData);

        // Name of the entity
        String name = entity.getCustomName() != null ? entity.getCustomName() : entity.getType().getName();
        itemMeta.displayName(net.kyori.adventure.text.Component.text("Cargando: " + name)
                .color(net.kyori.adventure.text.format.NamedTextColor.GOLD));

        item.setItemMeta(itemMeta);

        return setAttributeModifier(item, Attribute.GENERIC_MOVEMENT_SPEED, attributeModifier);
    }

    public static boolean isAllowedEntity(org.bukkit.entity.Entity entity) {
        if (!(entity instanceof org.bukkit.entity.LivingEntity)) return false;
        
        org.bukkit.entity.EntityType type = entity.getType();
        
        // Hostile check per user request
        if (entity instanceof org.bukkit.entity.Monster) {
            return type == org.bukkit.entity.EntityType.ZOMBIE || type == org.bukkit.entity.EntityType.ENDERMITE;
        }
        
        // Passive/Neutral are allowed
        return true;
    }

    private static boolean isAllowedCarryTarget(Block block) {
        return allowedCarryBlocks.contains(block.getType());
    }

    private static boolean isMainHandEmpty(Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.AIR;
    }

    private static boolean canBreakBlock(Player player, Block target) {
        BlockBreakEvent breakEvent = new BlockBreakEvent(target, player);
        EnderCarryOn plugin = EnderCarryOn.getInstance();

        plugin.getServer().getPluginManager().callEvent(breakEvent);

        return !breakEvent.isCancelled();
    }

    private static ItemStack setAttributeModifier(ItemStack item, Attribute attribute, AttributeModifier modifier) {
        ItemMeta itemMeta = item.getItemMeta();
        Multimap<Attribute, AttributeModifier> itemAttributeModifiers = itemMeta.getAttributeModifiers();

        if (itemAttributeModifiers != null && itemAttributeModifiers.containsKey(attribute)) {
            ArrayListMultimap<Attribute, AttributeModifier> mutableModifiers = ArrayListMultimap.create(itemAttributeModifiers);
            mutableModifiers.put(attribute, modifier);
            itemMeta.setAttributeModifiers(mutableModifiers);
        } else {
            itemMeta.addAttributeModifier(attribute, modifier);
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    private CarryHelper() {}
}
