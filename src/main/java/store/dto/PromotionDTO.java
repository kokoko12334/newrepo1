package store.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromotionDTO {
    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public PromotionDTO(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getGet() {
        return get;
    }

    public void setGet(int get) {
        this.get = get;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
