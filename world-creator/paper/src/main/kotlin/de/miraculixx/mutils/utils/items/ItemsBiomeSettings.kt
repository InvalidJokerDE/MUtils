package de.miraculixx.mutils.utils.items

import de.miraculixx.kpaper.items.customModel
import de.miraculixx.kpaper.items.itemStack
import de.miraculixx.kpaper.items.meta
import de.miraculixx.kpaper.items.name
import de.miraculixx.challenge.api.data.BiomeProviderData
import de.miraculixx.mcore.gui.items.ItemProvider
import de.miraculixx.mcore.gui.items.skullTexture
import de.miraculixx.mutils.data.getIcon
import de.miraculixx.mvanilla.gui.Head64
import de.miraculixx.mvanilla.messages.*
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType

class ItemsBiomeSettings(private val biomeProviderData: BiomeProviderData): ItemProvider {
    private val settingLore = listOf(emptyComponent(), cmp("• ") + cmp("Settings", cHighlight, underlined = true))

    override fun getSlotMap(): Map<Int, ItemStack> {
        return buildMap {
            val gen = biomeProviderData.algorithm
            put(20, gen.getIcon(biomeProviderData.settings, 0))
            put(22, itemStack(Material.PLAYER_HEAD) {
                meta<Any> {
                    name = emptyComponent()
                }
                itemMeta = (itemMeta as SkullMeta).skullTexture(Head64.ARROW_RIGHT_WHITE.value)
            })

            var index = 1
            gen.settings.forEach { (settingIndex, setting) ->
                put(getSlot(index), itemStack(setting.getIcon()) {
                    meta<Any> {
                        customModel = 1
                        persistentDataContainer.set(NamespacedKey(namespace, "gui.biome.setting"), PersistentDataType.STRING, settingIndex.name)
                        name = cmp(msgString("items.algo.${setting.name}.n"), cHighlight)
                        lore(
                            msgList("items.algo.${setting.name}.l", inline = "<grey>") + settingLore + listOf(
                                cmp("   Value: ") + cmp(settingIndex.getString(biomeProviderData.settings), cHighlight),
                                emptyComponent()
                            ) + settingIndex.getClickLore()
                        )
                    }
                })
                index++
            }
            while (index < 7) {
                put(getSlot(index), itemStack(Material.BARRIER) {
                    meta<Any> {
                        customModel = index + 10
                        name = emptyComponent()
                    }
                })
                index++
            }
        }
    }

    private fun getSlot(index: Int): Int {
        return when (index) {
            1 -> 15
            2 -> 24
            3 -> 33
            4 -> 16
            5 -> 25
            else -> 34
        }
    }
}