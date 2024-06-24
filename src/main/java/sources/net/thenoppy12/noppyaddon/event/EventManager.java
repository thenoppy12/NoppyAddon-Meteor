package sources.net.thenoppy12.noppyaddon.event;

import com.mojang.datafixers.TypeRewriteRule;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import sources.net.thenoppy12.noppyaddon.NoppyAddon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public final class EventManager {
    private static NoppyAddon addon = NoppyAddon.INSTANCE;
    public EventManager(NoppyAddon noppyAddon) {
        addon = noppyAddon;
    }
    private final HashMap<Class<? extends Listener>, ArrayList<? extends Listener>> listenerMap = new HashMap<>();

    public static <L extends Listener, E extends Event<L>> void fire(E event) {
        EventManager eventManager = addon.getEventManager();
        if(eventManager == null)
            return;

        eventManager.fireImpl(event);
    }

    private <L extends Listener, E extends Event<L>> void fireImpl(E event) {
        try {
            Class<L> type = event.getListenerType();
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null || listeners.isEmpty())
                return;

            // Creating a copy of the list to avoid concurrent modification
            // issues.
            ArrayList<L> listeners2 = new ArrayList<>(listeners);

            // remove() sets an element to null before removing it. When one
            // thread calls remove() while another calls fire(), it is possible
            // for this list to contain null elements, which need to be filtered
            // out.
            listeners2.removeIf(Objects::isNull);

            event.fire(listeners2);

        } catch(Throwable e) {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Firing Wurst event");
            CrashReportSection section = report.addElement("Affected event");
            section.add("Event class", () -> event.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends Listener> void add(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null)
            {
                listeners = new ArrayList<>(Arrays.asList(listener));
                listenerMap.put(type, listeners);
                return;
            }

            listeners.add(listener);

        } catch(Throwable e){
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Adding NoppyAddon event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
    public <L extends Listener> void remove(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners != null)
                listeners.remove(listener);

        } catch(Throwable e) {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Removing NoppyAddon event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
}
