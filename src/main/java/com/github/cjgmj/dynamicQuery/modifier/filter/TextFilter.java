package com.github.cjgmj.dynamicquery.modifier.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.replacement.CharacterReplacement;

import lombok.Getter;

@Getter
public abstract class TextFilter extends ValueFilter<String> {

	private Boolean normalizeText = Boolean.TRUE;

	private List<CharacterReplacement> charactersReplacement;

	public TextFilter(String field, String value) {
		super(field, value);
		this.charactersReplacement = this.getBasicReplacements();
	}

	public TextFilter noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}

	public TextFilter emptyReplacements() {
		this.charactersReplacement = this.getEmptyReplacements();

		return this;
	}

	public TextFilter basicReplacements() {
		this.charactersReplacement = this.getBasicReplacements();

		return this;
	}

	public TextFilter addListCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = Optional.ofNullable(charactersReplacement).orElse(this.getEmptyReplacements());

		return this;
	}

	public TextFilter addCharacterReplacement(CharacterReplacement characterReplacement) {
		if (characterReplacement == null) {
			this.charactersReplacement = this.getEmptyReplacements();
		} else {
			this.charactersReplacement = Arrays.asList(characterReplacement);
		}

		return this;
	}

	public TextFilter removeReplacement(CharacterReplacement replaceCharacter) {
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
