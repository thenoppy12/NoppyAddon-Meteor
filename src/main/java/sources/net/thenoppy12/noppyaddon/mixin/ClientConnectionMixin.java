package sources.net.thenoppy12.noppyaddon.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sources.net.thenoppy12.noppyaddon.events.ConnectionPacketOutputListener.ConnectionPacketOutputEvent;
import sources.net.thenoppy12.noppyaddon.events.PacketInputListener;
import sources.net.thenoppy12.noppyaddon.event.EventManager;

import java.util.concurrent.ConcurrentLinkedQueue;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin extends SimpleChannelInboundHandler<Packet<?>> {
    private ConcurrentLinkedQueue<ConnectionPacketOutputEvent> events = new ConcurrentLinkedQueue<>();
    @Inject(at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V",
            ordinal = 0),
            method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            cancellable = true
    )
    private void onChannelRead0(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        PacketInputListener.PacketInputEvent event = new PacketInputListener.PacketInputEvent(packet);
        EventManager.fire(event);

        if(event.isCancelled())
            ci.cancel();
    }
    @ModifyVariable(
            at = @At("HEAD"),
            method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V"
    )
    public Packet<?> modifyPacket(Packet<?> packet) {
        ConnectionPacketOutputEvent event = new ConnectionPacketOutputEvent(packet);
        events.add(event);
        EventManager.fire(event);
        return event.getPacket();
    }
    @Inject(
            at = @At("HEAD"),
            method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V",
            cancellable = true
    )
    private void onSend(Packet<?> packet, @Nullable PacketCallbacks callback, CallbackInfo ci) {
        ConnectionPacketOutputEvent event = getEvent(packet);
        if(event == null)
            return;

        if(event.isCancelled())
            ci.cancel();

        events.remove(event);
    }
    @Unique
    private ConnectionPacketOutputEvent getEvent(Packet<?> packet) {
        for(ConnectionPacketOutputEvent event : events)
            if(event.getPacket() == packet)
                return event;

        return null;
    }
}
