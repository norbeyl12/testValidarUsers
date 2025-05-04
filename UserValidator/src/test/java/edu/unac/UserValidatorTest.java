package edu.unac;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    @Test
    void testValidFile() {
        File file = new File("src/test/resources/valid_users.txt");
        List<String> result = validator.validate(file);
        assertTrue(result.isEmpty(), "No debe haber errores");
    }

    @Test
    void testFileWithInvalidUsername() {
        File file = new File("src/test/resources/invalid_username.txt");
        List<String> result = validator.validate(file);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).contains("username inválido"));
    }
    @Test
    void testFileWithInvalidEmail() {
        File file = new File("src/test/resources/invalid_email.txt");
        List<String> result = validator.validate(file);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).contains("email inválido"));
    }

    @Test
    void testFileWithInvalidAge() {
        File file = new File("src/test/resources/invalid_age.txt");
        List<String> result = validator.validate(file);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).contains("edad"));
    }

    @Test
    void testFileWithMissingFields() {
        File file = new File("src/test/resources/missing_fields.txt");
        List<String> result = validator.validate(file);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).contains("3 campos"));
    }

    @Test
    void testEmptyLinesAndComments() {
        File file = new File("src/test/resources/empty_and_comments.txt");
        List<String> result = validator.validate(file);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithTemporaryFile() throws IOException {
        File tempFile = File.createTempFile("users", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("validUser,valid@mail.com,30\n");
            writer.write("noEmailUser,,20\n");
        }
        List<String> result = validator.validate(tempFile);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("email inválido"));
}
}