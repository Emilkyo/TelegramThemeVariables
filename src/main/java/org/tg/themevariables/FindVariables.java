package org.tg.themevariables;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindVariables {
    private static void downloadFile(String fileURL, String saveDir) {
        try (InputStream in = new URL(fileURL).openStream()) {
            System.out.println("Getting file from the repository");
            FileOutputStream fos = new FileOutputStream(saveDir + "/ThemeColors.java");
            int length;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
            System.out.println("File received successfully \n");
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        String fileURL = "https://raw.githubusercontent.com/DrKLO/Telegram/master/TMessagesProj" +
                "/src/main/java/org/telegram/ui/ActionBar/ThemeColors.java";
        String saveDir = "./parse/";

        final String fileName = saveDir + "ThemeColors.java";
        final String methodName = "createColorKeysMap";

        downloadFile(fileURL, saveDir);

        List<String> variablesList = readInFile(fileName, methodName);
        List<String> result = parseData(variablesList);

        writeToFile(fileName, result);
    }
}