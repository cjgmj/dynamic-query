package com.cjgmj.dynamicQuery.filter.predicate;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
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
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter fieldFilter) {
		return this.buildTextPredicate(criteriaBuilder, this.expressionNonSensitiveText(expression, criteriaBuilder),
				this.transformTextToQuery(fieldFilter.getValue()));
	}

	protected abstract Predicate buildTextPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
			String value);

	private Expression<String> expressionNonSensitiveText(Expression<String> expression,
			CriteriaBuilder criteriaBuilder) {
		expression = this.toLowerCase(expression, criteriaBuilder);
		expression = this.replaceCharacters(expression, criteriaBuilder);

		return expression;
	}

	private Expression<String> toLowerCase(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.lower(expression);
	}

	private Expression<String> replaceCharacters(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
		for (final CharacterReplacement rc : this.charactersReplacement) {
			expression = criteriaBuilder.function(REPLACE, String.class, expression,
					criteriaBuilder.literal(rc.getOldCharacter()), criteriaBuilder.literal(rc.getNewCharacter()));
		}

		return expression;
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
