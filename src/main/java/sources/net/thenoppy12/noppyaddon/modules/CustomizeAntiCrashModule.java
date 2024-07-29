package sources.net.thenoppy12.noppyaddon.modules;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import sources.net.thenoppy12.noppyaddon.NoppyInit;

public class CustomizeAntiCrashModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> message = sgGeneral.add(new StringSetting.Builder()
            .name("Message")
            .description("message got when get crash packets")
            .defaultValue("Server attempts to crash you")
            .build()
    );

    private final Setting<Integer> particlelimit = sgGeneral.add(new IntSetting.Builder()
            .name("Particle Packet Limit")
            .description("limit the particle packet that client can handle")
            .defaultValue(10000)
            .range(1, 100000)
            .sliderMax(100000)
            .sliderMin(1)
            .build()
    );

    private final Setting<Boolean> log = sgGeneral.add(new BoolSetting.Builder()
            .name("log")
            .description("Logs when crash packet detected.")
            .defaultValue(false)
            .build()
    );

    public CustomizeAntiCrashModule() {
        super(NoppyInit.CATEGORY, "CustomizeAntiCrash", "yes the anticrash that more customize :)");
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (event.packet instanceof ExplosionS2CPacket packet) {
            if (
                    // outside of world (ra ngoai world)
                    packet.getX() > 30_000_000 ||
                    packet.getY() > 30_000_000 ||
                    packet.getZ() > 30_000_000 ||
                    packet.getX() < -30_000_000 ||
                    packet.getY() < -30_000_000 ||
                    packet.getZ() < -30_000_000 ||
                    // power too high (qua nhieu power)
                    packet.getRadius() > 1000 ||
                    // too many blocks (qua nhieu block)
                    packet.getAffectedBlocks().size() > 100_000 ||
                    // too much knockback (qua nhieu knockback)
                    packet.getPlayerVelocityX() > 30_000_000 ||
                    packet.getPlayerVelocityY() > 30_000_000 ||
                    packet.getPlayerVelocityZ() > 30_000_000 ||
                    // knockback can be negative? (knockback nguoc (anticheat caused))
                    packet.getPlayerVelocityX() < -30_000_000 ||
                    packet.getPlayerVelocityY() < -30_000_000 ||
                    packet.getPlayerVelocityZ() < -30_000_000
            )
            {
                cancel(event);
            }
        }
        else if (event.packet instanceof ParticleS2CPacket packet)
        {
            // too many particles (hat hieu ung qua nhieu)
            if (packet.getCount() > particlelimit.get())
            {
                cancel(event);
            }
        }
        else if (event.packet instanceof PlayerPositionLookS2CPacket packet) {
            // out of world movement (ra khoi worldborder)
            if (
                    packet.getX() > 30_000_000 ||
                    packet.getY() > 30_000_000 ||
                    packet.getZ() > 30_000_000 ||
                    packet.getX() < -30_000_000 ||
                    packet.getY() < -30_000_000 ||
                    packet.getZ() < -30_000_000)
            {
                cancel(event);
            }
        }
        else if (event.packet instanceof EntityVelocityUpdateS2CPacket packet) {
            // velocity (van toc)
            if (
                    packet.getVelocityX() > 30_000_000 ||
                    packet.getVelocityY() > 30_000_000 ||
                    packet.getVelocityZ() > 30_000_000 ||
                    // can be negative? (van toc nguoc)
                    packet.getVelocityX() < -30_000_000 ||
                    packet.getVelocityY() < -30_000_000 ||
                    packet.getVelocityZ() < -30_000_000)
            {
                cancel(event);
            }
        }
    }

    private void cancel(PacketEvent.Receive event) {
        if (log.get())
            warning(message.get());
        event.cancel();
    }
}
