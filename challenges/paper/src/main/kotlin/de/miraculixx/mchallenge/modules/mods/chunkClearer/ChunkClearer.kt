package de.miraculixx.mchallenge.modules.mods.chunkClearer

import de.miraculixx.challenge.api.modules.challenges.Challenge
import de.miraculixx.challenge.api.modules.challenges.Challenges
import de.miraculixx.challenge.api.settings.challenges
import de.miraculixx.challenge.api.settings.getSetting
import de.miraculixx.kpaper.event.listen
import de.miraculixx.kpaper.event.register
import de.miraculixx.kpaper.event.unregister
import de.miraculixx.kpaper.extensions.bukkit.allBlocks
import de.miraculixx.mchallenge.modules.spectator.Spectator
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack

class ChunkClearer : Challenge {
    private val breakAll: Boolean
    private val shouldBreak: Boolean
    private val bundle: Boolean
    private val blacklist = listOf(Material.END_PORTAL, Material.END_PORTAL_FRAME, Material.OBSIDIAN, Material.NETHER_PORTAL, Material.AIR, Material.BEDROCK)

    init {
        val settings = challenges.getSetting(Challenges.CHUNK_CLEARER).settings
        breakAll = settings["breakAll"]?.toBool()?.getValue() ?: true
        shouldBreak = settings["shouldBreak"]?.toBool()?.getValue() ?: true
        bundle = settings["bundle"]?.toBool()?.getValue() ?: true
    }

    override fun register() {
        onMove.register()
    }

    override fun unregister() {
        onMove.unregister()
    }

    private val onMove = listen<PlayerMoveEvent>(register = false) {
        val to = it.to
        val from = it.from
        val player = it.player
        if (Spectator.isSpectator(player.uniqueId) || player.gameMode == GameMode.SPECTATOR) return@listen
        if (from.x == to.x && from.y == to.x && from.z == to.z) return@listen
        if (!player.isOnGround) return@listen

        val chunk = to.chunk
        val tool = ItemStack(Material.DIAMOND_PICKAXE)
        val lower = it.to.clone().subtract(0.0, 1.0, 0.0)
        if (breakAll) {
            val lowerBlock = lower.block
            val lowerType = lowerBlock.type
            if (lowerType == Material.GLASS || blacklist.contains(lowerType)) return@listen
            val counter: MutableMap<Material, Int> = mutableMapOf()
            chunk.allBlocks.forEach { b ->
                val type = b.type
                if (blacklist.contains(type)) return@forEach
                if (shouldBreak) {
                    if (bundle) {
                        b.getDrops(tool).forEach { drop ->
                            counter[drop.type] = counter[drop.type]?.plus(1) ?: 1
                        }
                        b.type = Material.AIR
                    } else b.breakNaturally(tool)
                } else b.type = Material.AIR
            }
            val world = to.world
            if (bundle) {
                counter.forEach { (type, amount) ->
                    world.dropItem(it.to, ItemStack(type, amount))
                }
            }
            lowerBlock.type = Material.GLASS // Save from falling

        } else {
            val type = lower.block.type
            if (type == Material.GLASS || type.isAir) return@listen
            var counter = 0
            chunk.allBlocks.forEach { b ->
                if (b.type != type) return@forEach
                if (shouldBreak) {
                    if (bundle) {
                        b.type = Material.AIR
                        counter++
                    } else b.breakNaturally(tool)
                } else b.type = Material.AIR
            }
            if (bundle) {
                to.world.dropItem(it.to, ItemStack(type, counter))
            }
            lower.block.type = Material.GLASS // Save from falling
        }
    }

}