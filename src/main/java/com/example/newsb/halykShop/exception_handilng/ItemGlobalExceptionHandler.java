package com.example.newsb.halykShop.exception_handilng;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ItemGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ItemIncorrectData> handleException(NoSuchItemException exception){
        ItemIncorrectData data = new ItemIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ItemIncorrectData> handleException(Exception exception){
        ItemIncorrectData data = new ItemIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
