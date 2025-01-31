package dev.namd.staffchat.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.namd.staffchat.ChatUtils;
import dev.namd.staffchat.Staffchat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("staffchat|schat|sc") // Annotation Command Framework
@CommandPermission("staffchat.use")
@Description("Use the staffchat")
public class StaffChatCommand extends BaseCommand {

    @Dependency
    private final Staffchat main = Staffchat.getInstance();

    @Default
    @CommandCompletion("@players")
    public void onDefault(@NotNull CommandSender sender, String[] args) {
        String name = sender instanceof Player ? ((TextComponent) ((Player) sender).displayName()).content() : "§cConsole";
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!main.isStaffchatEnabled(player)) {
                    main.enableStaffchat(player);
                    player.sendMessage(main.EnableMessage);
                } else {
                    main.disableStaffchat(player);
                    player.sendMessage(main.DisableMessage);
                }
            }
        }
        if (args.length > 0) {
            StringBuilder rawMsg = new StringBuilder();
            for (String word : args) {
                rawMsg.append(word).append(" ");
            }
            String unformattedMessage = rawMsg.toString();
            TextComponent formattedMsg = ChatUtils.colorizeAll(unformattedMessage);

            TextComponent message = Component.text(main.MessageFormat.content()
                    .replace("{PLAYER}", name)
                    .replace("{MESSAGE}", formattedMsg.content()));

            ChatUtils.sendSCMessage(message);
        }
    }
}
