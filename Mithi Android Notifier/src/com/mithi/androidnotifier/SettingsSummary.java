package com.mithi.androidnotifier;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public final class SettingsSummary extends EditTextPreference {

	public SettingsSummary(final Context ctx, final AttributeSet attrs) {
		super(ctx, attrs);
	}

	public SettingsSummary(final Context ctx) {
		super(ctx);
	}

	@Override
	public void setText(final String value) {
		super.setText(value);
		setSummary(getText());
	}
}