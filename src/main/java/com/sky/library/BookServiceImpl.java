package com.sky.library;

import java.util.Arrays;

public class BookServiceImpl implements BookService {

    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
            throw new BookNotFoundException("Book reference must begin with '" + BOOK_REFERENCE_PREFIX + "'");
        }

        Book book = bookRepository.retrieveBook(bookReference);
        if (book == null) {
            throw new BookNotFoundException("Book with reference " + bookReference + " not found");
        }

        return book;
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException {
        Book book = retrieveBook(bookReference);

        String review = book.getReview();
        String[] words = review.split("\\s+");
        int summaryWordCount = Math.min(9, words.length);
        String summary = String.join(" ", Arrays.copyOfRange(words, 0, summaryWordCount));

        if (words.length > 9) {
            summary += " ...";
        }

        return String.format("[%s] %s - %s", book.getReference(), book.getTitle(), summary);
    }
}