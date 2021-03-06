package by.masarnovsky.releasedemon.mapper;

import by.masarnovsky.releasedemon.dto.ArtistDTO;
import by.masarnovsky.releasedemon.entity.Artist;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = AlbumMapper.class)
public interface ArtistMapper {
    ArtistDTO entityToDto(Artist artist);

    Artist dtoToEntity(ArtistDTO dto);

    List<ArtistDTO> entitySetToDtoSet(Set<Artist> artists);
}
