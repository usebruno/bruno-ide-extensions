package com.usebruno.plugin.textmate;

import com.intellij.openapi.application.PathManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class BrunoTextMateBundleProvider implements TextMateBundleProvider {
    @NotNull
    @Override
    public List<PluginBundle> getBundles() {
        try {
            Path brunoBundleTmpDir = Files.createTempDirectory(Path.of(PathManager.getTempPath()), "textmate-bruno");
            for (String fileToCopy : List.of("package.json", "language-configuration.json", "syntaxes/bruno.tmLanguage.json")) {
                URL resource = BrunoTextMateBundleProvider.class.getClassLoader().getResource("textmate/bruno-bundle/" + fileToCopy);
                try (InputStream resourceStream = Objects.requireNonNull(resource).openStream()) {
                    Path target = brunoBundleTmpDir.resolve(fileToCopy);
                    Files.createDirectories(target.getParent());
                    Files.copy(resourceStream, target);
                }
            }
            PluginBundle brunoBundle = new PluginBundle("Bruno", Objects.requireNonNull(brunoBundleTmpDir));
            return List.of(brunoBundle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
