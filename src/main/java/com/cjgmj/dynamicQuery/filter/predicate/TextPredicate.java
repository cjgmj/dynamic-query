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

public class TextPredicate implements QueryPredicate {

	private static final String LIKE = "%";

	private static final String REPLACE = "replace";

	private List<CharacterReplacement> charactersReplacement;

	private Boolean normalizeText = Boolean.TRUE;

	public TextPredicate() {
		this.charactersReplacement = CharacterReplacementHelper.basicReplacements();
	}

	@Override
	public Predicate getPredicate(CriteriaBuilder cb, Root<?> root, FieldFilter filter) {
		Expression<String> expression = null;

		final String[] arr = filter.getField().split("[.]");

		if (arr.length == 1) {
			expression = root.get(filter.getField());
		} else {
			Join<Object, Object> join = root.join(arr[0]);
			for (int i = 1; i < arr.length - 1; i++) {
				join = join.join(arr[i]);
			}
			expression = join.get(arr[arr.length - 1]);
		}

		return cb.like(this.nonSensitiveText(expression, cb), this.transformTextToQuery(filter.getValue()));
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

	private String transformTextToQuery(String text) {
		if (this.normalizeText) {
			text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		return LIKE.concat(text).concat(LIKE);
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

	public QueryPredicate noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}
}
