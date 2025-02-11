package io.github.cheesesandteam.practice

import io.github.cheesesandteam.practice.commands.DataTestCommand
import io.github.cheesesandteam.practice.commands.InvTestCommand
import io.github.cheesesandteam.practice.commands.PracticeCommand
import io.github.cheesesandteam.practice.commands.SendMsgCommand
import io.github.teamcrez.daydream.DayDream
import io.github.teamcrez.daydream.wrapper.CommandObject
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class PracticePlugin : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.apply {
            this.registerEvents(OnEvent(), this@PracticePlugin)
        }

        val commandMap = HashMap<String, CommandObject>()
        commandMap["practice"] = PracticeCommand()
        commandMap["sendmsg"] = SendMsgCommand()
        commandMap["invtest"] = InvTestCommand()
        commandMap["datatest"] = DataTestCommand(getPlugin(this::class.java))

        val daydream = DayDream(server)
        daydream.applyCommand("practice", commandMap)
        daydream.applyCommand("sendmsg", commandMap)
        daydream.applyCommand("invtest", commandMap)
        daydream.applyCommand("datatest", commandMap)

        saveDefaultConfig()

        logger.warning("test text (it`s not warning)")
        logger.severe("test text (it`s not severe)")

        logger.info("PracticePlugin Enabled!")
    }

    override fun onDisable() {
        logger.info("PracticePlugin Disabled!")
    }
}