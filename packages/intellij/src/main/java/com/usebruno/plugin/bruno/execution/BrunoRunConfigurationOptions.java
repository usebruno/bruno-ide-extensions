package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

import java.util.List;

final public class BrunoRunConfigurationOptions extends RunConfigurationOptions {
    final static String ENV_NONE_OPTION = "<None>";

    private final StoredProperty<String> collectionRoot = string("").provideDelegate(this, "collectionRoot");
    private final StoredProperty<String> environment = string("").provideDelegate(this, "environment");

    public String getCollectionRoot() {
        return collectionRoot.getValue(this);
    }

    public void setCollectionRoot(String collectionRoot) {
        this.collectionRoot.setValue(this, collectionRoot);
    }

    public String getEnvironment() {
        return environment.getValue(this);
    }

    public void setEnvironment(String environment) {
        this.environment.setValue(this, environment);
    }

    public void addCliParameters(List<String> cliParameters) {
        if (!getEnvironment().equals(ENV_NONE_OPTION)) {
            cliParameters.add("--env");
            cliParameters.add(getEnvironment());
        }
    }
}
