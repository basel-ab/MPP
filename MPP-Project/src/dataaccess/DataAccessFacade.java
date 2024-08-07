package dataaccess;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS,;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ File.separator + "src" + File.separator + "dataaccess" + File.separator + "storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	//implement: other save operations
	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String bookIsbn = book.getIsbn();
		books.put(bookIsbn, book);
		saveToStorage(StorageType.BOOKS, books);	
	}


	//Basil read checkout from stream files
	// @SuppressWarnings("unchecked")
	// public HashMap<String, CheckoutRecord> readCheckoutRecordsMap() {
	// 	return (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CHECKOUTRECORDS);
	// }

	// @SuppressWarnings("unchecked")
	// public HashMap<String, CheckoutRecordEntry> readCheckoutRecordEntriesMap() {
	// return (HashMap<String, CheckoutRecordEntry>)
	// readFromStorage(StorageType.CHECKOUTRECORDENTRIES);
	// }

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup

	// // Basil - checkout load to stream files
	// static void loadCheckoutRecord(CheckoutRecord record) {
	// HashMap<String, CheckoutRecord> records = (HashMap<String,
	// CheckoutRecord>)readFromStorage(StorageType.CHECKOUTRECORDS);
	// if(records == null) records = new HashMap<String, CheckoutRecord>();

	// records.put(record.getId(), record);
	// saveToStorage(StorageType.CHECKOUTRECORDS, records);
	// }

	// static void loadCheckoutRecordEntry(CheckoutRecordEntry entry) {
	// HashMap<String, CheckoutRecordEntry> entries = (HashMap<String,
	// CheckoutRecordEntry>)readFromStorage(StorageType.CHECKOUTRECORDENTRIES);
	// if(entries == null) entries = new HashMap<String, CheckoutRecordEntry>();
	// entries.put(entry.getId(), entry);
	// saveToStorage(StorageType.CHECKOUTRECORDENTRIES, entries);
	// }

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	@Override
	public LibraryMember searchMember(String memberId) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		return mems.get(memberId);
	}

	@Override
	public Book searchBook(String isbn) {
		HashMap<String, Book> book = readBooksMap();
		return book.get(isbn);
	}

	@Override
	public void saveBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String isbn = book.getIsbn();
		books.put(isbn, book);
		saveToStorage(StorageType.BOOKS, books);

	}

}
