package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.usebruno.plugin.bruno.settings.BrunoSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BrunoRunConfiguration extends RunConfigurationBase<BrunoRunConfigurationOptions> {
    protected BrunoRunConfiguration(
        @NotNull Project project,
        @Nullable ConfigurationFactory factory,
        @Nullable String name
    ) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    protected BrunoRunConfigurationOptions getOptions() {
        return (BrunoRunConfigurationOptions) super.getOptions();
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new BrunoRunConfigurationEditor();
    }

    @Override
    public void checkSettingsBeforeRun() throws RuntimeConfigurationException {
        File brunoCliExecutable = BrunoSettingsState.getBrunoCliExecutable();
        if (null == brunoCliExecutable) {
            throw new RuntimeConfigurationException("No Bruno CLI executable configured.");
        }

        File brunoCollectionRoot = new File(getOptions().getCollectionRoot());
        if (!brunoCollectionRoot.isDirectory()) {
            throw new RuntimeConfigurationException("Bruno collection root is not a directory.");
        }

        File brunoCollectionJson = brunoCollectionRoot.toPath().resolve("bruno.json").toFile();
        if (!brunoCollectionJson.isFile() || !brunoCollectionJson.canRead()) {
            throw new RuntimeConfigurationException("Bruno collection json must be a readable file.");
        }
    }

    @Override
    public RunProfileState getState(
        @NotNull Executor executor,
        @NotNull ExecutionEnvironment environment
    ) {
        return new CommandLineState(environment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                ArrayList<String> cliParams = new ArrayList<>(List.of(
                    Objects.requireNonNull(BrunoSettingsState.getBrunoCliExecutable()).getAbsolutePath(),
                    "run"
                ));
                getOptions().addCliParameters(cliParams);
                GeneralCommandLine commandLine = new GeneralCommandLine(cliParams)
                    .withWorkDirectory(getOptions().getCollectionRoot());
                OSProcessHandler processHandler = ProcessHandlerFactory.getInstance()
                    .createColoredProcessHandler(commandLine);
                ProcessTerminatedListener.attach(processHandler);
                return processHandler;
            }
        };
    }

}
