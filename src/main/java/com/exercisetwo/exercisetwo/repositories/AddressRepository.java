package com.exercisetwo.exercisetwo.repositories;

import com.exercisetwo.exercisetwo.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}