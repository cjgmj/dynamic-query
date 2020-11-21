package com.cjgmj.dynamicQuery.predicate;

import java.text.Normalizer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.TextFieldFilter;
import com.cjgmj.dynamicQuery.filter.TextLikeFieldFilter;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;

public abstract class TextPredicate implements QueryPredicate {

	private static final String LIKE = "%";

	private static final String REPLACE = "replace";

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter<?> fieldFilter) {
		return this.buildTextPredicate(criteriaBuilder,
				this.expressionNonSensitiveText(expression, criteriaBuilder, fieldFilter),
				this.transformTextToQuery(fieldFilter));
	}

	protected abstract Predicate buildTextPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
			String value);

	private Expression<String> expressionNonSensitiveText(Expression<String> expression,
			CriteriaBuilder criteriaBuilder, FieldFilter<?> fieldFilter) {
		expression = this.toLowerCase(expression, criteriaBuilder);
		expression = this.replaceCharacters(expression, criteriaBuilder, fieldFilter);

		return expression;
	}

	private Expression<String> toLowerCase(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.lower(expression);
	}

	private Expression<String> replaceCharacters(Expression<String> expression, CriteriaBuilder criteriaBuilder,
			FieldFilter<?> fieldFilter) {
		for (final CharacterReplacement rc : ((TextFieldFilter) fieldFilter).getCharactersReplacement()) {
			expression = criteriaBuilder.function(REPLACE, String.class, expression,
					criteriaBuilder.literal(rc.getOldCharacter()), criteriaBuilder.literal(rc.getNewCharacter()));
		}

		return expression;
	}

	protected String transformTextToQuery(FieldFilter<?> fieldFilter) {
		final TextFieldFilter textFieldFilter = (TextFieldFilter) fieldFilter;
		String value = textFieldFilter.getValue();

		if (textFieldFilter.getNormalizeText()) {
			value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		if (textFieldFilter instanceof TextLikeFieldFilter) {
			return LIKE.concat(value).concat(LIKE);
		} else {
			return value;
		}
	}
}
