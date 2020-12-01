package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.replacement.TextReplacement;

import lombok.Getter;

@Getter
public abstract class TextFilter extends ValueFilter<String> {

	private Boolean normalizeText = Boolean.TRUE;

	private TextReplacement textReplacement;

	public TextFilter(String field, String value) {
		super(field, value);
		this.textReplacement = TextReplacement.standardReplacements();
	}

	public TextFilter noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}

	public TextFilter defineTextReplacement(TextReplacement textReplacement) {
		this.textReplacement = textReplacement;

		return this;
	}

}
