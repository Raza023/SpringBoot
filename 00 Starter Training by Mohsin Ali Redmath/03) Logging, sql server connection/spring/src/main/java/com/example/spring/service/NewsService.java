package com.example.spring.service;

import com.example.spring.data.News;
import com.example.spring.data.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public News getNewsById(Long id) {
        logger.info("Getting new by id {}.", id);
        return newsRepository.findByNewsID(id).orElse(null);
    }

    public List<News> getAllNews() {
        logger.info("Getting all news");
        return newsRepository.findAll();
    }

    public List<News> getNewsByCategory(String category) {
        return newsRepository.findByNewsCategory(category);
    }

    public void deleteNewsById(Long id) {
        logger.info("Deleting new with id {}", id);
        newsRepository.deleteById(id);
    }

    public News updateNews(News news) {
        logger.info("Updating new {}", news);
        return newsRepository.save(news);
    }

    public void deleteAllNews() {
        logger.info("Deleting all news");
        newsRepository.deleteAll();
    }

    public News getNewsByIdAndCategory(Long id, String category) {
        logger.info("Getting new by id {} and category {}", id, category);
        return newsRepository.findByNewsIDAndNewsCategory(id, category);
    }

    public News save(News news) {
        logger.info("Saving new news: {}", news);
        return newsRepository.save(news);
    }
}
