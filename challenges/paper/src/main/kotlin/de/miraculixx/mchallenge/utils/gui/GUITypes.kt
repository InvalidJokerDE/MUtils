package de.miraculixx.mchallenge.utils.gui

import de.miraculixx.mvanilla.messages.cHighlight
import de.miraculixx.mvanilla.messages.cmp
import de.miraculixx.mvanilla.messages.plus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

enum class GUITypes(val title: Component) {
    CH_LIMITED_SKILLS(cmp("• ", NamedTextColor.DARK_GRAY) + cmp("Limited Skills", cHighlight)),

    CHALLENGE_SETTINGS(cmp("• ", NamedTextColor.DARK_GRAY) + cmp("Challenge Settings", cHighlight)),
    SPEC_SETTINGS(cmp("• ", NamedTextColor.DARK_GRAY) + cmp("Settings", cHighlight)),
    SPEC_PLAYER_OVERVIEW(cmp("• ", NamedTextColor.DARK_GRAY) + cmp("Online Player", cHighlight)),
    CHALLENGE_MENU(cmp("• ", NamedTextColor.DARK_GRAY) + cmp("Challenges", cHighlight));
}