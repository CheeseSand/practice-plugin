package io.github.cheesesandteam.practice.commands

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SendMsgCommand : CommandObject() {
    override fun execute(event: CommandExecuteEvent): Boolean {
        if (event.args.size < 2) {
            event.sender.sendMessage(
                Component.text("오류: 커맨드의 인자 값의 갯수가 부족합니다")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        val targetPlayer: Player? = event.sender.server.getPlayer(event.args[0])

        if (targetPlayer == null) {
            event.sender.sendMessage(
                Component.text("오류: 해당 플레이어를 찾을 수 없습니다.")
                    .color(TextColor.color(255, 0, 0))
            )

            return false
        }

        val message = event.args.drop(1).joinToString(" ")

        targetPlayer.sendMessage(
            Component.text(message)
                .color(TextColor.color(255, 255, 255))
        )

        return true
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args == null) {
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.toMutableList()
        }

        val currentInput = tabCompleteEvent.args.lastOrNull() ?: ""

        if (tabCompleteEvent.args.size <= 1) {
            return Bukkit.getOnlinePlayers().map {
                it.name
            }.filter {
                it.startsWith(currentInput, ignoreCase = true)
            }.toMutableList()
        }

        if (tabCompleteEvent.args.size == 2) {
            return mutableListOf("text")
        }

        return mutableListOf()
    }
}