package com.xix.cleanMvpArchitecture.data.cache.fileManager;

import java.io.File;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by xix on 4/15/17.
 */

@RunWith(JUnit4.class)
public class FileManagerTest extends TestCase {
    private FileManager fileManager;
    private String fileContent;
    File directory;
    @Before public void setUp() throws Exception {
        fileManager = new FileManager();
        fileContent = "HELLO";
         directory = new File("cache");
        directory.mkdirs();
    }

    @After public void tearDown() {
        fileManager.clearDirectory(directory);
    }

    @Test public void writeToFile() throws Exception {
        File file =new File(directory,"Test");
        fileManager.writeToFile(file, fileContent);
        assertTrue(file.exists());
    }


    @Test public void readFileContent() throws Exception {
        File file =new File(directory,"Test");
        fileManager.writeToFile(file,fileContent);
        String expectedContent = fileManager.readFileContent(file);
        assertEquals(expectedContent, "HELLO\n");
    }

}