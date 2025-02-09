package io.github.cheesesandteam.practice

import io.github.cheesesandteam.practice.commands.PracticeCommand
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

        val daydream = DayDream(server)
        daydream.applyCommand("practice", commandMap)

        logger.info("PracticePlugin Enabled!")
    }

    override fun onDisable() {
        logger.info("PracticePlugin Disabled!")
    }
}