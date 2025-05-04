package edu.unac;

import edu.unac.UserValidator;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    private String path(String filename) {
        return "src/test/resources/" + filename;
    }

    @Test
    void testValidFile() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("valid_users.txt"));
        assertTrue(result.isEmpty(), "No debe haber líneas inválidas");
    }

    @Test
    void testFileWithInvalidUsername() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("invalid_username.txt"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testFileWithInvalidEmail() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("invalid_email.txt"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testFileWithInvalidAge() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("invalid_age.txt"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testFileWithMissingFields() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("missing_fields.txt"));
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testEmptyLinesAndComments() throws IOException {
        List<String> result = validator.getInvalidUserLines(path("empty_and_comments.txt"));
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithTemporaryFile() throws IOException {
        File tempFile = File.createTempFile("users", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("validUser,valid@mail.com,30\n");
            writer.write("noEmailUser,,20\n"); // inválido
        }

        List<String> result = validator.getInvalidUserLines(tempFile.getPath());
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("noEmailUser"));
    }
}