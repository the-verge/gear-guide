package com.verge.mapping;

import java.util.Collection;
import java.util.List;

public interface Mapper<E, D> {

    D entityToDto(E entity);

    List<D> entitiesToDtos(Collection<E> entities);

    E dtoToEntity(D dto);

    List<E> dtosToEntities(Collection<D> dtos);
}
