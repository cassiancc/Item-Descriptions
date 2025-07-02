### Added
- Support for newlines (\n) in descriptions.
- If Cloth Config is not present, the mod can now be configured through YACL.

### Changed
- Migrated internal configuration to Kaleido Config. Item Descriptions config file is now `item_descriptions.toml`, and is now organized and properly commented.
- Block Descriptions and Entity Descriptions configs have been separated into their own tabs.

### Fixed
- Conflicting translation keys with EMI (#26).
- Crash from renamed method on NeoForge 1.21.5+.