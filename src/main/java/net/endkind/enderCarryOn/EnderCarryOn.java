package net.endkind.enderCarryOn;

import net.endkind.enderCarryOn.Listener.*;
import net.endkind.enderCore.platform.papermc.EnderPlugin;

public final class EnderCarryOn extends EnderPlugin {
    private static EnderCarryOn instance;

    @Override
    public void onPluginEnable() {
        instance = this;

        registerListener(new OnInventoryClickListener());
        registerListener(new OnInventoryDragListener());
        registerListener(new OnPlayerDropItemListener());
        registerListener(new OnPlayerInteractListener());
        registerListener(new OnPlayerInteractEntityListener());
        registerListener(new OnPlayerItemHeldListener());

        registerBukkitCommand("endercarryon", new net.endkind.enderCarryOn.commands.HelpCommand());

        if (config.getBoolean("reset_walk_speed_on_join") || config.getBoolean("resource_pack.use")) {
            registerListener(new OnPlayerJoinListener(this));
        }
    }

    @Override
    public void onPluginDisable() {}

    @Override
    public void reload() {}

    public static EnderCarryOn getInstance() {
        return instance;
    }
}
