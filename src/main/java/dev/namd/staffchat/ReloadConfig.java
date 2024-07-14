package dev.namd.staffchat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("screloadconfig")
@CommandPermission("staffchat.reload")
public class ReloadConfig extends BaseCommand {

    public final Staffchat main = Staffchat.getInstance();

    @Default
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        ((Player) sender).sendMessage("§aReloading Config for Staffchat");
        main.updateConfig();
    }
}
