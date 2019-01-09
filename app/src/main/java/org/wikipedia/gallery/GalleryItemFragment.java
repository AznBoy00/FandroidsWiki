package org.wikipedia.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.samples.zoomable.DoubleTapGestureListener;

import org.wikipedia.Constants;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.FragmentUtil;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.page.Namespace;
import org.wikipedia.page.PageTitle;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.FileUtil;
import org.wikipedia.util.PermissionUtil;
import org.wikipedia.util.ShareUtil;
import org.wikipedia.util.StringUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.views.ZoomableDraweeViewWithBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.wikipedia.util.PermissionUtil.hasWriteExternalStoragePermission;
import static org.wikipedia.util.PermissionUtil.requestWriteStorageRuntimePermissions;

public class GalleryItemFragment extends Fragment {
    public static final String ARG_PAGETITLE = "pageTitle";
    public static final String ARG_GALLERY_ITEM = "galleryItem";
    public static final String ARG_FEED_FEATURED_IMAGE = "feedFeaturedImage";
    public static final String ARG_AGE = "age";

    public interface Callback {
        void onDownload(@NonNull GalleryItem item);
        void onShare(@NonNull GalleryItem item, @Nullable Bitmap bitmap, @NonNull String subject, @NonNull PageTitle title);
    }

    @BindView(R.id.gallery_item_progress_bar) ProgressBar progressBar;
    @BindView(R.id.gallery_video_container) View videoContainer;
    @BindView(R.id.gallery_video) VideoView videoView;
    @BindView(R.id.gallery_video_thumbnail) SimpleDraweeView videoThumbnail;
    @BindView(R.id.gallery_video_play_button) View videoPlayButton;
    @BindView(R.id.gallery_image) ZoomableDraweeViewWithBackground imageView;
    @Nullable private Unbinder unbinder;

    private MediaController mediaController;
    private int age;

    @NonNull private WikipediaApp app = WikipediaApp.getInstance();
    @Nullable private GalleryActivity parentActivity;
    @Nullable private PageTitle pageTitle;
    @SuppressWarnings("NullableProblems") @NonNull private PageTitle imageTitle;
    @Nullable private GalleryItem galleryItem;
    @Nullable public GalleryItem getGalleryItem() {
        return galleryItem;
    }

    public static GalleryItemFragment newInstance(@Nullable PageTitle pageTitle, @NonNull GalleryItem galleryItem) {
        GalleryItemFragment f = new GalleryItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAGETITLE, pageTitle);
        args.putSerializable(ARG_GALLERY_ITEM, galleryItem);

        if (galleryItem instanceof FeaturedImageGalleryItem) {
            args.putBoolean(ARG_FEED_FEATURED_IMAGE, true);
            args.putInt(ARG_AGE, ((FeaturedImageGalleryItem) galleryItem).getAge());
        }

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryItem = (GalleryItem) getArguments().getSerializable(ARG_GALLERY_ITEM);
        pageTitle = getArguments().getParcelable(ARG_PAGETITLE);
        imageTitle = new PageTitle(Namespace.FILE.toLegacyString(),
                StringUtil.removeNamespace(galleryItem.getTitles().getCanonical()),
                new WikiSite(galleryItem.getFilePage()));

        if (getArguments().getBoolean(ARG_FEED_FEATURED_IMAGE)) {
            age = getArguments().getInt(ARG_AGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery_item, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        imageView.setTapListener(new DoubleTapGestureListener(imageView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                parentActivity.toggleControls();
                return true;
            }
        });

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build();
        imageView.setHierarchy(hierarchy);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        parentActivity = (GalleryActivity) getActivity();

        loadMedia();
    }

    @Override
    public void onDestroyView() {
        imageView.setController(null);
        imageView.setOnClickListener(null);
        videoThumbnail.setController(null);
        videoThumbnail.setOnClickListener(null);
        unbinder.unbind();
        unbinder = null;
        super.onDestroyView();
    }

