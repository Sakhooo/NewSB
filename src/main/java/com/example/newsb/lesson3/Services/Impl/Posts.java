package com.example.newsb.lesson3.Services.Impl;

import com.example.newsb.lesson3.Services.TestServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
@Service
public class Posts implements TestServices {
    private Long id;
    private String title;
    private String shortContent;
    private String content;
    private String pictureURL;
    private Timestamp postDate;


    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getShortContent() {

        return this.shortContent;
    }

    @Override
    public String getContent() {

        return this.content;
    }

    @Override
    public String getPictureURL()
    {
        return this.pictureURL;
    }

    @Override
    public Timestamp getPostDate() {

        return new Timestamp(System.currentTimeMillis());
    }



    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;

    }

    @Override
    public void setContent(String content) {
        this.content=content;

    }

    @Override
    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
