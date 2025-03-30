# Item Descriptions

<center text-align='center'>
<a href='https://modrinth.com/mod/pyrite/versions?l=fabric'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/pyrite/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg"></a>
<a href='https://modfest.net/vanity/bc25'><img height="56" src="https://raw.githubusercontent.com/worldwidepixel/badges/refs/heads/main/bc25/featured_in/cozy.svg"></a>
</center>

This mod adds a unique description to all blocks, items, and entities that is displayed when pressing the Ctrl key. These descriptions sum up what it can be used for, its source, and/or any other useful info. It strikes a compromise between not knowing anything about an item and constantly checking the Minecraft Wiki for detailed in-depth descriptions.

Item Descriptions includes all vanilla blocks and items. For mod support, also add [Mod Descriptions](https://modrinth.com/resourcepack/mod-descriptions)!

## Item Descriptions

By default, holding down Ctrl when hovering over an item will show a short description of what it can do. Unique descriptions have been written for every item, and generic descriptions are also supported for various tags so many modded blocks come with built-in descriptions.

## Block and Entity Descriptions

If you have [Jade](https://modrinth.com/mod/jade) or [WTHIT](https://modrinth.com/mod/wthit) installed, blocks and entities will also show descriptions when Ctrl is held.

## Enchantment Descriptions

Item Descriptions supports descriptions for enchantments as well, with descriptions for every vanilla enchantments included. Item Descriptions prefers its own descriptions, but will support mods that support [Enchantment Descriptions](https://modrinth.com/mod/enchantment-descriptions) out of the box.

## Installation

Item Descriptions is a completely clientside mod for Fabric and NeoForge. 
- On Fabric, only [Fabric API](https://modrinth.com/mod/fabric-api) is required.
- On NeoForge, there are no dependencies.
- Mod settings are available with [Mod Menu](https://modrinth.com/mod/mod-menu) (only required on Fabric) and [Cloth Config](https://modrinth.com/mod/cloth-config).

<details>
<summary>Mod Support</summary>

When [Mod Menu](https://modrinth.com/mod/mod-menu) (only required on Fabric) and [Cloth Config](https://modrinth.com/mod/cloth-config) are installed, you can configure mod settings ingame, including the tooltip key, tooltkip colour, "always on" mode, block/entity descriptions, and more.

When [Jade](https://modrinth.com/mod/jade)/[WTHIT](https://modrinth.com/mod/wthit)/[HWYLA](https://modrinth.com/mod/hwyla) is installed, it will show block and entity descriptions in its informational HUD.

When [Useful Spyglass](https://www.curseforge.com/minecraft/mc-mods/useful-spyglass) is installed, Useful Spyglass will show block and entity descriptions in its informational HUD.

When [Limelight](https://modrinth.com/mod/limelight) is installed, Limelight will show a description in its informational command palette when a block, item, or entity matches your search.

When [ToolTipFix](https://modrinth.com/mod/tooltipfix) is installed, its wrapping is used instead of the built-in wrapper.

When [Polymer](https://modrinth.com/mod/tooltipfix) is installed on the server, mods with custom serverside content can have their own custom descriptions.

For support for other mods, try [Mod Descriptions](https://modrinth.com/resourcepack/mod-descriptions)!

</details>


## Adding New Descriptions

By default, the mod provides descriptions for all blocks, items, and entities - including supported April Fools versions, but it has been designed in a way that makes it extensible for use in modpacks or when using other custom content. To add a tooltip to a block or item, just add the following entry to your mod or resource pack's language file.

```json
"lore.<namespace>.<your_item_here>": "A mysterious modded block."
```

For more information, including details on support for Custom Model Data, see the information on the [Item Descriptions Wiki](https://moddedmc.wiki/en/project/item-descriptions/docs).

## Credits
The descriptions present in the mod are inspired by the Minecraft Legacy Console Editions. 
The interface used was inspired by the one present in the Better Than Adventure mod for Minecraft b1.7.3. 
Mod Menu/Cloth Config integration and the code for reading an item's enchantments are both based on code present in [idwtialsimmoedm](https://modrinth.com/mod/idwtialsimmoedm) under its [MIT License](https://github.com/gliscowo/idwtialsimmoedm/blob/1.21/LICENSE).

For Polymer support, some code was copied from Polymer under its [LGPL License](https://github.com/Patbox/polymer/blob/dev/1.21.4/LICENSE).