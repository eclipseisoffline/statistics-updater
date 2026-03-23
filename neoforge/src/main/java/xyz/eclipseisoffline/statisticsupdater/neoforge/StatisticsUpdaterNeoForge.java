package xyz.eclipseisoffline.statisticsupdater.neoforge;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xyz.eclipseisoffline.statisticsupdater.StatisticsUpdater;

@Mod(StatisticsUpdater.MOD_ID)
public class StatisticsUpdaterNeoForge extends StatisticsUpdater {

    public StatisticsUpdaterNeoForge() {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> registerCommand(event.getDispatcher()));
    }
}
