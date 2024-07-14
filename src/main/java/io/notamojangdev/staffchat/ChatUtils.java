package io.notamojangdev.staffchat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final char colorChar = LegacyComponentSerializer.SECTION_CHAR;

    public static TextComponent colorizeAll(final String message) {
        return colorCodes(translateHexColorCodes(message).content());
    }

    public static TextComponent colorCodes(final String message) {
        return Component.text(message.replaceAll("&([0-9a-fA-Fklmnor])", colorChar + "$1"));
    }

    public static TextComponent translateHexColorCodes(final String message) {

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return Component.text(matcher.appendTail(buffer).toString());
    }

    public static void sendSCMessage(TextComponent message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(Staffchat.getInstance().PermissionNode)) player.sendMessage(message);
        }
        Bukkit.getLogger().info(message.content());
    }

}
