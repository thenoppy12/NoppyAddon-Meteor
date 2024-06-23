package sources.net.thenoppy12.noppyaddon.event;

import com.mojang.datafixers.TypeRewriteRule;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import sources.net.thenoppy12.noppyaddon.NoppyAddon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class EventManager {
    private static NoppyAddon addon = NoppyAddon.INSTANCE;
    public EventManager(NoppyAddon noppyAddon) {
        addon = noppyAddon;
    }
    private final HashMap<Class<? extends Listener>, ArrayList<? extends Listener>> listenerMap = new HashMap<>();
    public <L extends Listener> void add(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null)
            {
                listeners = new ArrayList<>(Arrays.asList(listener));
                listenerMap.put(type, listeners);
                return;
            }

            listeners.add(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Adding NoppyAddon event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
    public <L extends Listener> void remove(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners != null)
                listeners.remove(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Removing NoppyAddon event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
}
