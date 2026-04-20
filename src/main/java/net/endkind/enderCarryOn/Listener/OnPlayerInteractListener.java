package net.endkind.enderCarryOn.Listener;

import net.endkind.enderCarryOn.Keys;
import net.endkind.enderCarryOn.helpers.CarryHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnPlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if player is carrying something
        if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.CARRY, org.bukkit.persistence.PersistentDataType.BYTE)) {
            
            // If it's an entity
            if (item.getItemMeta().getPersistentDataContainer().has(Keys.ENTITY_DATA, org.bukkit.persistence.PersistentDataType.STRING)) {
                if (event.getAction().isRightClick() && event.getClickedBlock() != null) {
                    String snapshotData = item.getItemMeta().getPersistentDataContainer().get(Keys.ENTITY_DATA, org.bukkit.persistence.PersistentDataType.STRING);
                    EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(snapshotData);
                    
                    org.bukkit.Location location = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5, 0, 0.5);
                    snapshot.createEntity(location);
                    
                    player.getInventory().setItemInMainHand(null);
                    event.setCancelled(true);
                }
                return;
            }

            // Existing block carrying logic (already handled by isValidCarryAttempt returning false if holding item)
        }

        if (CarryHelper.isValidCarryAttempt(event)) {
            Block clickedBlock = event.getClickedBlock();

            ItemStack carryBlock = CarryHelper.getCarryBlock(clickedBlock);

            int selectedSlot = player.getInventory().getHeldItemSlot();
            player.getInventory().setItem(selectedSlot, carryBlock);
            clickedBlock.setType(Material.AIR);
        }
    }
}
