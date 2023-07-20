package com.anle.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anle.bookservice.command.data.Book;
import com.anle.bookservice.command.data.BookRepository;
import com.anle.commonservice.events.BookRollBackStatusEvent;
import com.anle.commonservice.events.BookUpdateStatusEvent;

@Component
public class BookEventsHandler {
	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookUpdatedEvent event) {
		java.util.Optional<Book> optionalBook = bookRepository.findById(event.getBookId());
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			book.setAuthor(event.getAuthor());
			book.setName(event.getName());
			book.setIsReady(event.getIsReady());
			bookRepository.save(book);
		}
	}

	@EventHandler
	public void on(BookDeletedEvent event) {
		bookRepository.deleteById(event.getBookId());
	}

	@EventHandler
	public void on(BookUpdateStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	public void on(BookRollBackStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

}
