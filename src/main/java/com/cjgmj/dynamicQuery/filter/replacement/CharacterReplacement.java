package com.cjgmj.dynamicQuery.filter.replacement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CharacterReplacement {

	A_ACUTE("á", "a"), A_GRAVE("à", "a"), A_UML("ä", "a"), A_CIRC("â", "a"), E_ACUTE("é", "e"), E_GRAVE("è", "e"),
	E_UML("ë", "e"), E_CIRC("ê", "e"), I_ACUTE("í", "i"), I_GRAVE("ì", "i"), I_UML("ï", "i"), I_CIRC("î", "i"),
	O_ACUTE("ó", "o"), O_GRAVE("ò", "o"), O_UML("ö", "o"), O_CIRC("ô", "o"), U_ACUTE("ú", "u"), U_GRAVE("ù", "u"),
	U_UML("ü", "u"), U_CIRC("û", "u"), N_TILDE("ñ", "n"), HYPHEN("-", " ");

	private String oldCharacter;

	private String newCharacter;

}
