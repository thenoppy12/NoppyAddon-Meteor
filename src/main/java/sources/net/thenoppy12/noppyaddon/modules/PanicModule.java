package sources.net.thenoppy12.noppyaddon.modules;

import meteordevelopment.meteorclient.systems.modules.Modules;
import sources.net.thenoppy12.noppyaddon.NoppyInit;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
public class PanicModule extends Module {
    public PanicModule() {
        super(NoppyInit.CATEGORY, "Panic", "Instantly turn all active modules off, caution with this one, use to proof that u dont hack =] .");
    }
    @EventHandler
    private void Panic() {
        for (Module module : Modules.get().getAll())
            if (module.isActive() && module != this) {
                module.toggle();
            }
        this.toggle();
    }
}
