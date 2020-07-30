package by.masarnovsky.releasedemon.mapper;

import by.masarnovsky.releasedemon.dto.AlbumDTO;
import by.masarnovsky.releasedemon.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mappings({
            @Mapping(target = "artistId", source = "album.artist.id"),
            @Mapping(target = "artistName", source = "album.artist.name")
    })
    AlbumDTO entityToDto(Album album);

    @Mappings({
            @Mapping(target = "artist.id", source = "dto.artistId"),
            @Mapping(target = "artist.name", source = "dto.artistName")
    })
    Album dtoToEntity(AlbumDTO dto);
}
