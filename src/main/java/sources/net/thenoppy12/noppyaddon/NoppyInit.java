package sources.net.thenoppy12.noppyaddon;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class NoppyInit extends MeteorAddon {
    public static final Category CATEGORY = new Category("NoppyAddon");
    @Override
    public void onInitialize() {
        NoppyAddon.INSTANCE.initialize();
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "sources.net.thenoppy12.noppyaddon";
    }


}
