package com.wild.springrest.controller;

import com.wild.springrest.domain.entity.Book;
import com.wild.springrest.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    private final BookRepository repository;

    public BookController(
            BookRepository injectedRepository
    ) {
        this.repository = injectedRepository;
    }

    @GetMapping("/books")
    public List<Book> index() {
        return repository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book show(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PostMapping("/books/search")
    public List<Book> search(@RequestBody Map<String, String> body)
    {
        String searchTerm = body.get("text");
        return repository.findByTitleContainingOrDescriptionContaining(searchTerm, searchTerm);
    }

    @PostMapping("/books")
    public Book create(@RequestBody Book book)
    {
        return repository.save(book);
    }

    @PutMapping("/books/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book){
        // getting book
        Book bookToUpdate = repository.findById(id).get();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        return repository.save(bookToUpdate);
    }

    @DeleteMapping("/books/{id}")
    public boolean delete(@PathVariable Long id){
        repository.deleteById(id);
        return true;
    }
}
