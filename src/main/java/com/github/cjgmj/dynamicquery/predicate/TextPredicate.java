package com.github.cjgmj.dynamicquery.predicate;

import java.text.Normalizer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TextFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TextLikeFilter;
import com.github.cjgmj.dynamicquery.replacement.CharacterReplacement;

public abstract class TextPredicate implements QueryPredicate {

	private static final String LIKE = "%";

	private static final String REPLACE = "replace";

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> valueFilter) {
		return this.buildTextPredicate(criteriaBuilder,
				this.expressionNonSensitiveText(expression, criteriaBuilder, valueFilter),
				this.transformTextToQuery(valueFilter));
	}

	protected abstract Predicate buildTextPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
			String value);

	private Expression<String> expressionNonSensitiveText(Expression<String> expression,
			CriteriaBuilder criteriaBuilder, ValueFilter<?> valueFilter) {
		expression = this.toLowerCase(expression, criteriaBuilder);
		expression = this.replaceCharacters(expression, criteriaBuilder, valueFilter);

		return expression;
	}

	private Expression<String> toLowerCase(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.lower(expression);
	}

	private Expression<String> replaceCharacters(Expression<String> expression, CriteriaBuilder criteriaBuilder,
			ValueFilter<?> valueFilter) {
		for (final CharacterReplacement rc : ((TextFilter) valueFilter).getTextReplacement()
				.getCharactersReplacement()) {
			expression = criteriaBuilder.function(REPLACE, String.class, expression,
					criteriaBuilder.literal(rc.getOldCharacter()), criteriaBuilder.literal(rc.getNewCharacter()));
		}

		return expression;
	}

	protected String transformTextToQuery(ValueFilter<?> valueFilter) {
		final TextFilter textValueFilter = (TextFilter) valueFilter;
		String value = textValueFilter.getValue();

		if (textValueFilter.getNormalizeText()) {
			value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		if (textValueFilter instanceof TextLikeFilter) {
			return LIKE.concat(value).concat(LIKE);
		} else {
			return value;
		}
	}
}
