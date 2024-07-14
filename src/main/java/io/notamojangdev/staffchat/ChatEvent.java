package io.notamojangdev.staffchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ChatEvent implements Listener {

    private final Staffchat main;

    ChatEvent(Staffchat main) { this.main = main; }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncChatEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission(main.PermissionNode)) {
            return;
        }

        boolean isInverted = ((TextComponent) event.message()).content().startsWith("@");
        TextComponent message = (TextComponent) event.originalMessage();
        if (isInverted) message = Component.text(message.content().replaceFirst("@", ""));


        if (!main.isStaffchatEnabled(event.getPlayer())) {
            if (!isInverted) return;

            String formattedMsg = ChatUtils.colorizeAll(message.content()).content();
            event.setCancelled(true);
            TextComponent messageFormat = prepareMessage(player.getName(), formattedMsg);

            broadcastToStaff(messageFormat);

        } else {
            if (isInverted) {
                event.message(message);
                return;
            }

            String formattedMsg = ChatUtils.colorizeAll(message.content()).content();
            TextComponent messageFormat = prepareMessage(player.getName(), formattedMsg);
            event.setCancelled(true);


            broadcastToStaff(messageFormat);

        }

    }

    public void broadcastToStaff(TextComponent message) {
        ChatUtils.sendSCMessage(message);
    }

    public TextComponent prepareMessage(String name, String message) {
        return Component.text(main.MessageFormat.content()
                .replace("{PLAYER}", name)
                .replace("{MESSAGE}", message));
    }
}
