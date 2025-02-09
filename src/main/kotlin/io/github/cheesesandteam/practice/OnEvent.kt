package io.github.cheesesandteam.practice

import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class OnEvent : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        event.player.sendMessage(Component.text("text"))
    }
}