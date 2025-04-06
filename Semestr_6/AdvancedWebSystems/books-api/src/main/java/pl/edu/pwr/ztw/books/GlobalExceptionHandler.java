package pl.edu.pwr.ztw.books;//package pl.edu.pwr.ztw.books;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ErrorResponse.class)
//    public ResponseEntity<ApiError> handleLoanReturnException(ErrorResponse ex) {

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

////        Map<String, String> errorDetails = new LinkedHashMap<>();
////        errorDetails.put("status", Optional.ofNullable(ex.getStatus()).orElse("UNKNOWN_STATUS"));
////        errorDetails.put("source", Optional.ofNullable(ex.getSource()).orElse("UNKNOWN_SOURCE"));
////        errorDetails.put("title", Optional.ofNullable(ex.getTitle()).orElse("UNKNOWN_TITLE"));
////        errorDetails.put("details", Optional.ofNullable(ex.getDetails()).orElse("No additional details."));
//        ApiError error = new ApiError(
//                Optional.ofNullable(ex.getStatus()).orElse("UNKNOWN_STATUS"),
//                Optional.ofNullable(ex.getSource()).orElse("UNKNOWN_SOURCE"),
//                Optional.ofNullable(ex.getTitle()).orElse("UNKNOWN_TITLE"),
//        Optional.ofNullable(ex.getDetails()).orElse("No additional details.")
//        );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
//        Map<String, String> errorDetails = new LinkedHashMap<>();
//        errorDetails.put("status", "400");
//        errorDetails.put("source", "UNKNOWN_SOURCE");
//        errorDetails.put("title", "UNKNOWN_TITLE");
//        errorDetails.put("details", ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
//    }
//@ExceptionHandler(Exception.class)
//public ResponseEntity<Map<String, String>> handleLoanReturnException(Exception ex) {
//    return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
//}
//}

//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleLoanReturnException(Exception ex) {
//        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
//   }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleErrorResponse(ErrorResponse ex) {
//        return ResponseEntity.status(HttpStatus.valueOf(Integer.parseInt(ex.getStatus()))).body(ex);
//    }
//}

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleLoanReturnException(Exception ex) {
//        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
//    }
//}
