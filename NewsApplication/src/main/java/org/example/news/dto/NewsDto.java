package org.example.news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class NewsDto {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
    private long categoryId;

    @Override
    public String toString() {
        return "\n{" + "\n" +
                "\"id\": " + id + ",\n" +
                "\"title\" :" + title + ",\n" +
                "\"text\": " + text + ",\n" +
                "\"date\": " + dateTime + "\n" +
                "\"categories\": " + categoryId + "\n" +
                "}";
    }
}
