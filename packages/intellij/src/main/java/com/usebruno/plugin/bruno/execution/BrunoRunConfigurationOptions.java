package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

final public class BrunoRunConfigurationOptions extends RunConfigurationOptions {
    private final StoredProperty<String> collectionRoot = string("").provideDelegate(this, "collectionRoot");

    public String getCollectionRoot() {
        return collectionRoot.getValue(this);
    }

    public void setCollectionRoot(String collectionRoot) {
        this.collectionRoot.setValue(this, collectionRoot);
    }
}
