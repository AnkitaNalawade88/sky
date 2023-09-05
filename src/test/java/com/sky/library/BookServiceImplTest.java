package com.sky.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceImplTest {

    private BookRepositoryStub bookRepositoryStub;
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        bookRepositoryStub = new BookRepositoryStub();
        bookService = new BookServiceImpl(bookRepositoryStub);
    }


    @Test
    public void testRetrieveExistingBook() throws BookNotFoundException {
        Book book = bookService.retrieveBook("BOOK-GRUFF472");
        assertNotNull(book);
        assertEquals("The Gruffalo", book.getTitle());
    }

    @Test
    public void testRetrieveNonExistingBook() {
        assertThrows(BookNotFoundException.class, () -> bookService.retrieveBook("INVALID-TEXT"));
    }

    @Test
    public void testGetBookSummary() throws BookNotFoundException {
        String summary = bookService.getBookSummary("BOOK-POOH222");
        assertNotNull(summary);
        assertEquals(summary, ("[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends ..."));
        assertTrue(summary.contains("[BOOK-POOH222] Winnie The Pooh "));
    }

    @Test
    public void testGetBookSummaryWithLongReview() throws BookNotFoundException {
        String summary = bookService.getBookSummary("BOOK-WILL987");
        assertNotNull(summary);
        assertTrue(summary.contains("With the arrival of spring and fine weather outside,"));
        assertTrue(summary.endsWith("..."));
    }
}
