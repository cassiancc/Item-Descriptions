# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

## Added
- Show translation keys debug tool will now translate keys when Alt is held.
- On 1.21 and above, support for Polymer's custom items.
- Where possible, blocks with visible contents now show the description of the item inside, not their own descriptions.
  - This includes Item Frames. 
  - This includes Fast Item Frames.
  - This includes Glowcase Item Display Entities.

## Fixed
- Incorrect descriptions on leaves, swords, and axes.

## [1.14] - 2025-03-11

## Added
- Description for Polished Andesite.
- Descriptions for 1.21.5 blocks, including Bushes, Firefly Bushes, Short Dry Grass, Tall Dry Grass, Test Block, Test Instance Block, Blue Egg, Brown Egg,  and Cactus Flowers.
- Additional descriptions for legacy Forge common tags.
- New developer option to show all translation keys available for an item.

## Changed
- Removed options to disable key translation in favour of consolidated "show potential keys" option.
- Deprecated legacy string match code, likely to be removed in the future.

## Fixed
- Tooltip wrapping on Forge/NeoForge
- Issues with nested tags not being parsed correctly.

## [1.13] - 2025-01-15

## Added

- Added descriptions for Eyeblossoms, Resin blocks, Oak Leaves, and individual heads.
- Added descriptions for 1.21.5 content, including Leaf Litter and Wildflowers.
- Added additional descriptions for common tags.
- The wiki now includes suggestions on how to structure a description. (@cassiancc, @sisby-folk)
- [Useful Spyglass](https://modrinth.com/mod/useful-spyglass) now shows block and item descriptions.

## Changed

- A majority of tags have been rewritten based off of the suggestions of @sisby-folk.
- Ported to Stonecutter.

## Fixed

- Fixed a bug where the config can fail to apply.
- For items with multiple tags, the longest tag is now chosen instead of a random one. (@sisby-folk)

## [1.12] - 2024-11-20

## Changed
- Ported to 1.21.4, added support for new Custom Model Data format.

## [1.11] - 2024-10-08

## Added
- Block Items can now show descriptions for their block tags, not just their item tags.
- Descriptions for 1.21.4 Pale Garden content.

## Fixes
- Fixes for legacy tags.

## [1.10] - 2024-09-06

## Added
- Generic Translations from Item and Block Tags have been refactored, allowing descriptions from tags to be added from resource packs. The new format is documented on the Wiki.
- Added compatibility with [Limelight](https://modrinth.com/mod/limelight). Item Descriptions results will now show when an item, block, or entity is searched.
- Spawn Eggs, Infested Blocks, Horse Armour, Coral, Netherite Upgrades, and Banner Patterns now have proper descriptions.

## Changed
- Descriptions for rare items now consistently mention their rarity, based off upcoming 1.21.2 rarity values.

## [1.9] - 2024-09-06

## Added
- Additional entity descriptions

## Changed
- Updated mod descriptions

## [1.8] - 2024-08-08

## Added
- Additional entity descriptions
- Support for custom tooltip colours by name
- Support for bold and italic tooltips.
