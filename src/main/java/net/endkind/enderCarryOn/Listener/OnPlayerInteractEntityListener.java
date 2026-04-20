package net.endkind.enderCarryOn.Listener;

import net.endkind.enderCarryOn.helpers.CarryHelper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnPlayerInteractEntityListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (player.isSneaking() && player.getInventory().getItemInMainHand().getType().isAir() && CarryHelper.isAllowedEntity(entity)) {
            ItemStack carryItem = CarryHelper.getCarryEntityItem(entity);
            int selectedSlot = player.getInventory().getHeldItemSlot();
            
            player.getInventory().setItem(selectedSlot, carryItem);
            entity.remove();
            
            // Cancel the interaction to prevent opening villager GUI etc.
            event.setCancelled(true);
        }
    }
}
