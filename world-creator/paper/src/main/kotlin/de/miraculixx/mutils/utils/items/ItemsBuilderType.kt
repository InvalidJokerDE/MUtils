package de.miraculixx.mutils.utils.items

import de.miraculixx.kpaper.items.customModel
import de.miraculixx.kpaper.items.itemStack
import de.miraculixx.kpaper.items.meta
import de.miraculixx.kpaper.items.name
import de.miraculixx.mcore.gui.items.ItemProvider
import de.miraculixx.mvanilla.messages.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemsBuilderType : ItemProvider {
    private val infoLore = listOf(emptyComponent(), cmp("• ") + cmp("Info", cHighlight, underlined = true))

    override fun getItemList(from: Int, to: Int): List<ItemStack> {
        return listOf(
            itemStack(Material.BEETROOT_SEEDS) {
                meta<Any> {
                    customModel = 1
                    name = cmp(msgString("items.creator.worldSet.n"), cHighlight)
                    lore(infoLore + msgList("items.creator.worldSet.l") + listOf(emptyComponent(), msgClick + cmp("Select")))
                }
            },
            itemStack(Material.OAK_BUTTON) {
                meta<Any> {
                    customModel = 2
                    name = cmp(msgString("items.creator.singleWorld.n"), cHighlight)
                    lore(infoLore + msgList("items.creator.singleWorld.l") + listOf(emptyComponent(), msgClick + cmp("Select")))
                }
            }
        )
    }
}