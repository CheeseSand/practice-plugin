package io.github.cheesesandteam.practice.commands

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class PracticeCommand : CommandObject() {
    override fun execute(event: CommandExecuteEvent): Boolean {
        if (event.args.size > 2) {
            event.sender.server.getPlayerExact(event.args[0])?.inventory!!
                .addItem(ItemStack(Material.matchMaterial(event.args[1])!!, event.args[2].toInt()))

        } else {
            event.sender.sendMessage(
                Component.text("오류: 커맨드의 인자 값의 갯수가 부족합니다")
                    .color(TextColor.color(255, 0, 0))
            )
        }

        return true
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        return if (tabCompleteEvent.args == null || tabCompleteEvent.args.size <= 1) {
            Bukkit.getOnlinePlayers().map {
                it.name
            }.toMutableList()
        } else if (tabCompleteEvent.args.size == 2) {
            mutableListOf("Item")
        } else {
            mutableListOf()
        }
    }
}