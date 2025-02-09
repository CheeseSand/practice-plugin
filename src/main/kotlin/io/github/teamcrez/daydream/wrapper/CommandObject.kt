package io.github.teamcrez.daydream.wrapper

import io.github.teamcrez.daydream.event.CommandExecuteEvent
import io.github.teamcrez.daydream.event.TabCompleteEvent

abstract class CommandObject {
    var aliases: List<String> = ArrayList()
    var description: String = ""
    var permission: String = ""
    var label: String = ""
    var usage: String = ""

    abstract fun execute(event: CommandExecuteEvent): Boolean
    abstract fun tabComplete(tabCompleteEvent: TabCompleteEvent): MutableList<String>?
}