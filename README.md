# Item Descriptions

<p text-align='center'>
<a href='https://modrinth.com/mod/pyrite/versions?l=fabric'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/pyrite/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg"></a>

</center>

This mod adds a unique description to all blocks, items, and entities that is displayed when pressing the Ctrl key. These descriptions sum up what it can be used for, its source, and/or any other useful info. It strikes a compromise between not knowing anything about an item and constantly checking the Minecraft Wiki for detailed in-depth descriptions.

## Item Descriptions

By default, holding down Ctrl when hovering over an item will show a short description of what it can do. Unique descriptions have been written for every item, and generic descriptions are also supported for various tags so many modded blocks come with built-in descriptions.

## Block and Entity Descriptions

If you have [Jade](https://modrinth.com/mod/jade) or [WTHIT](https://modrinth.com/mod/wthit) installed, blocks and entities will also show descriptions when Ctrl is held.

## Installation

Item Descriptions is a completely clientside mod for Fabric and NeoForge. 
- On Fabric, only [Fabric API](https://modrinth.com/mod/fabric-api) is required.
- On NeoForge, there are no dependencies.
- Mod settings are available with [Mod Menu](https://modrinth.com/mod/mod-menu) (only required on Fabric) and [Cloth Config](https://modrinth.com/mod/cloth-config).

<details>
<summary>Mod Support</summary>

When [Mod Menu](https://modrinth.com/mod/mod-menu) (only required on Fabric) and [Cloth Config](https://modrinth.com/mod/cloth-config) are installed, you can configure mod settings ingame, including the tooltip key, tooltkip colour, "always on" mode, block/entity descriptions, and more.

When [Jade](https://modrinth.com/mod/jade) is installed, Jade will show block and entity descriptions in its informational HUD.

When [WTHIT](https://modrinth.com/mod/wthit) is installed, WTHIT will show block and entity descriptions in its informational HUD.

When [HWYLA](https://modrinth.com/mod/hwyla) is installed, HWYLA will show block and entity descriptions in its informational HUD.

When [ToolTipFix](https://modrinth.com/mod/tooltipfix) is installed, its wrapping is used instead of the built in wrapper.

When [Limelight](https://modrinth.com/mod/limelight) is installed, Limelight will show block, item, and entity descriptions in its informational command palette when the search starts with `#`.

</details>


## Adding New Desccriptions

By default, the mod provides descriptions for all blocks, items, and entities - including supported April Fools versions, but it has been designed in a way that makes it extensible for use in modpacks or when using other custom content. To add a tooltip to a block or item, just add the following entry to your mod or resource pack's language file.

```json
"lore.<namespace>.<your_item_here>": "A mysterious modded block."
```

For more information, including details on support for Custom Model Data, see the information on the [Item Descriptions Wiki](https://github.com/cassiancc/Item-Descriptions/wiki).

## Credits
The descriptions present in the mod are inspired by the Minecraft Legacy Console Editions. The interface used was inspired by the one present in the Better Than Adventure mod for Minecraft b1.7.3. Mod Menu/Cloth Config integration is based on the integration present in [idwtialsimmoedm](https://modrinth.com/mod/idwtialsimmoedm) under its [MIT License](https://github.com/gliscowo/idwtialsimmoedm/blob/1.21/LICENSE).
