//package pl.edu.pwr.ztw.books;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//@Data
//public class ErrorResponse extends RuntimeException {
//    @JsonProperty
//    private String status;
//    @JsonProperty
//    private String source;
//    @JsonProperty
//    private String title;
//    @JsonProperty
//    private String details;
//
//    public ErrorResponse() {}
//
//    public ErrorResponse(String status, String source, String title, String details) {
//        this.status = status;
//        this.source = source;
//        this.title = title;
//        this.details = details;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//}
//
//
//
//
