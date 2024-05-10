package com.usebruno.plugin.bruno.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.io.File;

final class BrunoSettingsComponent {
    private final JPanel mainPanel;
    private final TextFieldWithBrowseButton pathToBrunoCliExecutableField;

    public BrunoSettingsComponent() {
        pathToBrunoCliExecutableField = new TextFieldWithBrowseButton();
        pathToBrunoCliExecutableField.addBrowseFolderListener(
            "Select Bruno CLI Executable",
            null,
            null,
            FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()
        );
        File autoDetectedPathToBrunoCliExecutable = BrunoSettingsUtils.authDetectPathToBrunoCliExecutable();
        if (null != autoDetectedPathToBrunoCliExecutable) {
            ((JBTextField) pathToBrunoCliExecutableField.getTextField())
                .getEmptyText()
                .setText("Auto detected: " + autoDetectedPathToBrunoCliExecutable.getAbsolutePath());
        }
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(new JBLabel("Bruno CLI Executable: "), pathToBrunoCliExecutableField, 1, false)
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return pathToBrunoCliExecutableField;
    }

    public String getPathToBrunoCliExecutable() {
        return pathToBrunoCliExecutableField.getText();
    }

    public void setPathToBrunoCliExecutable(String value) {
        pathToBrunoCliExecutableField.setText(value);
    }
}