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
    public static final Logger LOG = LogUtils.getLogger();
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private EventManager eventManager;

    public void initialize() {

        LOG.info("[NoppyAddon] Initializing NoppyAddon for Meteor");
        LOG.info("[NoppyAddon] Version: 0.1.0");
        LOG.info("[NoppyAddon] Github: https://github.com/thenoppy12/NoppyAddon-Meteor");

        eventManager = new EventManager(this);

        // Modules
        try {
            LOG.info("[NoppyAddon] Register Meteor Modules...");
            addNoppyModules(new PanicModule());
            addNoppyModules(new NocomCrashModule());
            addNoppyModules(new VanillaSpoofModule());
            LOG.info("[NoppyAddon] Load modules completed");
        } catch (Exception e) {
            LOG.error("[NoppyAddon] Can't load NoppyAddon modules, caught: " + e.getMessage());
        }

        // Commands
        //Commands.add(new CommandExample());

        // HUD
        //Hud.get().register(HudExample.INFO);

    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public void addNoppyModules(Module module) {
        Modules.get().add(module);
    }

}