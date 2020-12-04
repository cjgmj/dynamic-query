package com.github.cjgmj.dynamicquery.modifier.text.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.cjgmj.dynamicquery.replacement.BasicCharacterReplacement;
import com.github.cjgmj.dynamicquery.replacement.CharacterReplacement;

import lombok.Getter;

/**
 * In this class can be defined the characters replacement for the text filter.
 * This class provide two methods, {@code emptyReplacements} and
 * {@code standardReplacements}, for set up fast characters replacement.
 * <p>
 * 
 * It also could be defined a customized characters replacement using the
 * {@code customizeReplacement} method.
 * 
 * @author cjgmj
 *
 */
@Getter
public class TextReplacement {

	private List<CharacterReplacement> charactersReplacement;

	private TextReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement = charactersReplacement;
	}

	public static TextReplacement emptyReplacements() {
		return new TextReplacement(new ArrayList<>());
	}

	public static TextReplacement standardReplacements() {
		return new TextReplacement(Stream.of(BasicCharacterReplacement.values()).collect(Collectors.toList()));
	}

	public static TextReplacement customizeReplacement(CharacterReplacement... charactersReplacement) {
		final List<CharacterReplacement> charactersReplacementList = new ArrayList<>();
		if (charactersReplacement.length > 0) {
			for (final CharacterReplacement characterReplacement : charactersReplacement) {
				charactersReplacementList.add(characterReplacement);
			}
		}

		return new TextReplacement(charactersReplacementList);
	}

	public static TextReplacement customizeReplacement(List<CharacterReplacement> charactersReplacement) {
		return new TextReplacement(Optional.ofNullable(charactersReplacement).orElse(new ArrayList<>()));
	}

	public TextReplacement addCharactersReplacement(CharacterReplacement... charactersReplacement) {
		if (charactersReplacement.length > 0) {
			for (final CharacterReplacement characterReplacement : charactersReplacement) {
				this.charactersReplacement.add(characterReplacement);
			}
		}

		return this;
	}

	public TextReplacement addCharactersReplacement(List<CharacterReplacement> charactersReplacement) {
		this.charactersReplacement.addAll(charactersReplacement);

		return this;
	}

	public TextReplacement removeReplacement(CharacterReplacement replaceCharacter) {
		this.charactersReplacement.remove(replaceCharacter);

		return this;
	}
}
