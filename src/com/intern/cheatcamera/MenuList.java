// 設定画面
package com.intern.cheatcamera;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.ListPreference;  

public class MenuList extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		addPreferencesFromResource(R.xml.preference);
		
		//
		// 人物名のサマリー表示		 
		EditTextPreference edittext_preference = (EditTextPreference)getPreferenceScreen().findPreference("person");
		edittext_preference.setSummary(edittext_preference.getText());
		// シャッター音のサマリー表示
		//ListPreference list_sound_preference = (ListPreference)getPreferenceScreen().findPreference("sound");
		//list_sound_preference.setSummary(list_sound_preference.getEntry());
		// 時間のサマリー表示
		ListPreference list_time_preference = (ListPreference)getPreferenceScreen().findPreference("time");
		list_time_preference.setSummary(list_time_preference.getEntry());

	}
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,  String key) {
		// 人物名のサマリー表示		 
		EditTextPreference edittext_preference = (EditTextPreference)getPreferenceScreen().findPreference("person");
		edittext_preference.setSummary(edittext_preference.getText());
		// シャッター音のサマリー表示
		//ListPreference list_sound_preference = (ListPreference)getPreferenceScreen().findPreference("sound");
		//list_sound_preference.setSummary(list_sound_preference.getEntry());
		// 時間のサマリー表示
		ListPreference list_time_preference = (ListPreference)getPreferenceScreen().findPreference("time");
		list_time_preference.setSummary(list_time_preference.getEntry());

		}
	

}
