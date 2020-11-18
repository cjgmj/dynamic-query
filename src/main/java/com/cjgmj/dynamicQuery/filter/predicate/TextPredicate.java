package com.cjgmj.dynamicQuery.filter.predicate;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacementHelper;

public abstract class TextPredicate implements QueryPredicate {

	private static final String LIKE = "%";

	private static final String REPLACE = "replace";

	private List<CharacterReplacement> charactersReplacement;

	private Boolean normalizeText = Boolean.TRUE;

	public TextPredicate() {
		this.charactersReplacement = CharacterReplacementHelper.basicReplacements();
	}

	@Override
	public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, FieldFilter fieldFilter) {
		Expression<String> expression = null;

		final String[] sonsField = fieldFilter.getField().split("[.]");

		if (sonsField.length == 1) {
			expression = root.get(fieldFilter.getField());
		} else {
			Join<Object, Object> join = root.join(sonsField[0]);
			for (int i = 1; i < sonsField.length - 1; i++) {
				join = join.join(sonsField[i]);
			}
			expression = join.get(sonsField[sonsField.length - 1]);
		}

		return this.buildPredicate(criteriaBuilder, this.nonSensitiveText(expression, criteriaBuilder),
				this.transformTextToQuery(fieldFilter.getValue()));
	}

	protected abstract Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
			String value);

	private Expression<String> nonSensitiveText(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
		Expression<String> finalExpression = expression;

		finalExpression = criteriaBuilder.lower(finalExpression);

		for (final CharacterReplacement rc : this.charactersReplacement) {
			finalExpression = criteriaBuilder.function(REPLACE, String.class, finalExpression,
					criteriaBuilder.literal(rc.getOldCharacter()), criteriaBuilder.literal(rc.getNewCharacter()));
		}

		return finalExpression;
	}

	private String transformTextToQuery(String value) {
		if (this.normalizeText) {
			value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		return LIKE.concat(value).concat(LIKE);
	}

	public TextPredicate defineCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = Optional.ofNullable(charactersReplacement)
				.orElse(CharacterReplacementHelper.emptyReplacements());

		return this;
	}

	public TextPredicate defineCharacterReplacement(CharacterReplacement characterReplacement) {
		if (characterReplacement == null) {
			this.charactersReplacement = CharacterReplacementHelper.emptyReplacements();
		} else {
			this.charactersReplacement = Arrays.asList(characterReplacement);
		}

		return this;
	}

	public TextPredicate noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}
}
