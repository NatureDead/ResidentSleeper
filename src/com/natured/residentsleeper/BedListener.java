package com.natured.residentsleeper;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedListener implements Listener {

    private PluginContext pluginContext;

    public BedListener(PluginContext pluginContext) {

        this.pluginContext = pluginContext;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {

        PlayerBedEnterEvent.BedEnterResult bedEnterResult = event.getBedEnterResult();
        if (bedEnterResult != PlayerBedEnterEvent.BedEnterResult.OK) return;

        sendCancelMessage(event.getPlayer());

        if (pluginContext.getWorld().getPlayers().size() > 1) {

            pluginContext.queueTask(() -> {

                // message "Good morning everyone."
                pluginContext.getWorld().setTime(0);
            }, 100);
        }
    }

    private void sendCancelMessage(Player player) {

        ComponentBuilder componentBuilder = new ComponentBuilder();
        componentBuilder.append(player.getDisplayName());
        componentBuilder.color(ChatColor.DARK_PURPLE);
        componentBuilder.append(" tries to sleep. ");

        TextComponent clickableMessage = new TextComponent("Click here to stop it.");
        clickableMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/residentsleeper"));
        clickableMessage.setUnderlined(true);

        componentBuilder.append(clickableMessage);

        // broadcast to all worlds...
        Bukkit.getServer().spigot().broadcast(componentBuilder.create());
    }
}