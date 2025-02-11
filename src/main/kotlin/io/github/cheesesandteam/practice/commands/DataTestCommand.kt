package io.github.cheesesandteam.practice.commands

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DataTestCommand(private val plugin: JavaPlugin) : CommandObject() {
    override fun execute(event: CommandExecuteEvent): Boolean {
        // 명령어를 실행한 사람이 플레이어인지 확인
        val player: Player? = Bukkit.getPlayerExact(event.sender.name)
        if (player == null) {
            event.sender.sendMessage(Component.text("플레이어만 사용할 수 있습니다."))
            return true
        }

        val args = event.args
        if (args.isEmpty()) {
            event.sender.sendMessage(Component.text("Usage: /datatest <save|load> [내용]"))
            return true
        }

        when (args[0].lowercase()) {
            "save" -> {
                if (args.size < 2) {
                    event.sender.sendMessage(Component.text("저장할 내용을 입력해 주세요."))
                    return true
                }
                // 두 번째 인수부터 내용을 하나의 문자열로 합칩니다.
                val content = args.drop(1).joinToString(" ")
                // 저장 폴더 및 파일 생성
                val dataFolder = File(plugin.dataFolder, "datatest")
                if (!dataFolder.exists()) {
                    dataFolder.mkdirs()
                }
                val file = File(dataFolder, "${player.uniqueId}.dat")
                try {
                    ObjectOutputStream(FileOutputStream(file)).use { oos ->
                        oos.writeObject(content)
                    }
                    event.sender.sendMessage(Component.text("내용이 저장되었습니다."))
                } catch (e: Exception) {
                    e.printStackTrace()
                    event.sender.sendMessage(Component.text("내용 저장에 실패했습니다."))
                }
            }
            "load" -> {
                val dataFolder = File(plugin.dataFolder, "datatest")
                val file = File(dataFolder, "${player.uniqueId}.dat")
                if (!file.exists()) {
                    event.sender.sendMessage(Component.text("저장된 내용이 없습니다."))
                    return true
                }
                try {
                    ObjectInputStream(FileInputStream(file)).use { ois ->
                        val content = ois.readObject() as? String ?: ""
                        event.sender.sendMessage(Component.text("저장된 내용: $content"))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    event.sender.sendMessage(Component.text("내용 로드에 실패했습니다."))
                }
            }
            else -> {
                event.sender.sendMessage(Component.text("Usage: /datatest <save|load> [내용]"))
            }
        }
        return true
    }

    override fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String> {
        if (tabCompleteEvent.args == null || tabCompleteEvent.args.size <= 1){
            return mutableListOf("save", "load")
        }

        return mutableListOf()
    }
}
