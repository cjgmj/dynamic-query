package com.github.cjgmj.dynamicquery.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.github.cjgmj.dynamicquery.modifier.FieldOrder;
import com.github.cjgmj.dynamicquery.modifier.ValueFilter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * It is necessary to call {@code getQuerySpecification} method to get a {@code
 * QuerySpecification} instance.
 * <p>
 * 
 * The {@code QuerySpecification} is an interface that provides methods to add
 * AND conditions using {@code restrictiveFilters} method or add OR conditions
 * using {@code nonRestrictiveFilters} method. It is possible use both at the
 * same time to get the proper where clause.
 * <p>
 * 
 * Also, this interface provide the {@code orderBy} method to indicate the query
 * order.
 * <p>
 * 
 * Once the filters and order is defined, the predicate could be obtained using
 * the {@code getPredicate} method.
 * 
 * @author cjgmj
 *
 * @param <T> the type of the entity
 */
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
