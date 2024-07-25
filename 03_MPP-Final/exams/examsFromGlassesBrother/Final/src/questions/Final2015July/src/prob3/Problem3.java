package questions.Final2015July.src.prob3;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import questions.Final2015July.src.helperclasses.Book;
import questions.Final2015July.src.helperclasses.BookCopy;
import questions.Final2015July.src.helperclasses.CheckoutRecord;
import questions.Final2015July.src.helperclasses.LibraryMember;
import questions.Final2015July.src.helperclasses.LibrarySystemException;
import questions.Final2015July.src.helperclasses.TestData;

public class Problem3 {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Problem3 p = new Problem3();
		List<LibraryMember> members = TestData.INSTANCE.getMembers();
		p.books = TestData.INSTANCE.getAllBooks().iterator();

	}

	Iterator<Book> books;

	public LibraryMember detectIfWinnerDuringCheckout(List<LibraryMember> mems) {
		// return null;
		// fix this
//		 return mems.stream().filter(mem ->
//		 mem.checkout(books.next().getNextAvailableCopy(), LocalDate.now(),
//		 LocalDate.of(2015, 9, 1))
//		 .getCheckoutRecordEntries().size() == 10)
//		 .findFirst().orElse(null);
//		

		// fix this
		return mems.stream().filter(mem -> convert2Unchecked(mem, books.next().getNextAvailableCopy(), LocalDate.now(),
				LocalDate.of(2015, 9, 1)).getCheckoutRecordEntries().size() == 10).findFirst().orElse(null);

	}
//
	public static CheckoutRecord convert2Unchecked(LibraryMember mem, BookCopy copy, LocalDate checkout,
			LocalDate due) {
		try {
			return mem.checkout(copy, checkout, due);
		} catch (LibrarySystemException e) {
			throw new RuntimeException();
		}

	}

	}
