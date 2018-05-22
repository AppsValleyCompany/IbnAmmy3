package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.av.ibnammy.R;

import static com.av.ibnammy.networkUtilities.ApiClient.WORK_MEDIA_URL;

/**
 * Created by Mina on 5/6/2018.
 */

public class PreviewVideoDialogFrag extends DialogFragment {
    public PreviewVideoDialogFrag() {
    }

    public static PreviewVideoDialogFrag newInstance(String videoUrl) {
        PreviewVideoDialogFrag frag = new PreviewVideoDialogFrag();
        Bundle args = new Bundle();
        args.putString("videoUrl", videoUrl);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_video_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        VideoView videoView = view.findViewById(R.id.video_view);
        final Uri vidUri = Uri.parse(WORK_MEDIA_URL + getArguments().getString("videoUrl"));
        videoView.setVideoURI(vidUri);
        videoView.seekTo(100);
            MediaController videoControl = new MediaController(getActivity());
            videoControl.setAnchorView(videoView);
            videoView.setMediaController(videoControl);
            videoView.start();
        }

}
