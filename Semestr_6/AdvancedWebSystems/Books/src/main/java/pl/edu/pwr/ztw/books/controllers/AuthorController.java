package pl.edu.pwr.ztw.books.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.models.Author;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.repositories.IAuthorRepository;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    private final IAuthorRepository authorRepository;

    public AuthorController(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<Object> getAuthors() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorRepository.findById(id));
    }

    @PostMapping("/author")
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorRepository.save(author));
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @RequestBody Author author) {
        if (authorRepository.existsById(id)) {
            Author oldAuthor = authorRepository.findById(id).orElseThrow();
            oldAuthor.setName(author.getName());
            oldAuthor.setSurname(author.getSurname());
            return ResponseEntity.ok(authorRepository.save(oldAuthor));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        if (!authorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
