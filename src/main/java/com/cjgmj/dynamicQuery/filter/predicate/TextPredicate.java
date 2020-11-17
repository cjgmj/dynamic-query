package com.cjgmj.dynamicQuery.filter.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacementHelper;

public class TextPredicate extends QueryPredicate {

	private static final String REPLACE = "replace";

	private List<CharacterReplacement> charactersReplacement;

	public TextPredicate() {
		this.charactersReplacement = CharacterReplacementHelper.basicReplacements();
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

		return this.nonSensitiveText(expression, cb);
	}

	private Expression<String> nonSensitiveText(Expression<String> expression, CriteriaBuilder cb) {
		Expression<String> result = expression;

		result = cb.lower(result);

		for (final CharacterReplacement rc : this.charactersReplacement) {
			result = cb.function(REPLACE, String.class, result, cb.literal(rc.getOldCharacter()),
					cb.literal(rc.getNewCharacter()));
		}

		return result;
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
}
