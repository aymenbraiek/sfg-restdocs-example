package com.biat.sfgrestdocsexample.Repository;

import com.biat.sfgrestdocsexample.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository  extends PagingAndSortingRepository<Beer, UUID> {
}
