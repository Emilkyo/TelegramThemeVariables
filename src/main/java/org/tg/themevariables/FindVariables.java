package org.tg.themevariables;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindVariables {
    private static void downloadFile(String fileURL, String saveDir, String fileName) {
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

    private static List<String> readInFile(String fileName, String methodName) {
        List<String> methodData = new ArrayList<>();
        boolean inMethod = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Find the start of the method
                if (line.contains(methodName + "(")) {
                    inMethod = true;
                    continue;
                }

                // Adding method strings to the list
                if (inMethod) {
                    methodData.add(line);
                }
                // Method completion
                if (line.contains("}")) {
                    inMethod = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return methodData;
    }

    private static List<String> parseData(List<String> variablesList) {
        List<String> valuesInQuotes = new ArrayList<>();
        for (String line : variablesList) {
            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                valuesInQuotes.add(matcher.group(1));
            }
        }
        return valuesInQuotes;
    }

    private static void writeToFile(String fileName, List<String> list) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String item : list) {
                writer.write(item + System.lineSeparator());
            }
            System.out.println("Variables successfully written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace(); // I/O Error Handling
        }
        System.out.println(); // Empty line for better readability
    }

    private static String updated() {
        /*
            Check the channel message and determine which application
            then return the application name
        */
        return "android";
    }

    public static void main(String[] args) {
        String app = updated();
        switch (app) {
            case "android":
                factory(Constants.ANDROID);
                break;
            case "androidx":
                factory(Constants.ANDROIDX);
                break;
            case "desktop":
                factory(Constants.DESKTOP);
                break;
            case "unigram":
                factory(Constants.UNIGRAM);
                break;
            case "ios":
                factory(Constants.IOS);
                break;
            case "macos":
                factory(Constants.MACOS);
                break;
        }
    }

    public static void factory(Map<String, String> app) {
        downloadFile(app.get("fileURL"), app.get("saveDir"), app.get("fileName"));
        List<String> variablesList = readInFile(app.get("saveDir") + app.get("fileName"), app.get("methodName"));
        List<String> result = parseData(variablesList);
        writeToFile(app.get("saveDir") + "out.txt", result);
    }
}