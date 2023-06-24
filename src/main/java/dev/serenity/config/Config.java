package dev.serenity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.serenity.Serenity;
import dev.serenity.module.Module;
import dev.serenity.setting.Setting;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Config {
    private File dir;

    public Config(String name) {
        setConfigName(name);

        if (!dir.exists()) {
            try {
                dir.createNewFile();
                saveConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonObject = new JsonObject();
        for (Module module : Serenity.getInstance().getModuleManager().getModules()) {
            JsonObject jsonMod = new JsonObject();
            JsonObject jsonSetting = new JsonObject();

            for (Setting setting : module.settings) {
                if (setting instanceof BooleanSetting) {
                    jsonSetting.addProperty(setting.name, ((BooleanSetting) setting).isEnabled());
                }
                if (setting instanceof NumberSetting) {
                    jsonSetting.addProperty(setting.name, ((NumberSetting) setting).getValue());
                }
                if (setting instanceof ModeSetting) {
                    jsonSetting.addProperty(setting.name, ((ModeSetting) setting).getCurrentMode());
                }
            }

            jsonMod.addProperty("Key Bind", Keyboard.getKeyName(module.getKey()));
            jsonMod.addProperty("State", module.isEnabled());
            jsonMod.add("Settings", jsonSetting);

            jsonObject.add(module.getName(), jsonMod);
        }

        try (FileWriter writer = new FileWriter(dir)) {
            writer.write(gson.toJson(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        if (dir.exists()) {
            try {
                Gson gson = new Gson();
                FileReader fileReader = new FileReader(dir);
                JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
                fileReader.close();

                for (String moduleName : jsonObject.keySet()) {
                    JsonObject moduleObject = jsonObject.getAsJsonObject(moduleName);
                    String keyBind = moduleObject.get("Key Bind").getAsString();
                    boolean state = moduleObject.get("State").getAsBoolean();
                    JsonObject settingsObject = moduleObject.getAsJsonObject("Settings");

                    final Module module = Serenity.getInstance().getModuleManager().getModule(moduleName);
                    module.setKey(Keyboard.getKeyIndex(keyBind));
                    module.setState(state);

                    Set<Map.Entry<String, JsonElement>> settingsEntries = settingsObject.entrySet();
                    for (Map.Entry<String, JsonElement> entry : settingsEntries) {
                        String settingName = entry.getKey();
                        JsonElement settingValue = entry.getValue();

                        Setting setting = module.getSetting(settingName);
                        if (setting instanceof NumberSetting) {
                            ((NumberSetting) setting).setValue(settingValue.getAsFloat());
                        }
                        if (setting instanceof BooleanSetting) {
                            ((BooleanSetting) setting).setEnabled(settingValue.getAsBoolean());
                        }
                        if (setting instanceof ModeSetting) {
                            ((ModeSetting) setting).setMode(settingValue.getAsString());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setConfigName(String name) {
        this.dir = new File(ConfigManager.configsDir, name);
    }
}
