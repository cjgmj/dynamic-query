package com.cjgmj.dynamicQuery.filter.replace;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterReplacementHelper {

	@Getter
	private final List<CharacterReplacement> charactersReplacement;

	public static List<CharacterReplacement> emptyReplacements() {
		return new CharacterReplacementHelper(Collections.emptyList()).getCharactersReplacement();
	}

	public static List<CharacterReplacement> basicReplacements() {
		return new CharacterReplacementHelper(Stream.of(CharacterReplacement.values()).collect(Collectors.toList()))
				.getCharactersReplacement();
	}

	public List<CharacterReplacement> addListReplacement(List<CharacterReplacement> replacesCharacters) {
		this.getCharactersReplacement().addAll(replacesCharacters);

		return this.getCharactersReplacement();
	}

	public List<CharacterReplacement> addReplacement(CharacterReplacement replaceCharacter) {
		this.getCharactersReplacement().add(replaceCharacter);

		return this.getCharactersReplacement();
	}

	public List<CharacterReplacement> deleteReplacement(CharacterReplacement replaceCharacter) {
		this.getCharactersReplacement().remove(replaceCharacter);

		return this.getCharactersReplacement();
	}

}
