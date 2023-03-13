package seedu.clialgo.command;

import org.junit.jupiter.api.Test;
import seedu.clialgo.TopicManager;
import seedu.clialgo.Ui;
import seedu.clialgo.storage.FileManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidTopicCommandTest {

    /**
     * Deletes folder at <code>pathToFolder</code> and all the files within.
     * @param pathToFolder The <code>File</code> representing the folder to delete.
     */
    public void deleteAll(File pathToFolder) {
        for (File f : Objects.requireNonNull(pathToFolder.listFiles())) {
            if (!f.delete()) {
                System.out.println("Delete failed");
            }
        }
        if (!pathToFolder.delete()) {
            System.out.println("Delete failed");
        } else {
            System.out.println("Delete successful");
        }
    }

    /**
     * Checks the <code>equals</code> method of the <code>InvalidTopicCommand</code> class.
     * Inputs two equal <code>InvalidTopicCommand</code> objects and expects the method to return true.
     */
    @Test
    void equals_checkEqualInvalidTopicCommand_expectTrue() {
        String inputTopic = "dummyTopic";
        InvalidTopicCommand actualInvalidTopicCommand = new InvalidTopicCommand(inputTopic);
        InvalidTopicCommand expectedInvalidTopicCommand = new InvalidTopicCommand("dummyTopic");
        assertTrue(expectedInvalidTopicCommand.equals(actualInvalidTopicCommand));
    }

    /**
     * Checks the <code>equals</code> method of the <code>InvalidTopicCommand</code> class.
     * Inputs two unequal <code>InvalidTopicCommand</code> objects and expects the method to return false.
     */
    @Test
    void equals_checkUnequalInvalidTopicCommand_expectFalse() {
        String firstTopic = "dummyTopic";
        String secondTopic = "diffDummyTopic";
        InvalidTopicCommand firstInvalidTopicCommand = new InvalidTopicCommand(firstTopic);
        InvalidTopicCommand secondInvalidTopicCommand = new InvalidTopicCommand(secondTopic);
        assertFalse(firstInvalidTopicCommand.equals(secondInvalidTopicCommand));
    }

    /**
     * Checks the <code>execute</code> method of the <code>InvalidTopicCommand</code> class.
     * Inputs repeated name for <code>InvalidTopicCommand</code> object and expects the method
     * to print message for unsuccessful adding of note due to invalid topic.
     */
    @Test
    void execute_checkMethodExecution_expectAddUnsuccessfulDueToInvalidTopicMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());
        fileManager.initialize();

        ArrayList<String> invalidTopics = new ArrayList<>(
                Arrays.asList("sorting", "linked_list", "graph_structures", "binary_heap", "hash_table",
                        "graph_traversal", "binary_search_tree", "ss_shortest_path", "union_find_ds",
                        "minimum_spanning_tree", "Sorting", "Linked_list", "Graph_structures", "Binary_heap",
                        "Hash_table", "Graph_traversal", "Binary_search_tree", "Ss_shortest_path", "Union_find_ds",
                        "Minimum_spanning_tree", "LINKED LIST", "GRAPH STRUCTURES", "BINARY HEAP", "HASH TABLE",
                        "GRAPH TRAVERSAL", "BINARY SEARCH TREE", "SS SHORTEST PATH", "UNION FIND DS",
                        "MINIMUM SPANNING TREE", "BITMASK", "SEGMENT_TREE", "SUFFIX_TREE", "CONVEX_HULL",
                        "NETWORK_FLOW", "MIN_VERTEX_COVER")
        );

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        for (String invalidTopic : invalidTopics) {
            // Generate the exected output
            if (os.contains("Windows")) {
                expectedOutput = String.format("======================================================\r\n" +
                        "Unsuccessful! " + invalidTopic + " is not a topic in CS2040C.\r\n" +
                        "Type 'help c/add' for assistance.\r\n" +
                        "======================================================\r\n");
            } else {
                expectedOutput = String.format("======================================================\n" +
                        "Unsuccessful! " + invalidTopic + " is not a topic in CS2040C.\n" +
                        "Type 'help c/add' for assistance.\n" +
                        "======================================================\n");
            }

            // Generate the actual output
            new InvalidTopicCommand(invalidTopic).execute(topicManager, ui, fileManager);
            assertEquals(expectedOutput, outContent.toString());
            outContent.reset();
        }

        deleteAll(new File(testDataPath));
    }

}
