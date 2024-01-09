package org.tg.themevariables.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tg.themevariables.LocalConstants;
import org.tg.themevariables.generator.LinksPageGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

public class FileDownloader {
    private static final Logger logger = LoggerFactory.getLogger(FileDownloader.class);

    public static void downloadFile(String fileURL, String saveDir, String fileName) {
        try {
            // Checking and creating a directory if it does not exist
            createDirectoryIfNotExists(saveDir);

            try (InputStream in = new URI(fileURL).toURL().openStream()) {
                logger.info("Getting file from the repository");
                // Creating a path to save the file
                Path filePath = FileSystems.getDefault().getPath(saveDir, fileName);

                // Copying data from an InputStream to a file
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);

                // Instead of java.nio.file
//            try (FileOutputStream fos = new FileOutputStream(saveDir + '/' + fileName)) {
//                int length;
//                byte[] buffer = new byte[1024];
//                while ((length = in.read(buffer)) != -1) {
//                    fos.write(buffer, 0, length);
//                }
//            }
                logger.info("File received successfully \n");
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path dirPath = FileSystems.getDefault().getPath(directoryPath);

        try {
            Files.createDirectories(dirPath);
        } catch (FileAlreadyExistsException e) {
            // Директория уже существует, игнорируем
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
