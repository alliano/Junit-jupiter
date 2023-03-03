package com.java.unit;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "A Queue")
public class QueueTest {
    
    private Queue<String> queue;

    // ini adalah Nested class atau inner class annotasi 
    // @Nested untuk memberitahu bahwa class ini adalah class unit test
    @Nested @DisplayName(value = "When new")
    public class WhenNew {

        @BeforeEach
        public void setup() {
            queue = new LinkedList<String>();
        }

        @Test @DisplayName(value = "When offer 1, the size must be 1")
        public void whenOfferQueue() {
            queue.offer("Alliano");
            Assertions.assertEquals(1, queue.size());

            Assertions.assertEquals("Alliano", queue.poll());
        }

    }
}
