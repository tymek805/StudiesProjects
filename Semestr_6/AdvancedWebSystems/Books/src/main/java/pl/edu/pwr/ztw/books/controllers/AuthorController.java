package pl.edu.pwr.ztw.books.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.models.Author;
import pl.edu.pwr.ztw.books.repositories.IAuthorRepository;
import pl.edu.pwr.ztw.books.ApiResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    private final IAuthorRepository authorRepository;

    public AuthorController(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<ApiResponse<?>> getAuthors() {
        return ResponseEntity.ok(new ApiResponse<>(authorRepository.findAll(), "success", "Authors retrieved successfully"));
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity<ApiResponse<?>> getAuthor(@PathVariable Long id) {
        if (authorRepository.existsById(id)){
            return ResponseEntity.ok(new ApiResponse<>(authorRepository.findById(id), "success", "Author retrieved successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Author not found"));
    }

    @PostMapping("/author")
    public ResponseEntity<ApiResponse<?>> addAuthor(@RequestBody AuthorDTO authorDTO) {
        Author author = new Author(authorDTO.getName(), authorDTO.getSurname());
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(new ApiResponse<>(savedAuthor, "success", "Author added successfully"));
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<ApiResponse<?>> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        if (authorRepository.existsById(id)) {
            Author oldAuthor = authorRepository.findById(id).orElseThrow();

            if (authorDTO.getName() != null)
                oldAuthor.setName(authorDTO.getName());
            if (authorDTO.getSurname() != null)
                oldAuthor.setSurname(authorDTO.getSurname());

            Author updatedAuthor = authorRepository.save(oldAuthor);
            return ResponseEntity.ok(new ApiResponse<>(updatedAuthor, "success", "Author updated successfully"));
        }

        return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Author not found"));
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAuthor(@PathVariable Long id) {
        if (!authorRepository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Author not found"));
        }

        authorRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "success", "Author deleted successfully"));
    }
}
