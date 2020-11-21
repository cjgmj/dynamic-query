package com.cjgmj.dynamicQuery.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@Component
public class QuerySpecification<T> {

	public Specification<T> restrictiveSearch(List<FieldFilter<?>> fieldFilters) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicatesList = this.getPredicateList(criteriaBuilder, root, fieldFilters);

			return predicatesList.isEmpty() ? null
					: criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
		};
	}

	public Specification<T> nonRestrictiveSearch(List<FieldFilter<?>> fieldFilters) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicatesList = this.getPredicateList(criteriaBuilder, root, fieldFilters);

			return predicatesList.isEmpty() ? null
					: criteriaBuilder.or(predicatesList.toArray(new Predicate[predicatesList.size()]));
		};
	}

	private List<Predicate> getPredicateList(CriteriaBuilder criteriaBuilder, Root<?> root,
			List<FieldFilter<?>> fieldFilters) {
		final List<Predicate> predicatesList = new ArrayList<>();

		Optional.ofNullable(fieldFilters).orElse(new ArrayList<>()).forEach(filter -> {
			predicatesList.add(filter.getQueryPredicate().getPredicate(criteriaBuilder, root, filter));
		});

		return predicatesList;
	}

}
