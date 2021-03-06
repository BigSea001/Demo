/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.dahai.demo.photoview2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.dahai.demo.video.SwipeToDismissTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class GalleryAdapter extends PagerAdapter {
    final List<MediaEntity> items = new ArrayList<>();
    final Context context;
    final SwipeToDismissTouchListener.Callback callback;

    GalleryAdapter(Context context, SwipeToDismissTouchListener.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    void addAll(List<MediaEntity> entities) {
        items.addAll(entities);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final GalleryImageView root = new GalleryImageView(context);
        root.setSwipeToDismissCallback(callback);

        container.addView(root);

        final MediaEntity entity = items.get(position);
        Picasso.get().load(entity.mediaUrlHttps).into(root);

        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
