package net.endkind.enderCarryOn;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Keys {
    public static final NamespacedKey CARRY = new NamespacedKey("endercarryon", "carry");
    public static final NamespacedKey ENTITY_DATA = new NamespacedKey("endercarryon", "entity_data");
    
    // CustomModelData IDs for 1.20.6
    public static final int CHEST_ID = 1001;
    public static final int TRAPPED_CHEST_ID = 1002;
    public static final int ENDER_CHEST_ID = 1003;
    public static final int BARREL_ID = 1004;
    public static final int ENTITY_ID = 2000;

    private Keys() {}

    public static int getCustomModelData(Material material) {
        return switch (material) {
            case Material.CHEST -> CHEST_ID;
            case Material.TRAPPED_CHEST -> TRAPPED_CHEST_ID;
            case Material.ENDER_CHEST -> ENDER_CHEST_ID;
            case Material.BARREL -> BARREL_ID;
            default -> 0;
        };
    }
}
