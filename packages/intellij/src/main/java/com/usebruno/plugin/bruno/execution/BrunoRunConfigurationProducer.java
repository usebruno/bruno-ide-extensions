package com.usebruno.plugin.bruno.execution;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BrunoRunConfigurationProducer extends LazyRunConfigurationProducer<BrunoRunConfiguration> {
    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
        return Objects.requireNonNull(ConfigurationTypeUtil.findConfigurationType(BrunoRunConfigurationType.ID)).getConfigurationFactories()[0];
    }

    @Override
    protected boolean setupConfigurationFromContext(
        @NotNull BrunoRunConfiguration configuration,
        @NotNull ConfigurationContext context,
        @NotNull Ref<PsiElement> sourceElement
    ) {
        VirtualFile collectionFile = contextToCollectionFile(context);
        if (collectionFile == null) {
            return false;
        }

        configuration.setName("Run Collection " + collectionFile.getName());
        configuration.getOptions().setCollectionRoot(collectionFile.getPath());
        return true;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull BrunoRunConfiguration configuration, @NotNull ConfigurationContext context) {
        VirtualFile collectionFile = contextToCollectionFile(context);
        if (collectionFile == null) {
            return false;
        }

        return configuration.getOptions().getCollectionRoot().equals(collectionFile.getPath());
    }

    @Nullable
    private VirtualFile contextToCollectionFile(@NotNull ConfigurationContext context) {
        //noinspection rawtypes
        Location location = context.getLocation();
        if (location == null) {
            return null;
        }

        PsiFile psiFile = location.getPsiElement().getContainingFile();
        if (psiFile == null) {
            return null;
        }


        VirtualFile virtualFile = psiFile.getVirtualFile();
        String fileName = virtualFile.getName();
        if (!fileName.equals("bruno.json") && !fileName.equals("collection.bru")) {
            return null;
        }

        return virtualFile.getParent();
    }
}
