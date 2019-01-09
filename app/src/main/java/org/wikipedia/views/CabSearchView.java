package org.wikipedia.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.richtext.RichTextUtil;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.wikipedia.util.ResourceUtil.getThemedColor;

/** {@link SearchView} that exposes contextual action bar callbacks. */
public class CabSearchView extends SearchView {
    private static final boolean DEFAULT_CAB_ENABLED = true;

    private boolean mCabEnabled;

    private  ImageView searchCloseBtn;

    private static final int SEARCH_TEXT_SIZE = 16;

    public CabSearchView(Context context) {
        this(context, null);
    }

    public CabSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.searchViewStyle);
    }

    public CabSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int themedIconColor = getThemedColor(getContext(), R.attr.page_toolbar_icon_color);
        SearchView.SearchAutoComplete searchSrcTextView = findViewById(R.id.search_src_text);
        searchSrcTextView.setCustomSelectionActionModeCallback(new Callback());
        searchSrcTextView.setTextColor(getThemedColor(getContext(), R.attr.primary_text_color));
        searchSrcTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SEARCH_TEXT_SIZE);
        searchSrcTextView.setHintTextColor(themedIconColor);
        ImageView searchMagIcon = findViewById(R.id.search_mag_icon);
        searchMagIcon.setColorFilter(themedIconColor);
        searchCloseBtn = findViewById(R.id.search_close_btn);
        searchCloseBtn.setVisibility(GONE);
        searchCloseBtn.setColorFilter(themedIconColor);
        addFilter(searchSrcTextView, new PlainTextInputFilter());

        // TODO: remove this if the SearchFragment has its own theme style.
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchSrcTextView, R.drawable.custom_cursor);
        } catch (Exception e) {
            // ignore
        }

        initLayoutAttributes(attrs, defStyleAttr);
    }

    public boolean isCabEnabled() {
        return mCabEnabled;
    }

    public void setCabEnabled(boolean enabled) {
        mCabEnabled = enabled;
    }

    private void addFilter(TextView textView, InputFilter filter) {
        InputFilter[] filters = textView.getFilters();
        InputFilter[] newFilters = Arrays.copyOf(filters, filters.length + 1);
        newFilters[filters.length] = filter;
        textView.setFilters(newFilters);
    }

    private void initLayoutAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.CabSearchView, defStyleAttr, 0);

        setCabEnabled(attrsArray.getBoolean(R.styleable.CabSearchView_cabEnabled,
                DEFAULT_CAB_ENABLED));

        attrsArray.recycle();
    }

    public void setCloseButtonVisibility(String searchString) {
        if (TextUtils.isEmpty(searchString)) {
            searchCloseBtn.setVisibility(GONE);
            searchCloseBtn.setImageDrawable(null);

        } else {
            searchCloseBtn.setVisibility(VISIBLE);
            searchCloseBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_close_themed_24dp));
        }
    }

    private class Callback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return isCabEnabled();
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override public void onDestroyActionMode(ActionMode mode) { }
    }

    private static class PlainTextInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            return RichTextUtil.stripRichText(source, start, end).subSequence(start, end);
        }
    }
}
