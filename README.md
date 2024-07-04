# Item Descriptions

<center>
<a href='https://modrinth.com/mod/fabric-api'><img alt="fabric-api" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/qsl'><img alt="quilted-fabric-api" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/quilt_vector.svg"></a>
</center><br>

This mod adds a unique description to all items, displayed when pressing the Control key. These descriptions sum up what a block can be used for, its source, and/or any other useful info. It strikes a compromise between not knowing anything about an item and constantly checking the Minecraft Wiki for detailed in-depth descriptions.

## Installation
<a href='https://modrinth.com/mod/fabric-api'><img alt="fabric-api" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/fabric-api_vector.svg"></a>

Item Descriptions is a completely clientside mod for Fabric, only requiring [Fabric API](https://modrinth.com/mod/fabric-api). Mod settings are available with [Mod Menu](https://modrinth.com/mod/mod-menu) and [Cloth Config](https://modrinth.com/mod/cloth-config).

<details>
<summary>Other Loaders</summary>

- On Fabric, [Fabric API](https://modrinth.com/mod/fabric-api) is required.
- On Quilt, [Quilted Fabric API](https://modrinth.com/mod/qsl) is required.
- Forge support is available through [Sinytra Connector](https://modrinth.com/mod/connector) and the [Forgified Fabric API](https://modrinth.com/mod/forgified-fabric-api).

A dedicated Forge port is not planned at this time.

</details>


<details>
<summary>Mod Support</summary>

When [Mod Menu](https://modrinth.com/mod/mod-menu) and [Cloth Config](https://modrinth.com/mod/cloth-config) are installed, Mod Menu can be used to change various settings, including the tooltip key, tooltkip colour, "always on" mode, block descriptions, and more.

When [Jade](https://modrinth.com/mod/jade) is installed, Jade will show item and block descriptions in its informational HUD.

When [ToolTipFix](https://modrinth.com/mod/tooltipfix) is installed, its wrapping is used instead of the built in wrapper.

</details>


## Adding New Tooltips

By default, the mod provides descriptions for all Vanilla Minecraft items - including supported April Fools versions, but it has been designed in a way that makes it extensible for use in modpacks. To add a tooltip to a block, just add the following entry to your mod or resource pack's language file.

```json
"lore.<namespace>.<your_item_here>": "A mysterious modded block."
```

For more information, see the information on the [Item Descriptions Wiki](https://github.com/cassiancc/Item-Descriptions/wiki).

## Credits
The descriptions present in the mod are inspired by the Minecraft Legacy Console Editions. The interface used was inspired by the one present in the Better Than Adventure mod for Minecraft b1.7.3. Mod Menu/Cloth Config integration is based on the integration present in [idwtialsimmoedm](https://modrinth.com/mod/idwtialsimmoedm) under its [MIT License](https://github.com/gliscowo/idwtialsimmoedm/blob/1.21/LICENSE).
