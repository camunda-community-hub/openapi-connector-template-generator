package org.camunda.community;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Hello world!
 *
 */
public class PostProcessor {
    private Path path;
    private Optional<String> prefix;

    public PostProcessor(Path path) {
        this.path = path;
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path:" + path.toString() + " does not exist");
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path:" + path.toString() + " is not a directory");
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java -jar target/postprocessor.jar [output path]");
        }
        Path file = FileSystems.getDefault().getPath(args[0]);
        PostProcessor processor = new PostProcessor(file);
        processor.postprocess();
    }

    public void postprocess() throws IOException {
        // removeBadMarkdownChars();
        // insertFavicons();
        // beautifyApiFile();
        insertFeelDefinitionsIntoApiFile();
        // applyPrefix();
    }

    private void removeBadMarkdownChars() {
        // Probably not needed
    }

    private void insertFavicons() {
    }

    private void beautifyApiFile() {
    }

    private void insertFeelDefinitionsIntoApiFile() throws IOException {
        HashMap<String, String> includeMap = new HashMap<>();

        Files.walk(this.path.resolve("includes")).forEach(
                p -> {
                    String fileName = p.getFileName().toString();
                    int index = fileName.lastIndexOf(".feel");
                    if (index < 0) {
                        return;
                    }
                    fileName = fileName.substring(0, index);
                    try {
                        includeMap.put(fileName, Files.readString(p));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        ObjectMapper om = new ObjectMapper();
        Pattern pattern = Pattern.compile("^=<(.*)>$");
        Files.walk(this.path.resolve("element-templates"), 1).filter(p -> Files.isRegularFile(p)).forEach(
                p -> {
                    try {
                        JsonNode root = om.readTree(p.toFile());
                        JsonNode properties = root.at("/properties");
                        for (JsonNode property : properties) {
                            JsonNode value = property.get("value");
                            if (value == null) {
                                continue;
                            }

                            Matcher m = pattern.matcher(value.asText());
                            if (!m.matches()) {
                                continue;
                            }
                            String newString = m.replaceAll(matchResult -> {
                                System.out.println(matchResult.group(1));
                                return "=" + includeMap.get(matchResult.group(1));});
                            ObjectNode propertyObj = (ObjectNode) property;
                            propertyObj.put("value", newString);
                        }
                        om.writeValue(p.toFile(), root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void applyPrefix() {
    }
}
