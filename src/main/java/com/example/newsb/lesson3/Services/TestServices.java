package com.example.newsb.lesson3.Services;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

public interface TestServices {

    String getTitle();
    String getShortContent();
    String getContent();
    String getPictureURL();
    Timestamp getPostDate();

    void setTitle(String title);
    void setShortContent(String shortContent);
    void setContent(String content);
    void setPictureURL(String pictureURL);
}
