package com.github.cjgmj.dynamicquery.modifier.text.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * It is two ways to create a text filter configuration, calling the
 * {@code normalizeTextWithTextReplacement} method or the
 * {@code noNormalizeTextWithTextReplacement} methods. Both method receive as
 * parameter a {@link TextReplacement} object.
 * 
 * @author cjgmj
 *
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TextFilterConfiguration {

	private Boolean normalizeText;

	private TextReplacement textReplacement;

	public static TextFilterConfiguration normalizeTextWithTextReplacement(TextReplacement textReplacement) {
		return new TextFilterConfiguration(Boolean.TRUE, textReplacement);
	}

	public static TextFilterConfiguration noNormalizeTextWithTextReplacement(TextReplacement textReplacement) {
		return new TextFilterConfiguration(Boolean.FALSE, textReplacement);
	}

}
