package sources.net.thenoppy12.noppyaddon;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerInitializer implements DedicatedServerModInitializer {
    private static final Logger LOG = LogManager.getLogger("NoppyAddon");
    @Override
    public void onInitializeServer() {
        LOG.fatal("THIS MOD IS CLIENT-SIDE WITH METEOR CLIENT INSTALLED, CANT RUN IN MINECRAFT SERVER");
        System.exit(-1);
    }
}
