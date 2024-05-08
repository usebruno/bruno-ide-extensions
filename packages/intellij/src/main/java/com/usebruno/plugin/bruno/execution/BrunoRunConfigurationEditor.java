package com.usebruno.plugin.bruno.execution;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

final class BrunoRunConfigurationEditor extends SettingsEditor<BrunoRunConfiguration> {
    private final JPanel mainPanel;
    private final TextFieldWithBrowseButton scriptPathField;

    public BrunoRunConfigurationEditor() {
        scriptPathField = new TextFieldWithBrowseButton();
        scriptPathField.addBrowseFolderListener(
            "Select Collection Root Directory",
            null,
            null,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        );
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Collection root directory", scriptPathField)
            .getPanel();
    }

    @Override
    protected void resetEditorFrom(BrunoRunConfiguration brunoRunConfiguration) {
        scriptPathField.setText(brunoRunConfiguration.getOptions().getCollectionRoot());
    }

    @Override
    protected void applyEditorTo(@NotNull BrunoRunConfiguration brunoRunConfiguration) {
        brunoRunConfiguration.getOptions().setCollectionRoot(scriptPathField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }
}
