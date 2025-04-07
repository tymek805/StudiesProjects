package pl.edu.pwr.ztw.books.dto;

public class BookDTO {
    private String title;
    private Long authorId;
    private Integer pages;

    public BookDTO() {
    }

    public BookDTO(String title, Long authorId, Integer pages) {
        this.title = title;
        this.authorId = authorId;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
