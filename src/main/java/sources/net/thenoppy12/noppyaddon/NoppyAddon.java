package sources.net.thenoppy12.noppyaddon;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import sources.net.thenoppy12.noppyaddon.event.EventManager;
import sources.net.thenoppy12.noppyaddon.modules.NocomCrashModule;
import sources.net.thenoppy12.noppyaddon.modules.PanicModule;
import sources.net.thenoppy12.noppyaddon.modules.VanillaSpoofModule;

public enum NoppyAddon {
    INSTANCE;
    public static final Logger log = LogUtils.getLogger();
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private EventManager eventManager;

    public void initialize() {
        log.info("\n");
        log.info("┌──────────────────────────────────────────────────────────────────────┐");
        log.info("│ [NoppyAddon] Initializing NoppyAddon for Meteor                      │");
        log.info("│ [NoppyAddon] Version: 0.1.0                                          │");
        log.info("│ [NoppyAddon] Github: https://github.com/thenoppy12/NoppyAddon-Meteor │");
        log.info("└──────────────────────────────────────────────────────────────────────┘");
        log.info("\n");
        eventManager = new EventManager(this);

        try {
            log.info("[NoppyAddon] Register Meteor Modules...");
            addNoppyModules(new PanicModule());
            addNoppyModules(new NocomCrashModule());
            addNoppyModules(new VanillaSpoofModule());
            log.info("[NoppyAddon] Load modules completed\n");
        } catch (Exception e) {
            log.error("\n[NoppyAddon] Can't load NoppyAddon modules, caught: " + e.getMessage() + "\n");
        }
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public void addNoppyModules(Module module) {
        Modules.get().add(module);
    }

}