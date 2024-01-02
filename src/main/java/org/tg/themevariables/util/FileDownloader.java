package org.tg.themevariables.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class FileDownloader {
    public static void downloadFile(String fileURL, String saveDir, String fileName) {
        try (InputStream in = new URI(fileURL).toURL().openStream()) {
            System.out.println("Getting file from the repository");

            try (FileOutputStream fos = new FileOutputStream(saveDir + '/' + fileName)) {
                int length;
                byte[] buffer = new byte[1024];
                while ((length = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
            }
            System.out.println("File received successfully \n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
