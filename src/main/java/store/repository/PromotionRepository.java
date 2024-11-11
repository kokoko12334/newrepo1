package store.repository;

import store.dto.ProductDTO;
import store.dto.PromotionDTO;
import store.util.FileLoader;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionRepository {
    private final Map<String, PromotionDTO> promotions;

    public PromotionRepository() {
        this.promotions = loadFile();
    }

    private Map<String, PromotionDTO> loadFile() {
        List<String> lines = FileLoader.load("promotions.md");
        Map<String, PromotionDTO> promotions = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i).split(",");
            PromotionDTO promotion = generatePromotionDTO(line);
            promotions.put(promotion.getName(), promotion);
        }
        return promotions;
    }

    private PromotionDTO generatePromotionDTO(String[] line) {
        String name = line[0];
        int buy = Integer.parseInt(line[1]);
        int get = Integer.parseInt(line[2]);
        LocalDateTime startDate = LocalDateTime.parse(line[3]+"T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse(line[4]+"T00:00:00");

        return new PromotionDTO(name, buy, get, startDate, endDate);
    }

    public void save(PromotionDTO promotion) {
        String name = promotion.getName();
        promotions.put(name, promotion);
    }

    public PromotionDTO findById(String name) {
        return promotions.get(name);
    }

    public List<PromotionDTO> findAll() {
        return new ArrayList<>(promotions.values());
    }

    public void update(String name, PromotionDTO updatedPromotion) {
        promotions.put(name, updatedPromotion);
    }

    public void deleteById(String name) {
        promotions.remove(name);
    }
}
