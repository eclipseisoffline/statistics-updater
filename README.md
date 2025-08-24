# Statistics Updater

[![Modrinth Version](https://img.shields.io/modrinth/v/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/sw5IYd9j?logo=modrinth&color=008800)](https://modrinth.com/mod/statistics-updater)
[![Discord Badge](https://img.shields.io/badge/chat-discord-%235865f2)](https://discord.gg/CNNkyWRkqm)
[![Github Badge](https://img.shields.io/badge/github-statistics--updater-white?logo=github)](https://github.com/eclipseisoffline/statistics-updater)
![GitHub License](https://img.shields.io/github/license/eclipseisoffline/statistics-updater)

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
| 1.21.6+7+8        | ✅ Current    |
| 1.21.5            | ✔️ Available |
| 1.21.4            | ✔️ Available |
| 1.21.1            | ✅ Current    |

I try to keep support up for the latest major and latest minor release of Minecraft. Updates to newer Minecraft
versions may be delayed from time to time, as I do not always have the time to immediately update my mods.

Unsupported versions are still available to download, but they won't receive new features or bugfixes.

## Usage

The Fabric API is required. This mod is not required on clients when playing on multiplayer.

This mod adds 2 new commands to the `/scoreboard` command:

- `/scoreboard objectives update <objective>`
- `/scoreboard players update <player>`

The first can be used to update an objective with a statistic criterion. The second can be used to update a player's score
for all existing objectives with a statistic criterion.
