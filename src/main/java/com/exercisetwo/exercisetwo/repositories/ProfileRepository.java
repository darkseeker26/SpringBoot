package com.exercisetwo.exercisetwo.repositories;

import com.exercisetwo.exercisetwo.domain.Profile;
import com.exercisetwo.exercisetwo.dtos.ProductSummary;
import com.exercisetwo.exercisetwo.dtos.ProductSummaryDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {



}