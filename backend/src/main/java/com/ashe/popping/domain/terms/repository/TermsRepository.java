package com.ashe.popping.domain.terms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.terms.entity.Terms;

public interface TermsRepository extends JpaRepository<Terms, Long> {
	List<Terms> findAll();

	Terms findByTermsId(Long termsId);
}
