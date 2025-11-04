package com.usebruno.plugin.bruno.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Service(Service.Level.APP)
@State(
    name = "com.usebruno.plugin.bruno.settings.BrunoSettingsState",
    storages = @Storage("bruno.xml")
)
public final class BrunoSettingsState implements PersistentStateComponent<BrunoSettingsState> {

    public String pathToBrunoCliExecutable = null;

    static BrunoSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(BrunoSettingsState.class);
    }

    @Override
    public BrunoSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BrunoSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static File getBrunoCliExecutable() {
        String pathToBrunoCliExecutable = getInstance().pathToBrunoCliExecutable;
        if (pathToBrunoCliExecutable == null || pathToBrunoCliExecutable.isBlank()) {
            return BrunoSettingsUtils.authDetectPathToBrunoCliExecutable();
        }

        File configuredExecutable = new File(pathToBrunoCliExecutable);
        if (configuredExecutable.isFile() && configuredExecutable.canExecute()) {
            return configuredExecutable;
        }

        return null;
    }
}