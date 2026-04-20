package net.endkind.enderCarryOn.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(Component.text("----------------------------------------").color(NamedTextColor.GRAY));
        sender.sendMessage(Component.text("ENDER CARRY ON - GUÍA DE USO").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        sender.sendMessage(Component.text(""));
        sender.sendMessage(Component.text("1. Levantar Bloques:").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("   - Agáchate (Shift) y haz clic derecho con la mano vacía sobre un cofre o barril."));
        sender.sendMessage(Component.text(""));
        sender.sendMessage(Component.text("2. Levantar Entidades (Mobs):").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("   - Agáchate (Shift) y haz clic derecho con la mano vacía sobre un animal o aldeano."));
        sender.sendMessage(Component.text("   - Hostiles permitidos: Solo Zombies y Endermites."));
        sender.sendMessage(Component.text(""));
        sender.sendMessage(Component.text("3. Soltar:").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("   - Haz clic derecho sobre el suelo para colocar el bloque o la entidad."));
        sender.sendMessage(Component.text(""));
        sender.sendMessage(Component.text("Nota: Mientras llevas algo, caminarás más lento.").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
        sender.sendMessage(Component.text("----------------------------------------").color(NamedTextColor.GRAY));
        
        return true;
    }
}
