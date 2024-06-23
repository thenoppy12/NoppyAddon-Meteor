package sources.net.thenoppy12.noppyaddon;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NormalClientInitializer implements ClientModInitializer {
    private static final Logger LOG = LogManager.getLogger("NoppyAddon");
    @Override
    public void onInitializeClient() {
        LOG.fatal("THIS MOD IS NOT USABLE WITHOUT METEOR CLIENT, PLEASE INSTALL METEOR CLIENT!");
        System.exit(-1);
    }
}
