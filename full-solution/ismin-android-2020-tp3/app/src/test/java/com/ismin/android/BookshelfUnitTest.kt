package com.ismin.android

import com.ismin.android.domain.entities.Book
import com.ismin.android.domain.services.Bookshelf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookshelfUnitTest {
    private val theLordOfTheRings = Book(
        title = "The Lord of the Rings",
        author = "J. R. R. Tolkien",
        date = "1954-02-15"
    )

    private val theHobbit = Book(
        title = "The Hobbit",
        author = "J. R. R. Tolkien",
        date = "1937-09-21"
    )
    private val aLaRechercheDuTempsPerdu = Book(
        title = "À la recherche du temps perdu",
        author = "Marcel Proust",
        date = "1927"
    )

    private lateinit var bookshelf: Bookshelf

    @Before
    fun setup() {
        bookshelf = Bookshelf()
    }

    @Test
    fun getBook_returns_stored_book() {
        bookshelf.addBook(theLordOfTheRings)

        assertEquals(bookshelf.getBook("The Lord of the Rings"), theLordOfTheRings)
    }

    @Test
    fun getAllBooks_returns_all_stored_books() {
        bookshelf.addBook(theLordOfTheRings)
        bookshelf.addBook(theHobbit)
        bookshelf.addBook(aLaRechercheDuTempsPerdu)

        assertEquals(
            bookshelf.getAllBooks(),
            arrayListOf(theHobbit, theLordOfTheRings, aLaRechercheDuTempsPerdu)
        )
    }

    @Test
    fun getBooksOf_returns_all_books_with_input_author() {
        bookshelf.addBook(theLordOfTheRings)
        bookshelf.addBook(theHobbit)
        bookshelf.addBook(aLaRechercheDuTempsPerdu)

        assertEquals(
            bookshelf.getBooksOf("J. R. R. Tolkien"),
            arrayListOf(theHobbit, theLordOfTheRings)
        )
    }

    @Test
    fun getTotalNumberOfBooks_returns_number_of_stored_books() {
        bookshelf.addBook(theLordOfTheRings)
        bookshelf.addBook(theHobbit)
        bookshelf.addBook(aLaRechercheDuTempsPerdu)

        assertEquals(bookshelf.getTotalNumberOfBooks(), 3)
    }

    @Test
    fun should_not_duplicate_a_book_already_stored() {
        bookshelf.addBook(theLordOfTheRings)
        assertEquals(bookshelf.getTotalNumberOfBooks(), 1)

        bookshelf.addBook(theLordOfTheRings)
        assertEquals(bookshelf.getTotalNumberOfBooks(), 1)
    }
}
