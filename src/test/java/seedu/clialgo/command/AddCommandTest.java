package seedu.clialgo.command;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import seedu.clialgo.TopicManager;
import seedu.clialgo.Ui;
import seedu.clialgo.storage.FileManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * JUnit test for the <code>AddCommand</code> class methods.
 */
class AddCommandTest {

    /**
     * Checks the <code>equals</code> method of the <code>AddCommand</code> class.
     * Inputs two equal <code>AddCommand</code> objects and expects the method to return true.
     */
    @Test
    void equals_checkEqualAddCommand_expectTrue() {
        String firstName = "dummyName";
        String secondName = "dummyName";
        String firstPath = "dummyPath";
        String secondPath = "dummyPath";
        AddCommand firstAddCommand = new AddCommand(firstName, firstPath);
        AddCommand secondAddCommand = new AddCommand(secondName, secondPath);
        assertTrue(firstAddCommand.equals(secondAddCommand));
    }

    /**
     * Checks the <code>equals</code> method of the <code>AddCommand</code> class.
     * Inputs two unequal <code>AddCommand</code> objects and expects the method to return false.
     */
    @Test
    void equals_checkUnequalAddCommand_expectFalse() {
        String firstName = "dummyName1";
        String secondName = "dummyName2";
        String firstPath = "dummyPath1";
        String secondPath = "dummyPath2";
        AddCommand firstAddCommand = new AddCommand(firstName, firstPath);
        AddCommand secondAddCommand = new AddCommand(secondName, secondPath);
        assertFalse(firstAddCommand.equals(secondAddCommand));
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs proper inputs for <code>AddCommand</code> object and expects the method
     * to print message for successful adding of note.
     */
    @Test
    void execute_properInput_expectAddSuccessfulMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager();

        String noteName = "queue";
        String noteTopic = "LINKED_LIST";

        new AddCommand(noteName, noteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {
            // This expected output has "File Created" due to the first
            // initialisation of the FileManager in AddCommandTest.
            expectedOutput = "File created\r\n" +
                    "======================================================\r\n" +
                    "Successfully added queue into LINKED_LIST.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "File created\n" +
                    "======================================================\n" +
                    "Successfully added queue into LINKED_LIST.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs invalid topic for <code>AddCommand</code> object and expects the method
     * to print message for unsuccessful adding of note due to invalid topic.
     */
    @Test
    void execute_invalidTopicInput_expectAddUnsuccessfulDueToInvalidTopicMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager();

        String noteName = "queue";
        String noteTopic = "invalidTopic";

        new AddCommand(noteName, noteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {
            expectedOutput = "======================================================\r\n" +
                    "Unsuccessful! invalidTopic is not a topic in CS2040C.\r\n" +
                    "Type 'help c/add' for assistance.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Unsuccessful! invalidTopic is not a topic in CS2040C.\n" +
                    "Type 'help c/add' for assistance.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs repeated name for <code>AddCommand</code> object and expects the method
     * to print message for unsuccessful adding of note due to invalid command.
     */
    @Test
    void execute_repeatedInput_expectAddUnsuccessfulDueToInvalidCommandMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager();

        String noteName = "queue";
        String noteTopic = "LINKED_LIST";

        // Adding the note with same name twice into same topic
        new AddCommand(noteName, noteTopic).execute(topicManager, ui, fileManager);
        new AddCommand(noteName, noteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {

            expectedOutput = "======================================================\r\n" +
                    "Successfully added queue into LINKED_LIST.\r\n" +
                    "======================================================\r\n" +
                    "======================================================\r\n" +
                    "This is the printInvalidCommand method\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Successfully added queue into LINKED_LIST.\n" +
                    "======================================================\n" +
                    "======================================================\n" +
                    "This is the printInvalidCommand method\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, outContent.toString());
    }
}
