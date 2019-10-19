/*
 * Copyright © 2019 LLuviaOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tormenta.settings.fragments;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.preference.Preference;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import com.android.settings.R;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

import com.tormenta.settings.preference.CustomSeekBarPreference;

import java.util.ArrayList;
import java.util.List;

public class LockScreenSettings extends SettingsPreferenceFragment implements
      Preference.OnPreferenceChangeListener, Indexable {

    private static final String KEY_LOCKSCREEN_MEDIA_BLUR = "lockscreen_media_blur";
    private CustomSeekBarPreference mLockscreenMediaBlur;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.lockscreen_settings);

        int defaultBlur = 25;
        mLockscreenMediaBlur = (CustomSeekBarPreference) findPreference(KEY_LOCKSCREEN_MEDIA_BLUR);
        int value = Settings.System.getInt(getContentResolver(),
                Settings.System.OMNI_LOCKSCREEN_MEDIA_BLUR, defaultBlur);
        mLockscreenMediaBlur.setValue(value);
        mLockscreenMediaBlur.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mLockscreenMediaBlur) {
            int value = (Integer) newValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.OMNI_LOCKSCREEN_MEDIA_BLUR, value);
            return true;
        }
        return true;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.TORMENTA;
    }

    /**
     * For Search.
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {

                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                        boolean enabled) {
                    final ArrayList<SearchIndexableResource> result = new ArrayList<>();

                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.lockscreen_settings;
                    result.add(sir);
                    return result;
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
    };
}
