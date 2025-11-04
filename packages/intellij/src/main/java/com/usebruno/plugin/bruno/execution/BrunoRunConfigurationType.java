package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.util.NotNullLazyValue;
import com.usebruno.plugin.bruno.BrunoIcons;

final public class BrunoRunConfigurationType extends ConfigurationTypeBase {
    static final String ID = "BrunoRunConfiguration";

    BrunoRunConfigurationType() {
        super(
            ID,
            "Bruno",
            "Bruno runner to execute bruno collections and requests",
            NotNullLazyValue.createValue(() -> BrunoIcons.RUN_CONFIG)
        );
        addFactory(new BrunoRunConfigurationFactory(this));
    }

}