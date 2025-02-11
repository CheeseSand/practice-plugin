package io.github.cheesesandteam.practice.commands

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class InvTestCommand : CommandObject() {
    override fun execute(event: CommandExecuteEvent): Boolean {
        val customInventory: Inventory = Bukkit.createInventory(null, 27, Component.text("테스트 인벤토리"))
        Bukkit.getPlayerExact(event.sender.name)?.openInventory(customInventory)
        return true
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        return mutableListOf()
    }
}