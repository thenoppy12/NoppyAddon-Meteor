package sources.net.thenoppy12.noppyaddon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import sources.net.thenoppy12.noppyaddon.NoppyAddon;
import sources.net.thenoppy12.noppyaddon.NoppyInit;
import sources.net.thenoppy12.noppyaddon.event.EventManager;
import sources.net.thenoppy12.noppyaddon.events.ConnectionPacketOutputListener;

public class VanillaSpoofModule extends Module implements ConnectionPacketOutputListener {
    EventManager EVENTS = NoppyAddon.INSTANCE.getEventManager();
    public VanillaSpoofModule() {
        super(NoppyInit.CATEGORY, "VanillaSpoof", "Make server think that ur client is vanilla, useful for Anti-Fabric server.");
        EVENTS.add(ConnectionPacketOutputListener.class, this);
    }
    @EventHandler
    @Override
    public void onSentConnectionPacket(ConnectionPacketOutputEvent event) {
        if(!this.isActive())
            return;

        if(!(event.getPacket() instanceof CustomPayloadC2SPacket packet))
            return;

        // change client brand "fabric" back to "vanilla"
        if(packet.payload() instanceof BrandCustomPayload)
            event.setPacket(
                    new CustomPayloadC2SPacket(new BrandCustomPayload("vanilla")));

    }
}
