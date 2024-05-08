package com.usebruno.plugin.bruno.settings;

import java.io.File;

final class BrunoSettingsUtils {
    public static File authDetectPathToBrunoCliExecutable() {
        for (String dirname : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(dirname, "bru");
            if (file.isFile() && file.canExecute()) {
                return file;
            }
        }
        return null;
    }
}