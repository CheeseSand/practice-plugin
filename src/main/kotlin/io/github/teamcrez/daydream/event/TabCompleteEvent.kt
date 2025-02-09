package io.github.teamcrez.daydream.event

import org.bukkit.command.CommandSender

data class TabCompleteEvent(val sender: CommandSender, val alias: String, val args: Array<out String>?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TabCompleteEvent

        if (sender != other.sender) return false
        if (alias != other.alias) return false
        if (args != null) {
            if (other.args == null) return false
            if (!args.contentEquals(other.args)) return false
        } else if (other.args != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sender.hashCode()
        result = 31 * result + alias.hashCode()
        result = 31 * result + (args?.contentHashCode() ?: 0)
        return result
    }
}