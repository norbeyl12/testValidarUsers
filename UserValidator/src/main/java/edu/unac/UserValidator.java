package edu.unac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public List<String> getInvalidUserLines(String filePath) throws IOException {
        List<String> invalidLines = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;

            String[] parts = trimmed.split(",", -1);

            if (parts.length != 3) {
                invalidLines.add(line);
                continue;
            }

            String username = parts[0].trim();
            String email = parts[1].trim();
            String ageStr = parts[2].trim();

            if (username.isEmpty() || username.contains(" ")) {
                invalidLines.add(line);
                continue;
            }

            if (!isValidEmail(email)) {
                invalidLines.add(line);
                continue;
            }

            if (!isValidNonNegativeInteger(ageStr)) {
                invalidLines.add(line);
            }
        }

        return invalidLines;
    }

    private boolean isValidEmail(String email) {
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");
        return atIndex > 0 && dotIndex > atIndex;
    }

    private boolean isValidNonNegativeInteger(String value) {
        try {
            int num = Integer.parseInt(value);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
