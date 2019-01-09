package org.wikipedia.feed.onthisday;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.wikipedia.activity.SingleFragmentActivity;
import org.wikipedia.dataclient.WikiSite;

public class OnThisDayActivity extends SingleFragmentActivity<OnThisDayFragment> {
    public static final String AGE = "age";
    public static final String WIKISITE = "wikisite";
    public static final int INVOKE_SOURCE_CARD_BODY = 0;
    public static final int INVOKE_SOURCE_CARD_FOOTER = 1;
    static final String INVOKE_SOURCE_EXTRA = "invokeSource";

    public static Intent newIntent(@NonNull Context context, int age, WikiSite wikiSite, int invokeSource) {
        return new Intent(context, OnThisDayActivity.class)
                .putExtra(AGE, age)
                .putExtra(WIKISITE, wikiSite)
                .putExtra(INVOKE_SOURCE_EXTRA, invokeSource);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragment().onBackPressed();
    }

    @Override
    protected OnThisDayFragment createFragment() {
        return OnThisDayFragment.newInstance(getIntent().getIntExtra(AGE, 0), getIntent().getParcelableExtra(WIKISITE));
    }
}
