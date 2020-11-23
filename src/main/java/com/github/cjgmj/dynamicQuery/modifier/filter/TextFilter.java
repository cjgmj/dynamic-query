package com.github.cjgmj.dynamicquery.modifier.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.replacement.BasicCharacterReplacement;
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

	public TextFilter defineCharactersReplacement(CharacterReplacement... charactersReplacement) {
		if (charactersReplacement.length > 0) {
			for (final CharacterReplacement characterReplacement : charactersReplacement) {
				this.charactersReplacement.add(characterReplacement);
			}
		}

		return this;
	}

	public TextFilter defineCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = Optional.ofNullable(charactersReplacement).orElse(this.getEmptyReplacements());

		return this;
	}

	public TextFilter addCharactersReplacement(CharacterReplacement... charactersReplacement) {
		if (charactersReplacement.length > 0) {
			for (final CharacterReplacement characterReplacement : charactersReplacement) {
				this.charactersReplacement.add(characterReplacement);
			}
		}

		return this;
	}

	public TextFilter addCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement.addAll(charactersReplacement);

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
		return Stream.of(BasicCharacterReplacement.values()).collect(Collectors.toList());
	}

}
