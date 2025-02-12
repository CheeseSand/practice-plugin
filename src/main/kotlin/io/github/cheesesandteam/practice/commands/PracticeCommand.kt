package io.github.cheesesandteam.practice.commands

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PracticeCommand : CommandObject() {
    override fun execute(event: CommandExecuteEvent): Boolean {
        if (event.args.size < 3) {
            event.sender.sendMessage(
                Component.text("오류: 커맨드의 인자 값의 갯수가 부족합니다")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        val targetPlayer: Player? = Bukkit.getPlayerExact(event.args[0])
        val material = Material.matchMaterial(event.args[1])
        val amount: Int? = event.args[2].toIntOrNull()

        if(targetPlayer == null) {
            event.sender.sendMessage(
                Component.text("오류: 해당 플레이어를 찾을 수 없습니다.")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        if(material == null) {
            event.sender.sendMessage(
                Component.text("오류: 아이템 이름이 잘못되었습니다.")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        if (amount == null) {
            event.sender.sendMessage(
                Component.text("오류: count 값이 숫자가 아닙니다")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        targetPlayer.inventory.addItem(ItemStack(material, amount))

        event.sender.sendMessage(
            Component.text("${event.args[0]}에게 ${event.args[1]} 아이템을 ${event.args[2]} 만큼 주었습니다")
                .color(TextColor.color(255, 255, 255))
        )

        return true
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if(tabCompleteEvent.args == null){
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.toMutableList()
        }

        val currentInput = tabCompleteEvent.args.lastOrNull() ?: ""

        if (tabCompleteEvent.args.size == 1) {
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.filter {
                it.startsWith(currentInput, ignoreCase = true)
            }.toMutableList()
        }

        if (tabCompleteEvent.args.size == 2) {
            return Material.entries.map {
                it.name
            }.filter {
                it.startsWith(currentInput, ignoreCase = true)
            }.toMutableList()
        }

        if (tabCompleteEvent.args.size == 3) {
            return mutableListOf("count")
        }

        return mutableListOf()
    }
}