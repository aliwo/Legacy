package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OptionsActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getFragmentManager().beginTransaction() // 설정 화면을 가져옵니다.
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }

    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_options); // xml을 가져옵니다.

            Preference NoticePreference = (Preference) findPreference("notice");
            NoticePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
             @Override
             public boolean onPreferenceClick(Preference preference)
                {
                    Intent intent = new Intent(preference.getContext(), NoticeActivity.class);
                    startActivity(intent);
                    return false;
                 }
            });

        }
    }

}
