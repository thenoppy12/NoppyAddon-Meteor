package sources.net.thenoppy12.noppyaddon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NormalClientInitializer implements ClientModInitializer {
    private static final Logger LOG = LogManager.getLogger("NoppyAddon");
    @Override
    public void onInitializeClient() {
        if (!FabricLoader.getInstance().isModLoaded("meteor-client")) {
            LOG.fatal("[NoppyAddon] THE CLIENT IS NOT USABLE WTIH NOPPYADDON WITHOUT METEOR CLIENT, PLEASE INSTALL METEOR CLIENT!");
            System.exit(-1);
        }
    }
}