    private void updateProgressBar(boolean visible, boolean indeterminate, int value) {
        progressBar.setIndeterminate(indeterminate);
        if (!indeterminate) {
            progressBar.setProgress(value);
        }
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        app.getRefWatcher().watch(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!isAdded()) {
            return;
        }
        menu.findItem(R.id.menu_gallery_visit_page).setEnabled(galleryItem != null);
        menu.findItem(R.id.menu_gallery_share).setEnabled(galleryItem != null
                && !TextUtils.isEmpty(galleryItem.getThumbnailUrl()) && imageView.getDrawable() != null);
        menu.findItem(R.id.menu_gallery_save).setEnabled(galleryItem != null
                && !TextUtils.isEmpty(galleryItem.getThumbnailUrl()) && imageView.getDrawable() != null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_gallery_visit_page:
                if (galleryItem != null) {
                    parentActivity.finishWithPageResult(imageTitle);
                }
                return true;
            case R.id.menu_gallery_save:
                handleImageSaveRequest();
                return true;
            case R.id.menu_gallery_share:
                shareImage();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Notifies this fragment that the current position of its containing ViewPager has changed.
     *
     * @param fragmentPosition This fragment's position in the ViewPager.
     * @param pagerPosition    The pager's current position that is displayed to the user.
     */
    public void onUpdatePosition(int fragmentPosition, int pagerPosition) {
        if (fragmentPosition != pagerPosition) {
            // update stuff if our position is not "current" within the ViewPager...
            if (mediaController != null) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                mediaController.hide();
            }
        } else {
            // update stuff if our position is "current"
            if (mediaController != null) {
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
            }
        }
    }

    private void handleImageSaveRequest() {
        if (!(hasWriteExternalStoragePermission(this.getActivity()))) {
            requestWriteExternalStoragePermission();
        } else {
            saveImage();
        }
    }

    private void requestWriteExternalStoragePermission() {
        requestWriteStorageRuntimePermissions(this,
                Constants.ACTIVITY_REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    /**
     * Load the actual media associated with our gallery item into the UI.
     */
    private void loadMedia() {
        if (FileUtil.isVideo(galleryItem.getType())) {
            loadVideo();
        } else {
            loadImage(galleryItem.getPreferredSizedImageUrl());
        }

        parentActivity.supportInvalidateOptionsMenu();
        parentActivity.layOutGalleryDescription();
    }

    private View.OnClickListener videoThumbnailClickListener = new View.OnClickListener() {
        private boolean loading = false;

        @Override
        public void onClick(View v) {
            if (loading) {
                return;
            }
            loading = true;
            L.d("Loading video from url: " + galleryItem.getOriginalVideoSource().getOriginalUrl());
            videoView.setVisibility(View.VISIBLE);
            mediaController = new MediaController(parentActivity);
            updateProgressBar(true, true, 0);
            videoView.setMediaController(mediaController);
            videoView.setOnPreparedListener((mp) -> {
                updateProgressBar(false, true, 0);
                // ...update the parent activity, which will trigger us to start playing!
                parentActivity.layOutGalleryDescription();
                // hide the video thumbnail, since we're about to start playback
                videoThumbnail.setVisibility(View.GONE);
                videoPlayButton.setVisibility(View.GONE);
                // and start!
                videoView.start();
                loading = false;
            });
            videoView.setOnErrorListener((mp, what, extra) -> {
                updateProgressBar(false, true, 0);
                FeedbackUtil.showMessage(getActivity(),
                        R.string.gallery_error_video_failed);
                videoView.setVisibility(View.GONE);
                videoThumbnail.setVisibility(View.VISIBLE);
                videoPlayButton.setVisibility(View.VISIBLE);
                loading = false;
                return true;
            });
            videoView.setVideoURI(Uri.parse(galleryItem.getOriginalVideoSource().getOriginalUrl()));
        }
    };

    private void loadVideo() {
        videoContainer.setVisibility(View.VISIBLE);
        videoPlayButton.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        if (TextUtils.isEmpty(galleryItem.getThumbnailUrl())) {
            videoThumbnail.setVisibility(View.GONE);
        } else {
            // show the video thumbnail while the video loads...
            videoThumbnail.setVisibility(View.VISIBLE);
            videoThumbnail.setController(Fresco.newDraweeControllerBuilder()
                    .setUri(galleryItem.getThumbnail().getSource())
                    .setAutoPlayAnimations(true)
                    .setControllerListener(new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                            updateProgressBar(false, true, 0);
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                            updateProgressBar(false, true, 0);
                        }
                    })
                    .build());
        }
        videoThumbnail.setOnClickListener(videoThumbnailClickListener);
    }

    private void loadImage(String url) {
        imageView.setVisibility(View.VISIBLE);
        L.v("Loading image from url: " + url);

        updateProgressBar(true, true, 0);
        imageView.setDrawBackground(false);
        imageView.setController(Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        imageView.setDrawBackground(true);
                        updateProgressBar(false, true, 0);
                        parentActivity.supportInvalidateOptionsMenu();
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        updateProgressBar(false, true, 0);
                        FeedbackUtil.showMessage(getActivity(), R.string.gallery_error_draw_failed);
                        L.d(throwable);
                    }
                })
                .build());
    }

    private void shareImage() {
        if (galleryItem == null) {
            return;
        }
        new ImagePipelineBitmapGetter(galleryItem.getPreferredSizedImageUrl()){
            @Override
            public void onSuccess(@Nullable Bitmap bitmap) {
                if (!isAdded()) {
                    return;
                }
                if (callback() != null) {
                    callback().onShare(galleryItem, bitmap, getShareSubject(), imageTitle);
                }
            }
        }.get();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.ACTIVITY_REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (PermissionUtil.isPermitted(grantResults)) {
                    saveImage();
                } else {
                    L.e("Write permission was denied by user");
                    FeedbackUtil.showMessage(getActivity(),
                            R.string.gallery_save_image_write_permission_rationale);
                }
                break;
            default:
                throw new RuntimeException("unexpected permission request code " + requestCode);
        }
    }

    @Nullable
    private String getShareSubject() {
        if (getArguments().getBoolean(ARG_FEED_FEATURED_IMAGE)) {
            return ShareUtil.getFeaturedImageShareSubject(requireContext(), age);
        }
        return pageTitle != null ? pageTitle.getDisplayText() : null;
    }

    private void saveImage() {
        if (galleryItem != null && callback() != null) {
            callback().onDownload(galleryItem);
        }
    }

    @Nullable private Callback callback() {
        return FragmentUtil.getCallback(this, Callback.class);
    }
}
