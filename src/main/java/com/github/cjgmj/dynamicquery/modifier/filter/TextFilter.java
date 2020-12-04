package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.text.configuration.TextFilterConfiguration;
import com.github.cjgmj.dynamicquery.modifier.text.configuration.TextReplacement;

import lombok.Getter;

/**
 * The {@code TextFilter} class is an abstract class that provides methods for
 * configuring the text replacements and if the text will be normalized or not.
 * <p>
 * 
 * By default, the text will be normalized and it will be used the standard text
 * replacements which could be found in {@code BasicCharacterReplacement}.
 * 
 * It provide the {@code noNormalizeAndEmptyReplacement} and
 * {@code noNormalizeAndStandardReplacement} methods to set up fast
 * configuration. It also provide the {@code customizeConfiguration} method to
 * set up a customize configuration using the {@link TextFilterConfiguration}
 * class.
 * 
 * @author cjgmj
 *
 */
@Getter
public abstract class TextFilter extends ValueFilter<String> {

	private TextFilterConfiguration textFilterConfiguration;

	protected TextFilter(String field, String value) {
		super(field, value);
		this.textFilterConfiguration = TextFilterConfiguration
				.normalizeTextWithTextReplacement(TextReplacement.standardReplacements());
	}

	public TextFilter noNormalizeAndEmptyReplacement() {
		this.textFilterConfiguration = TextFilterConfiguration
				.noNormalizeTextWithTextReplacement(TextReplacement.emptyReplacements());

		return this;
	}

	public TextFilter noNormalizeAndStandardReplacement() {
		this.textFilterConfiguration = TextFilterConfiguration
				.noNormalizeTextWithTextReplacement(TextReplacement.standardReplacements());

		return this;
	}

	public TextFilter customizeConfiguration(TextFilterConfiguration textFilterConfiguration) {
		this.textFilterConfiguration = textFilterConfiguration;

		return this;
	}

}
