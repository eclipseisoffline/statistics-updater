package xyz.eclipseisoffline.statisticsupdater;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.UserNameToIdResolver;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import org.slf4j.Logger;

public class StatisticsUpdater implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->dispatcher.register(
                Commands.literal("scoreboard")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("objectives")
                                .then(Commands.literal("update")
                                        .then(Commands.argument("objective", ObjectiveArgument.objective())
                                                .executes(context -> {
                                                    Objective objective = ObjectiveArgument.getObjective(context, "objective");

                                                    if (!(objective.getCriteria() instanceof Stat<?>)) {
                                                        throw new SimpleCommandExceptionType(Component.literal("Objective criteria is not a statistic")).create();
                                                    }

                                                    MinecraftServer server = context.getSource().getServer();
                                                    UserNameToIdResolver profileCache = server.services().nameToIdCache();

                                                    Scoreboard scoreboard = objective.getScoreboard();
                                                    Map<UUID, ServerStatsCounter> stats = getPlayerStats(server);

                                                    AtomicInteger missingPlayers = new AtomicInteger();
                                                    stats.forEach((uuid, playerStats) -> {
                                                        int statsValue = playerStats.getValue((Stat<?>) objective.getCriteria());
                                                        if (statsValue == 0) {
                                                            return;
                                                        }

                                                        ScoreHolder scoreHolder = profileCache.get(uuid)
                                                                .map(nameAndId -> new GameProfile(nameAndId.id(), nameAndId.name()))
                                                                .map(ScoreHolder::fromGameProfile)
                                                                .orElseGet(() -> {
                                                                    missingPlayers.incrementAndGet();
                                                                    LOGGER.warn("Couldn't find a player name for UUID {} while updating objective {}", uuid, objective.getName());
                                                                    return ScoreHolder.forNameOnly(uuid.toString());
                                                                });
                                                        scoreboard.getOrCreatePlayerScore(scoreHolder, objective).set(statsValue);
                                                    });

                                                    context.getSource().sendSuccess(() -> {
                                                        if (missingPlayers.get() > 0) {
                                                            return Component.literal("Updated values for all players for " + objective.getName() + ". Not all UUIDs could be fetched; please read the logs for more information");
                                                        } else {
                                                            return Component.literal("Updated values for all players for " + objective.getName());
                                                        }
                                                    }, true);

                                                    return missingPlayers.get();
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("players")
                                .then(Commands.literal("update")
                                        .then(Commands.argument("player", ScoreHolderArgument.scoreHolder())
                                                .executes(context -> {
                                                    MinecraftServer server = context.getSource().getServer();
                                                    UserNameToIdResolver profileCache = server.services().nameToIdCache();

                                                    ScoreHolder scoreHolder = ScoreHolderArgument.getName(context, "player");
                                                    UUID uuid = profileCache.get(scoreHolder.getScoreboardName())
                                                            .map(NameAndId::id)
                                                            .orElseThrow(() -> new SimpleCommandExceptionType(Component.literal("No UUID could be found for the given player")).create());

                                                    ServerStatsCounter stats = getPlayerStats(server).get(uuid);
                                                    if (stats == null) {
                                                        throw new SimpleCommandExceptionType(Component.literal("No statistics found for the given player")).create();
                                                    }

                                                    server.getScoreboard().getObjectives().forEach(objective -> {
                                                        if (objective.getCriteria() instanceof Stat<?> stat) {
                                                            if (stats.getValue(stat) != 0) {
                                                                server.getScoreboard().getOrCreatePlayerScore(scoreHolder, objective).set(stats.getValue(stat));
                                                            }
                                                        }
                                                    });

                                                    context.getSource().sendSuccess(() -> Component.literal("Updated scores for " + scoreHolder.getScoreboardName()), true);
                                                    return 0;
                                                })
                                        )
                                )
                        )
        ));
    }

    private Map<UUID, ServerStatsCounter> getPlayerStats(MinecraftServer server) {
        File statsDir = server.getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();

        File[] statsFiles = statsDir.listFiles();
        if (statsFiles == null) {
            return Map.of();
        }

        Map<UUID, ServerStatsCounter> statsCounters = new HashMap<>();
        for (File statsFile : statsFiles) {
            UUID uuid = UUID.fromString(statsFile.getName().replace(".json", ""));

            ServerStatsCounter playerStat = new ServerStatsCounter(server, statsFile.toPath());
            statsCounters.put(uuid, playerStat);
        }

        return statsCounters;
    }
}
