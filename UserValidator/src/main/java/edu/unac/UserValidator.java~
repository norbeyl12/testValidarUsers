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

    public List<String> validate(File file) {
        List<String> invalidLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    lineNumber++;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    invalidLines.add("Línea " + lineNumber + ": debe tener exactamente 3 campos");
                    lineNumber++;
                    continue;
                }

                String username = parts[0].trim();
                String email = parts[1].trim();
                String ageStr = parts[2].trim();

                if (username.isEmpty() || username.contains(" ")) {
                    invalidLines.add("Línea " + lineNumber + ": username inválido");
                }

                int atIndex = email.indexOf("@");
                int dotIndex = email.indexOf(".", atIndex);
                if (atIndex <= 0 || dotIndex <= atIndex + 1) {
                    invalidLines.add("Línea " + lineNumber + ": email inválido");
                }

                try {
                    int age = Integer.parseInt(ageStr);
                    if (age < 0) {
                        invalidLines.add("Línea " + lineNumber + ": edad negativa");
                    }
                } catch (NumberFormatException e) {
                    invalidLines.add("Línea " + lineNumber + ": edad no es numérica");
                }

                lineNumber++;
            }
        } catch (IOException e) {
            invalidLines.add("Error al leer el archivo: " + e.getMessage());
        }

        return invalidLines;
}
}