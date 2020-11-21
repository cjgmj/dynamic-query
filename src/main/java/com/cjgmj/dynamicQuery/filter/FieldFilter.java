package com.cjgmj.dynamicQuery.filter;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class FieldFilter<T> {

	private String field;
	private T value;

	public abstract QueryPredicate getQueryPredicate();

	public Expression<String> getExpression(Root<?> root) {
		Expression<String> expression = null;

		final String[] relatedAttribute = this.getField().split("[.]");

		if (relatedAttribute.length == 1) {
			expression = root.get(this.getField());
		} else {
			Join<Object, Object> join = root.join(relatedAttribute[0]);
			for (int i = 1; i < relatedAttribute.length - 1; i++) {
				join = join.join(relatedAttribute[i]);
			}
			expression = join.get(relatedAttribute[relatedAttribute.length - 1]);
		}

		return expression;
	}

}
