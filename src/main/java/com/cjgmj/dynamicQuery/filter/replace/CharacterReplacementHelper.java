package com.cjgmj.dynamicQuery.filter.replace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterReplacementHelper {

	@Getter
	private final List<CharacterReplacement> replaces;

	public static List<CharacterReplacement> emptyReplacements() {
		return new CharacterReplacementHelper(Collections.emptyList()).getReplaces();
	}

	public static List<CharacterReplacement> basicReplacements() {
		return new CharacterReplacementHelper(Arrays.asList(CharacterReplacement.A_ACUTE, CharacterReplacement.A_GRAVE,
				CharacterReplacement.A_UML, CharacterReplacement.A_CIRC, CharacterReplacement.E_ACUTE,
				CharacterReplacement.E_GRAVE, CharacterReplacement.E_UML, CharacterReplacement.E_CIRC,
				CharacterReplacement.I_ACUTE, CharacterReplacement.I_GRAVE, CharacterReplacement.I_UML,
				CharacterReplacement.I_CIRC, CharacterReplacement.O_ACUTE, CharacterReplacement.O_GRAVE,
				CharacterReplacement.O_UML, CharacterReplacement.O_CIRC, CharacterReplacement.U_ACUTE,
				CharacterReplacement.U_GRAVE, CharacterReplacement.U_UML, CharacterReplacement.U_CIRC,
				CharacterReplacement.N_TILDE, CharacterReplacement.HYPHEN)).getReplaces();
	}

	public List<CharacterReplacement> addListReplacement(List<CharacterReplacement> replacesCharacters) {
		this.replaces.addAll(replacesCharacters);

		return this.replaces;
	}

	public List<CharacterReplacement> addReplacement(CharacterReplacement replaceCharacter) {
		this.replaces.add(replaceCharacter);

		return this.replaces;
	}

	public List<CharacterReplacement> deleteReplacement(CharacterReplacement replaceCharacter) {
		this.replaces.remove(replaceCharacter);

		return this.replaces;
	}

}
