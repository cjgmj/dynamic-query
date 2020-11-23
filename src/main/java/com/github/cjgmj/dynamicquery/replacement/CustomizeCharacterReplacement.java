package com.github.cjgmj.dynamicquery.replacement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomizeCharacterReplacement implements CharacterReplacement {

	private String oldCharacter;

	private String newCharacter;
}
