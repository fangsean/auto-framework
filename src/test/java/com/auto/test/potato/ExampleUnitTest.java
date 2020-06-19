package com.auto.test.potato;

import com.auto.common.potato.manager.TaskManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        basicTest();
    }

    private void basicTest() {
        TaskManager taskManager = TaskManager.getInstance();
//        taskManager.add(Task1.class).add(Task2.class).add(Task3.class).dependOn(Task4.class).enqueue();
        taskManager.add(Task1.class).add(Task2.class).dependOn(Task4.class).dependOnAll(Task3.class).enqueue();
//        taskManager.add(Task1.class).add(Task2.class).add(Task3.class).enqueue();
//        taskManager.startWith(Task1.class).then(Task2.class).then(Task3.class).enqueue();
//        taskManager.enqueue();
    }

}