package com.usebruno.plugin.bruno.styles;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class BruFileType extends LanguageFileType {
    public static final BruFileType INSTANCE = new BruFileType();
    public static final String DEFAULT_EXTENSION = "bru";

    private BruFileType() {
        super(PlainTextLanguage.INSTANCE);
    }


    @NotNull
    @Override
    public String getName() {
        return "Bru File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Bru File Type";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return null;
    }
}


