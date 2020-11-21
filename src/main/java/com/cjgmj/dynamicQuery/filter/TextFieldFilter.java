package com.cjgmj.dynamicQuery.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cjgmj.dynamicQuery.filter.replacement.CharacterReplacement;

import lombok.Getter;

@Getter
public abstract class TextFieldFilter extends FieldFilter<String> {

	private Boolean normalizeText = Boolean.TRUE;

	private List<CharacterReplacement> charactersReplacement;

	public TextFieldFilter(String field, String value) {
		super(field, value);
		this.charactersReplacement = this.getBasicReplacements();
	}

	public TextFieldFilter noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}

	public TextFieldFilter emptyReplacements() {
		this.charactersReplacement = this.getEmptyReplacements();

		return this;
	}

	public TextFieldFilter basicReplacements() {
		this.charactersReplacement = this.getBasicReplacements();

		return this;
	}

	public TextFieldFilter addListCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = Optional.ofNullable(charactersReplacement).orElse(this.getEmptyReplacements());

		return this;
	}

	public TextFieldFilter addCharacterReplacement(CharacterReplacement characterReplacement) {
		if (characterReplacement == null) {
			this.charactersReplacement = this.getEmptyReplacements();
		} else {
			this.charactersReplacement = Arrays.asList(characterReplacement);
		}

		return this;
	}

	public TextFieldFilter removeReplacement(CharacterReplacement replaceCharacter) {
		this.charactersReplacement.remove(replaceCharacter);

		return this;
	}

	private List<CharacterReplacement> getEmptyReplacements() {
		return new ArrayList<>();
	}

	private List<CharacterReplacement> getBasicReplacements() {
		return Stream.of(CharacterReplacement.values()).collect(Collectors.toList());
	}

}
