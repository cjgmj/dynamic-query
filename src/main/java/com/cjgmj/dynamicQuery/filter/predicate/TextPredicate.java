package com.cjgmj.dynamicQuery.filter.predicate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacementHelper;

public class TextPredicate extends QueryPredicate {

	private static final String REPLACE = "replace";

	public static Expression<String> nonSensitiveText(Expression<String> expression, CriteriaBuilder cb) {
		return TextPredicate.nonSensitiveText(expression, cb, CharacterReplacementHelper.basicReplacements());
	}

	public static Expression<String> nonSensitiveText(Expression<String> expression, CriteriaBuilder cb,
			List<CharacterReplacement> replaces) {
		Expression<String> result = expression;

		result = cb.lower(result);

		for (final CharacterReplacement rc : replaces) {
			result = cb.function(REPLACE, String.class, result, cb.literal(rc.getOldCharacter()),
					cb.literal(rc.getNewCharacter()));
		}

		return result;
	}

	@Override
	protected Expression<?> getExpression(String pattern, CriteriaBuilder cb, Root<?> root) {
		Expression<String> expression = null;

		final String[] arr = pattern.split("[.]");

		if (arr.length == 1) {
			expression = root.get(pattern);
		} else {
			Join<Object, Object> join = root.join(arr[0]);
			for (int i = 1; i < arr.length - 1; i++) {
				join = join.join(arr[i]);
			}
			expression = join.get(arr[arr.length - 1]);
		}

		return nonSensitiveText(expression, cb);
	}
}
