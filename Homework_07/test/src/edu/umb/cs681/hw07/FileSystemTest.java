package edu.umb.cs681.hw07;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileSystemTest {

    private LocalDateTime now = LocalDateTime.now();

    private Directory[] LinkedListToArray(LinkedList<Directory> rootList) {
        Directory[] rootArray = new Directory[rootList.size()];
        int index = 0;
        for (Directory root : rootList) {
            rootArray[index++] = root;
        }
        return rootArray;
    }

    @Test
    public void CheckForGetRootDirectory() {
        FileSystem fileSystem = FileSystem.getFileSystem();
        Directory[] expected = {};
        Directory[] actual = this.LinkedListToArray(fileSystem.getRootDirectory());
        assertArrayEquals(expected, actual);
    }

    @Test
    public void checkFileSystemForSingleInstance() {
        FileSystem fileSystem1 = FileSystem.getFileSystem();
        FileSystem fileSystem2 = FileSystem.getFileSystem();
        assertSame(fileSystem1, fileSystem2);
    }

    @Test
    public void CheckForAddRootDirectory() {
        Directory root = new Directory(null, "Root", 0, this.now);
        Directory[] expected = {root};

        FileSystem fileSystem = FileSystem.getFileSystem();
        fileSystem.appendRootDirectory(root);
        LinkedList<Directory> rootDir = fileSystem.getRootDirectory();
        Directory[] actual = this.LinkedListToArray(rootDir);

        assertArrayEquals(expected, actual);
    }


    @Test
    public void checkThreadSafety() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            FileSystem fs1 = FileSystem.getFileSystem();
            assertNotNull(fs1);
        });

        Thread thread2 = new Thread(() -> {
            FileSystem fs2 = FileSystem.getFileSystem();
            assertNotNull(fs2);
        });

        Thread thread3 = new Thread(() -> {
            FileSystem fs3 = FileSystem.getFileSystem();
            assertNotNull(fs3);
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        FileSystem fs1 = FileSystem.getFileSystem();
        FileSystem fs2 = FileSystem.getFileSystem();
        assertSame(fs1, fs2);
    }
}
