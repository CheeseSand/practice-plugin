package io.github.cheesesandteam.practice

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class OnEvent : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        if (!event.player.isOp) {
            event.player.gameMode = GameMode.CREATIVE
            event.player.sendMessage(Component.text("치샌이 주는 선물~ (겜모임)"))
        }
        event.player.sendMessage(Component.text("text"))
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player

        if (!player.isSneaking) {
            return;
        }

        val customInventory: Inventory = Bukkit.createInventory(null, 27, Component.text("커스텀 인벤토리"))

        customInventory.setItem(13, ItemStack(Material.DIAMOND, 1))

        var item = ItemStack(Material.BOOK, 1)
        var itemMeta = item.itemMeta
        itemMeta.displayName(Component.text("명령어들 목록"))
        item.itemMeta = itemMeta

        customInventory.setItem(4, item)

        item = ItemStack(Material.PAPER, 1)
        itemMeta = item.itemMeta
        itemMeta.displayName(Component.text("/practice"))
        item.itemMeta = itemMeta

        customInventory.setItem(9, item)

        itemMeta.displayName(Component.text("/sendmsg"))
        item.itemMeta = itemMeta

        customInventory.setItem(10, item)

        player.openInventory(customInventory)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.view.title

        // 커스텀 인벤토리의 제목을 확인하여 해당 인벤토리인지 판별
        if (inventory == "커스텀 인벤토리") {
            // 클릭 이벤트 취소하여 아이템 추가/제거 방지
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message

        // 특정 조건에 따라 채팅 입력 처리
        if (message.equals("특정 명령어", ignoreCase = true)) {
            // 플레이어에게 안내 메시지 전송
            player.sendMessage("채팅을 입력해주세요:")

            // 채팅 이벤트 취소
            event.isCancelled = true

            // 플레이어의 채팅 입력을 기다리는 로직 추가
            // 예: 일정 시간 후 자동으로 입력 처리하거나, 다른 방법으로 입력 받기
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val randomInt = (1..10).random()

        if(randomInt <= 3) {
            event.isCancelled = true
            event.player.sendMessage(
                Component.text("블럭 보호 단체에 의해 파괴 실패! (30% 확률)")
                    .color(TextColor.color(200, 0, 0))
            )
            return
        }

        if(randomInt <= 5){
            event.isCancelled = true
            event.player.damage(1.0)

            val item = ItemStack(Material.BROWN_DYE, 1)
            val itemMeta = item.itemMeta
            itemMeta.displayName(Component.text("똥"))
            item.itemMeta = itemMeta

            event.player.inventory.addItem(item)

            event.player.sendMessage(
                Component.text("블럭 보호 단체가 당신에게 똥을 던졌습니다! (20% 확률)")
                    .color(TextColor.color(200, 0, 0))
            )
            return
        }

        event.player.sendMessage(
            Component.text("블럭도 생명입니다!")
                .color(TextColor.color(255, 255, 255))
        )
    }
}