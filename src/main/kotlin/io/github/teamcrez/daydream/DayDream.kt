package io.github.teamcrez.daydream

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent
import io.github.teamcrez.daydream.wrapper.CommandObject
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

class DayDream(private val server: Server) {
    fun applyCommand(commandPrefix: String, commandRegister: HashMap<String, CommandObject>) {
        server.commandMap.apply {
            commandRegister.keys.forEach {
                val commandName = it.split(':')
                commandRegister[it]?.let { commandObject ->
                    if (commandName.size > 1) {
                        register(commandName[0], CommandWrapper(commandObject, commandName[1]))
                    } else {
                        register(commandPrefix, CommandWrapper(commandObject, commandName[0]))
                    }
                }
            }

            commandRegister.clear()
        }
    }
}

private class CommandWrapper(private val commandObj: CommandObject, name: String) : BukkitCommand(name) {
    override fun getDescription() = commandObj.description
    override fun getPermission() = commandObj.permission
    override fun getAliases() = commandObj.aliases
    override fun getLabel() = commandObj.label
    override fun getUsage() = commandObj.usage

    override fun execute(sender: CommandSender, label: String, args: Array<out String>) =
        commandObj.execute(CommandExecuteEvent(sender, label, args))
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        return commandObj.tabComplete(TabCompleteEvent(sender, alias, args)) ?: mutableListOf()
    }
}