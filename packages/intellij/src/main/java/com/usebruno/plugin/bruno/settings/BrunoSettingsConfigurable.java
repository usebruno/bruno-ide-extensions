package com.usebruno.plugin.bruno.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

final class BrunoSettingsConfigurable implements Configurable {

    private BrunoSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Bruno";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new BrunoSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        BrunoSettingsState settings = BrunoSettingsState.getInstance();
        return !Objects.equals(settingsComponent.getPathToBrunoCliExecutable(), settings.pathToBrunoCliExecutable);
    }

    @Override
    public void apply() {
        BrunoSettingsState settings = BrunoSettingsState.getInstance();
        settings.pathToBrunoCliExecutable = settingsComponent.getPathToBrunoCliExecutable();
    }

    @Override
    public void reset() {
        BrunoSettingsState settings = BrunoSettingsState.getInstance();
        settingsComponent.setPathToBrunoCliExecutable(settings.pathToBrunoCliExecutable);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}