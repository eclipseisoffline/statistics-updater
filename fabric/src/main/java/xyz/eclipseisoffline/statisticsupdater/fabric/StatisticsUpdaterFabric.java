package xyz.eclipseisoffline.statisticsupdater.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import xyz.eclipseisoffline.statisticsupdater.StatisticsUpdater;

public class StatisticsUpdaterFabric extends StatisticsUpdater implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) -> registerCommand(dispatcher));
    }
}
