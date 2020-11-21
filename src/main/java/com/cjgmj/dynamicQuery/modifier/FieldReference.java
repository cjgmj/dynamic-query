package com.cjgmj.dynamicQuery.modifier;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FieldReference {

	private String field;

	public Expression<String> getExpression(Root<?> root) {
		Expression<String> expression = null;

		final String[] relatedAttribute = this.field.split("[.]");

		if (relatedAttribute.length == 1) {
			expression = root.get(this.field);
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
