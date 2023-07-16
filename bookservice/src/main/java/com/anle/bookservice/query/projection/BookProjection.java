package com.anle.bookservice.query.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anle.bookservice.command.data.Book;
import com.anle.bookservice.command.data.BookRepository;
import com.anle.bookservice.query.model.BookResponseModel;
import com.anle.bookservice.query.queries.GetAllBooksQuery;
import com.anle.bookservice.query.queries.GetBooksQuery;

@Component
public class BookProjection {
	@Autowired
	private BookRepository bookRepository;

	@QueryHandler
	public BookResponseModel handle(GetBooksQuery getBooksQuery) {
		BookResponseModel model = new BookResponseModel();
		Optional<Book> bookOptional = bookRepository.findById(getBooksQuery.getBookId());
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			BeanUtils.copyProperties(book, model);
		}
		return model;
	}

	@QueryHandler
	public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
		List<Book> listEntity = bookRepository.findAll();
		List<BookResponseModel> listBook = new ArrayList<>();
		listEntity.forEach(s -> {
			BookResponseModel model = new BookResponseModel();
			BeanUtils.copyProperties(s, model);
			listBook.add(model);
		});
		return listBook;

	}
}
