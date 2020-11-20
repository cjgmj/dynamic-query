package com.cjgmj.dynamicQuery.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacementHelper;

import lombok.Getter;

@Getter
public abstract class TextFieldFilter extends FieldFilter<String> {

	private Boolean normalizeText = Boolean.TRUE;

	private List<CharacterReplacement> charactersReplacement;

	public TextFieldFilter(String field, String value) {
		super(field, value);
		this.charactersReplacement = CharacterReplacementHelper.basicReplacements();
	}

	public TextFieldFilter noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}

	public TextFieldFilter defineCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = Optional.ofNullable(charactersReplacement)
				.orElse(CharacterReplacementHelper.emptyReplacements());

		return this;
	}

	public TextFieldFilter defineCharacterReplacement(CharacterReplacement characterReplacement) {
		if (characterReplacement == null) {
			this.charactersReplacement = CharacterReplacementHelper.emptyReplacements();
		} else {
			this.charactersReplacement = Arrays.asList(characterReplacement);
		}

		return this;
	}

}
