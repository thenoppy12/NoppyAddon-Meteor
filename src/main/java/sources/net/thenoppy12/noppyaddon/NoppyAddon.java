package sources.net.thenoppy12.noppyaddon;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.commands.Commands;
// import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import sources.net.thenoppy12.noppyaddon.commands.*;
import sources.net.thenoppy12.noppyaddon.modules.*;

public enum NoppyAddon {
    INSTANCE;
    public static final Logger log = LogUtils.getLogger();
    public static final MinecraftClient minecraft = MinecraftClient.getInstance();

    public void initialize() {

        log.info("\n");
        log.info("------------------------------------------------------------------------");
        log.info("| [NoppyAddon] Initializing NoppyAddon for Meteor                      |");
        log.info("| [NoppyAddon] Version: 0.1.0                                          |");
        log.info("| [NoppyAddon] Github: https://github.com/thenoppy12/NoppyAddon-Meteor |");
        log.info("------------------------------------------------------------------------");
        log.info("goofy ahh box lmfao the console cant print utf-8 unicode :skull:");
        log.info("\n");

        try {
            log.info("[NoppyAddon] Register Meteor Modules...");
            addNoppyModules();
            log.info("[NoppyAddon] Load modules completed\n");
        } catch (Exception e) {
            log.error("[NoppyAddon] Can't load NoppyAddon modules, caught: " + e.getMessage() + "\n");
        }

        try {
            log.info("[NoppyAddon] Register Meteor Commands...");
            addNoppyCommands();
            log.info("[NoppyAddon] Load commands completed\n");
        } catch (Exception e) {
            log.error("[NoppyAddon] Can't load NoppyAddon commands, caught: " + e.getMessage() + "\n");
        }

        try {
            log.info("[NoppyAddon] Register Meteor HUDs...");
            addNoppyHUD();
            log.info("[NoppyAddon] Load HUDs completed\n");
        } catch (Exception e) {
            log.error("[NoppyAddon] Can't load NoppyAddon HUDs, caught: " + e.getMessage() + "\n");
        }
    }
    public void addNoppyModules() {
        Modules.get().add(new PanicModule());
        Modules.get().add(new NocomCrashModule());
        Modules.get().add(new SerberSpoofModule());
        Modules.get().add(new CustomizeAntiCrashModule());
    }
    public void addNoppyCommands() {
        //Commands.add(new DamageCommand());
    }
    public void addNoppyHUD() {
        // Hud.get().add(new <name()>);
    }
}