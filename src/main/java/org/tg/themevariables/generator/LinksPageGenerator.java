package org.tg.themevariables.generator;

import org.tg.themevariables.parser.HTMLFileLister;

import static org.tg.themevariables.LocalConstants.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.slf4j.Logger;

/**
 * <p> 1. get directories with HTML pages
 * <p> 2. pre-process for creating a link sheet
 * <p> 3. create a link page using a template
 * <p> 4. find the insertion location
 * <p> 5. create HTML code with a list of links
 * <p> 6. insert the HTML code into the links page
 */
public class LinksPageGenerator {
    private static List<String> linksList;
    private static final Logger logger = Logger.getLogger(LinksPageGenerator.class.getName());
//    private static final Logger logger = LoggerFactory.getLogger
    public static void main(String[] args) {
        List<String> htmlFilesList = HTMLFileLister.getHTMLFiles(PROJECT_PATH);
        linksList = preprocessData(htmlFilesList);

        try {
            replaceLinks();
//            insertHTMLFileList(TEMPLATE_FILE_PATH, OUTPUT_FILE_PATH, linksList);
            logger.info("The list of HTML files was successfully inserted into the file:" + OUTPUT_FILE_PATH);
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    private static void replaceLinks() {
        try {
            // Чтение содержимого HTML-файла
            String templateContent = readFromFile(TEMPLATE_FILE_PATH);

            // Определение места вставки (замены)

//            String tagToReplaceAfter = "<div id=\"generated_links\">";
//            String tagRegex = Pattern.quote(tagToReplaceAfter);
            String insertionPoint = "<!-- Paste links list here -->";
            String tagRegex = Pattern.quote(insertionPoint);

            // Поиск конца тега
            Pattern pattern = Pattern.compile(tagRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(templateContent);

            if (matcher.find()) {
                // Замена данных после найденного тега
                int replaceStartIndex = matcher.end();
                String replacement = generatePageLinks(linksList);

                String updatedHtmlContent = templateContent.substring(0, replaceStartIndex) +
                        replacement + templateContent.substring(replaceStartIndex);

                // Запись измененного HTML-кода в файл
                try (FileWriter writer = new FileWriter(OUTPUT_FILE_PATH)) {
                    writer.write(updatedHtmlContent);
                } catch (Exception e) {
                    logger.severe("Error: " + e.getMessage());
                }
                logger.info("The data was successfully replaced in the HTML template after the " + insertionPoint +
                        "tag");
            } else {
                logger.info("Tag " + insertionPoint + " not found in HTML template.");
            }
        } catch (IOException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    private static void insertHTMLFileList(String templateFilePath, String outputFilePath, List<String> htmlFiles) throws IOException {
        // Reading HTML Template Contents
        String templateContent = readFromFile(templateFilePath);

        // Finding a place to insert a list (with the identifier)
        String insertionPoint = "<!-- Paste links list here -->";
        int index = templateContent.indexOf(insertionPoint);

        if (index != -1) {
            // Вставка списка файлов в место с идентификатором "fileList"
            String fileListHtml = generatePageLinks(htmlFiles);
            String modifiedTemplate = templateContent.substring(0, index) + fileListHtml
                    + templateContent.substring(index + insertionPoint.length());

            // Запись измененного HTML-кода в файл
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(modifiedTemplate);
            }
        } else {
            System.out.println("Место для вставки списка файлов не найдено в HTML-шаблоне.");
        }
    }

    private static String readFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }
        logger.info("File read.");
        return content.toString();
    }

    private static String generatePageLinks(List<String> linksList) {
        String tabs = "\t\t\t\t\t";
        StringBuilder fileListHtml = new StringBuilder(tabs)
//                .append("<!-- Paste links list here -->\n")
                .append(tabs).append("<div id=\"generated_links\">\n");
        for (String link : linksList) {
            fileListHtml.append(tabs).append("\t<p><a href=\"").append(link)
                    .append("\" target=\"_blank\">").append(link).append("</a></p>\n");
        }
        fileListHtml.append("\t\t\t\t\t</div>");
        logger.info("Page Links generated.");
        return fileListHtml.toString();
    }

    private static List<String> preprocessData(List<String> htmlFiles) {
        List<String> links = new ArrayList<>(htmlFiles.size());
        for (String htmlFile : htmlFiles) {

            htmlFile = htmlFile.substring(PROJECT_PATH.length());
            if (htmlFile.endsWith("index.html"))
                htmlFile = htmlFile.substring(0, htmlFile.length() - 10);
            htmlFile = htmlFile.replace("\\", "/");
            links.add(htmlFile);
        }

        // Sort list by directory length and then alphabetically
        links.sort(Comparator
                .comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        logger.info("Data preprocessed.");
        return links;
    }
}
