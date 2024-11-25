package com.example.myapplication;

import androidx.annotation.NonNull;

public class Post {
    private int id;
    private String title;
    private String body;

    // Конструктор для создания объекта Post
    public Post(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    // Геттер для ID
    public int getId() {
        return id;
    }

    // Геттер для заголовка
    public String getTitle() {
        return title;
    }

    // Геттер для тела поста
    public String getBody() {
        return body;
    }

    // Переопределение метода toString для удобного отображения данных
    @NonNull
    @Override
    public String toString() {
        return "POST " + id + " {\n" +
                "  Title: " + title + ",\n" +
                "  Body: " + body + "\n" +
                "}";
    }
}
