package com.biat.sfgrestdocsexample.web.mappers;

import com.biat.sfgrestdocsexample.domain.Beer;
import com.biat.sfgrestdocsexample.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto dto);
}
