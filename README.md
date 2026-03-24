# Statistics Updater

[![Modrinth Version](https://img.shields.io/modrinth/v/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Discord Badge](https://img.shields.io/badge/chat-discord-%235865f2)](https://discord.gg/CNNkyWRkqm)
[![Github Badge](https://img.shields.io/badge/github-statistics--updater-white?logo=github)](https://github.com/eclipseisoffline/statistics-updater)
![GitHub License](https://img.shields.io/github/license/eclipseisoffline/statistics-updater)
![Available for Fabric](https://img.shields.io/badge/available_for-fabric-_?color=%23dbd0b4)
![Available for NeoForge](https://img.shields.io/badge/available_for-NeoForge-_?color=%23e58c53)

This mod extends the `/scoreboard` command to add functionality for updating scoreboards that use statistics criteria.

## License

This mod is licensed under the MIT license.

## Donating

If you like this mod, consider [donating](https://buymeacoffee.com/eclipseisoffline).

## Discord

For support and/or any questions you may have, feel free to join [my discord](https://discord.gg/CNNkyWRkqm).

## Version support

| Minecraft Version | Status       |
|-------------------|--------------|
| 26.1              | ✅ Current    |
| 1.21.11           | ✔️ Available |
| 1.21.9+10         | ✔️ Available |
| 1.21.6+7+8        | ✔️ Available |
| 1.21.5            | ✔️ Available |
| 1.21.4            | ✔️ Available |
| 1.21.1            | ✔️ Available |

I try to keep support up for the latest drop of Minecraft. Updates to newer Minecraft
versions may be delayed from time to time, as I do not always have the time to immediately update my mods.

Unsupported versions are still available to download, but they won't receive new features or bugfixes.

NeoForge ports are available for Minecraft 26.1 onwards.

## Usage

The Fabric API is required on Fabric. This mod is not required on clients when playing on multiplayer.

This mod adds 2 new commands to the `/scoreboard` command:

- `/scoreboard objectives update <objective>`
- `/scoreboard players update <player>`

The first can be used to update an objective with a statistic criterion. The second can be used to update a player's score
for all existing objectives with a statistic criterion.
