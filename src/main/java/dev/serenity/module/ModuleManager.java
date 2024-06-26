package dev.serenity.module;

import dev.serenity.module.impl.combat.*;
import dev.serenity.module.impl.movement.*;
import dev.serenity.module.impl.player.*;
import dev.serenity.module.impl.render.*;
import dev.serenity.module.impl.world.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        init();
        modules.sort(Comparator.comparing(Module::getName));
    }

    public void init() {
        modules.add(new Sprint());
        modules.add(new HUD());
        modules.add(new ClickGUI());
        modules.add(new Fullbright());
        modules.add(new Speed());
        modules.add(new Velocity());
        modules.add(new KillAura());
        modules.add(new NoSlow());
        modules.add(new ChestStealer());
        modules.add(new InvManager());
        modules.add(new Criticals());
        modules.add(new AutoPot());
        modules.add(new Scaffold());
        modules.add(new Fly());
    }

    public <T extends Module> T getModule(Class<T> module) {
        for (Module m : modules) {
            if (module.isInstance(m)) {
                return module.cast(m);
            }
        }
        return null;
    }

    public Module getModule(String module) {
        for (Module m : modules) {
            if (module.equalsIgnoreCase(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}
