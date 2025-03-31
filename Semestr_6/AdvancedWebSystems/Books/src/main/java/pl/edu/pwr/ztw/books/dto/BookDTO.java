package pl.edu.pwr.ztw.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String title;
    private Long authorId;
    private Integer pages;
}
