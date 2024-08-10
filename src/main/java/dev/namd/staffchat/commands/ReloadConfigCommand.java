package dev.namd.staffchat.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.namd.staffchat.Staffchat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("screloadconfig")
@CommandPermission("staffchat.reload")
public class ReloadConfigCommand extends BaseCommand {

    public final Staffchat main = Staffchat.getInstance();

    @Default
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage("§aReloading Config for Staffchat");
        main.updateConfig();
    }
}
