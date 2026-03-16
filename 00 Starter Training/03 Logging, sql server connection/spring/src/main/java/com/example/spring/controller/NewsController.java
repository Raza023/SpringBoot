package com.example.spring.controller;

import com.example.spring.data.News;
import com.example.spring.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/allNews")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping("/saveNews")
    public News saveNews(@RequestBody News news) {
        return newsService.save(news);
    }

    @PutMapping("/updateNews")
    public News updateNews(@RequestBody News news) {
        return newsService.updateNews(news);
    }

    @DeleteMapping("/deleteNews/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNewsById(id);
    }

    @GetMapping("/getNewsById/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    @GetMapping("/getNewsByCategory/{category}")
    public List<News> getNewsByCategory(@PathVariable String category) {
        return newsService.getNewsByCategory(category);
    }

    @GetMapping("/getNewsByIdAndCategory/{id}/{category}")
    public News getNewsByIdAndCategory(@PathVariable Long id, @PathVariable String category) {
        return newsService.getNewsByIdAndCategory(id, category);
    }
}
