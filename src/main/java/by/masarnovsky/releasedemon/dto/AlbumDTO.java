package by.masarnovsky.releasedemon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlbumDTO {
    private Integer id;
    private String title;
    private LocalDate releaseDate;
    private Integer artistId;
    private String artistName;
}
