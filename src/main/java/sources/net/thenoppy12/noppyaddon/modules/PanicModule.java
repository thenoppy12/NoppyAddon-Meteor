package sources.net.thenoppy12.noppyaddon.modules;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import sources.net.thenoppy12.noppyaddon.NoppyInit;

// works
public class PanicModule extends Module {
    public PanicModule() {
        super(NoppyInit.CATEGORY, "Panic", "Instantly turn all active modules off, caution with this one, use to proof that u dont hack =] .");
    }
    @Override
    public void onActivate() {
        for (Module module : Modules.get().getAll()) {
            if (module.isActive() && module != this) {
                module.toggle();
            }
        }
        ChatUtils.info("All modules toggled");
        this.toggle();
    }
}
