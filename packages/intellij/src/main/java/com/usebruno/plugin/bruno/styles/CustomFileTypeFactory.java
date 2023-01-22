package com.usebruno.plugin.bruno.styles;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

public class CustomFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(FileTypeConsumer consumer) {
        consumer.consume(BruFileType.INSTANCE, BruFileType.DEFAULT_EXTENSION);
    }
}