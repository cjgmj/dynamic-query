package com.cjgmj.dynamicQuery.query;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@Component
public class DynamicQuery<T> {

	public Specification<T> fieldFilter(List<FieldFilter> filters) {
		return (obj, cq, cb) -> {
			final List<Predicate> predicates = Collections.emptyList();

			filters.forEach(filter -> {
				predicates.add(filter.getQueryPredicate().getPredicateLike(cb, obj, filter));
			});

			return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

}
