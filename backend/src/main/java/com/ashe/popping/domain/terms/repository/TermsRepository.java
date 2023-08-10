package com.ashe.popping.domain.terms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.terms.entity.Terms;

public interface TermsRepository extends JpaRepository<Terms, Long> {
	List<Terms> findAll();

	Optional<Terms> findByTermsId(Long termsId);

	Long deleteByTermsId(Long termsId);
}
