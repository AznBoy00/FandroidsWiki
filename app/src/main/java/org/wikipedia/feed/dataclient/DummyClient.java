package org.wikipedia.feed.dataclient;

import android.content.Context;
import android.support.annotation.NonNull;

import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.feed.FeedCoordinator;
import org.wikipedia.feed.model.Card;
import org.wikipedia.feed.searchbar.SearchCard;

import java.util.Collections;

/** A dummy client for providing static cards (main page, random) on tap to the FeedCoordinator. */
public abstract class DummyClient implements FeedClient {
    @Override public void request(@NonNull Context context, @NonNull WikiSite wiki, int age,
                                  @NonNull FeedClient.Callback cb) {
        Card card = getNewCard(wiki);
        if (card instanceof SearchCard) {
            cb.success(Collections.singletonList(card));
        } else {
            FeedCoordinator.postCardsToCallback(cb, Collections.singletonList(card));
        }
    }

    @Override public void cancel() { }

    public abstract Card getNewCard(WikiSite wiki);
}
