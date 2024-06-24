package sources.net.thenoppy12.noppyaddon.events;
import java.util.ArrayList;

import net.minecraft.network.packet.Packet;
import sources.net.thenoppy12.noppyaddon.event.CancellableEvent;
import sources.net.thenoppy12.noppyaddon.event.Listener;

public interface PacketInputListener extends Listener
{
    public void onReceivedPacket(PacketInputEvent event);

    public static class PacketInputEvent
            extends CancellableEvent<PacketInputListener>
    {
        private final Packet<?> packet;

        public PacketInputEvent(Packet<?> packet)
        {
            this.packet = packet;
        }

        public Packet<?> getPacket()
        {
            return packet;
        }

        @Override
        public void fire(ArrayList<PacketInputListener> listeners)
        {
            for(PacketInputListener listener : listeners)
            {
                listener.onReceivedPacket(this);

                if(isCancelled())
                    break;
            }
        }

        @Override
        public Class<PacketInputListener> getListenerType()
        {
            return PacketInputListener.class;
        }
    }
}
