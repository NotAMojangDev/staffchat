package io.notamojangdev.staffchat;

import co.aikar.commands.PaperCommandManager;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Staffchat extends JavaPlugin {

    private static Staffchat Instance;
    private List<UUID> staffchatEnabled;
    public TextComponent EnableMessage;
    public TextComponent DisableMessage;
    public TextComponent MessageFormat;
    public String PermissionNode;

    public Staffchat() {
        Instance = this;
    }

    @Override
    public void onEnable() {
        PermissionNode = "staffchat.use";
        staffchatEnabled = new ArrayList<UUID>();

        this.saveDefaultConfig();
        updateConfig();

        Bukkit.getPluginManager().registerEvents(new ChatEvent(this), this);
        registerCommands();

    }

    public void updateConfig() {
        this.reloadConfig();
        YamlConfiguration Config = (YamlConfiguration) this.getConfig();

        String prefix = Config.getString("prefix");
        assert prefix != null;
        EnableMessage =
                ChatUtils.colorizeAll(
                    Objects.requireNonNull(Config.getString("enable-message"))
                            .replace("{PREFIX}", prefix)
                );
        DisableMessage =
                ChatUtils.colorizeAll(
                    Objects.requireNonNull(Config.getString("disable-message"))
                            .replace("{PREFIX}", prefix)
                );
        MessageFormat =
                ChatUtils.colorizeAll(
                    Objects.requireNonNull(Config.getString("message-format"))
                            .replace("{PREFIX}", prefix)
                );
    }

    public void enableStaffchat(Player player) {
        staffchatEnabled.add(player.getUniqueId());
    }

    public void disableStaffchat(Player player) {
        staffchatEnabled.remove(player.getUniqueId());
    }

    public boolean isStaffchatEnabled(Player player) {
        return staffchatEnabled.contains(player.getUniqueId());
    }

    public static Staffchat getInstance() {
        return Instance;
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new staffChatCommand());
        manager.registerCommand(new ReloadConfig());
    }

}
