package com.cjgmj.dynamicQuery.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.FieldOrder;
import com.cjgmj.dynamicQuery.modifier.ValueFilter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class QuerySpecification<T> {

	private Specification<T> specification;

	public static <T> QuerySpecification<T> getQuerySpecification() {
		return new QuerySpecification<>();
	}

	public QuerySpecification<T> restrictiveFilters(List<ValueFilter<?>> valueFilters) {
		final Specification<T> newSpecification = (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicatesList = this.getPredicateList(criteriaBuilder, root, valueFilters);

			return predicatesList.isEmpty() ? null
					: criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
		};

		this.specification = newSpecification.and(this.specification);

		return this;
	}

	public QuerySpecification<T> nonRestrictiveFilters(List<ValueFilter<?>> valueFilters) {
		final Specification<T> newSpecification = (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicatesList = this.getPredicateList(criteriaBuilder, root, valueFilters);

			return predicatesList.isEmpty() ? null
					: criteriaBuilder.or(predicatesList.toArray(new Predicate[predicatesList.size()]));
		};

		this.specification = newSpecification.or(this.specification);

		return this;
	}

	public QuerySpecification<T> orderBy(List<FieldOrder> orderFilters) {
		final Specification<T> newSpecification = (root, criteriaQuery, criteriaBuilder) -> {
			final List<Order> ordersList = new ArrayList<>();

			Optional.ofNullable(orderFilters).orElse(new ArrayList<>()).forEach(filter -> {
				ordersList.add(filter.getOrder(criteriaBuilder, root));
			});

			if (!ordersList.isEmpty()) {
				criteriaQuery.orderBy(ordersList.toArray(new Order[ordersList.size()]));
			}

			return criteriaQuery.getRestriction();
		};

		this.specification = newSpecification.or(this.specification);

		return this;
	}

	private List<Predicate> getPredicateList(CriteriaBuilder criteriaBuilder, Root<?> root,
			List<ValueFilter<?>> valueFilters) {
		final List<Predicate> predicatesList = new ArrayList<>();

		Optional.ofNullable(valueFilters).orElse(new ArrayList<>()).forEach(filter -> {
			predicatesList.add(filter.getQueryPredicate().getPredicate(criteriaBuilder, root, filter));
		});

		return predicatesList;
	}

}
