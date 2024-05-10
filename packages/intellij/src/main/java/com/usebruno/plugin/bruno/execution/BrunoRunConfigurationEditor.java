package com.usebruno.plugin.bruno.execution;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import static com.usebruno.plugin.bruno.execution.BrunoRunConfigurationOptions.ENV_NONE_OPTION;

final class BrunoRunConfigurationEditor extends SettingsEditor<BrunoRunConfiguration> {
    private final JPanel mainPanel;
    private final TextFieldWithBrowseButton collectionPathField;
    private final ComboBox<String> environmentSelection;

    public BrunoRunConfigurationEditor() {
        collectionPathField = new TextFieldWithBrowseButton();
        collectionPathField.addBrowseFolderListener(
            "Select Collection Root Directory",
            null,
            null,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        );

        environmentSelection = new ComboBox<>(new DefaultComboBoxModel<String>(new String[]{ENV_NONE_OPTION}) {
            {
                collectionPathField.getTextField().getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {
                        refreshOptions();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {
                        refreshOptions();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent documentEvent) {
                        refreshOptions();
                    }
                });
            }

            void refreshOptions() {
                while (getSize() > 1) {
                    removeElementAt(1);
                }
                Path collectionRootPath = Path.of(collectionPathField.getText());
                File environmentDir = collectionRootPath.resolve("environments").toFile();
                if (environmentDir.isDirectory() && environmentDir.canRead()) {
                    for (File file : Objects.requireNonNull(environmentDir.listFiles())) {
                        if (file.isFile() && file.canRead() && file.getName().endsWith(".bru")) {
                            addElement(file.getName().replaceAll("\\.bru$", ""));
                        }
                    }
                }
            }
        });

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Collection root directory", collectionPathField)
            .addLabeledComponent("Environment", environmentSelection)
            .getPanel();
    }

    @Override
    protected void resetEditorFrom(BrunoRunConfiguration brunoRunConfiguration) {
        collectionPathField.setText(brunoRunConfiguration.getOptions().getCollectionRoot());
        environmentSelection.setItem(brunoRunConfiguration.getOptions().getEnvironment());
    }

    @Override
    protected void applyEditorTo(@NotNull BrunoRunConfiguration brunoRunConfiguration) {
        brunoRunConfiguration.getOptions().setCollectionRoot(collectionPathField.getText());
        brunoRunConfiguration.getOptions().setEnvironment(environmentSelection.getItem());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }
}
