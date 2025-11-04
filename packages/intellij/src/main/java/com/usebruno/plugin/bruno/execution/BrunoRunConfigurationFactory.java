package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

final class BrunoRunConfigurationFactory extends ConfigurationFactory {
    BrunoRunConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull String getId() {
        return BrunoRunConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new BrunoRunConfiguration(project, this, "Bruno");
    }

    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return BrunoRunConfigurationOptions.class;
    }
}
