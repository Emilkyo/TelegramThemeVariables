package org.tg.themevariables.parser;

import org.tg.themevariables.LocalConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HTMLFileLister {
    public static List<String> getHTMLFiles(String projectPath) {
        List<String> htmlFiles = new ArrayList<>();
        File directory = new File(projectPath);

        if (directory.exists() && directory.isDirectory()) {
            // Getting a list of files in a directory
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Skip exclude dirs
                    if (file.isDirectory() && LocalConstants.EXCLUDE_DIRS.contains(file.getName())) {
                        continue;
                    }

                    // Checking if the file is an HTML file
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".html")) {
                        htmlFiles.add(file.getAbsolutePath());
                    } else if (file.isDirectory()) {
                        // Call recursively for a subdirectory
                        htmlFiles.addAll(getHTMLFiles(file.getAbsolutePath()));
                    }
                }
            }
        }
        return htmlFiles;
    }
}
