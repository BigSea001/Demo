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


import java.io.Serializable;

/**
 * Represents media elements uploaded with the Tweet.
 */
public class MediaEntity extends UrlEntity {

    /**
     * ID of the media expressed as a 64-bit integer.
     */
    public final long id;

    /**
     * ID of the media expressed as a string.
     */
    public final String idStr;

    /**
     * A http:// URL pointing directly to the uploaded media file.
     *
     * For media in direct messages, media_url is the same https URL as media_url_https and must be
     * accessed via an authenticated twitter.com session or by signing a request with the user's
     * access token using OAuth 1.0A. It is not possible to directly embed these images in a web
     * page.
     */
    public final String mediaUrl;

    /**
     * A https:// URL pointing directly to the uploaded media file, for embedding on https pages.
     *
     * For media in direct messages, media_url_https must be accessed via an authenticated
     * twitter.com session or by signing a request with the user's access token using OAuth 1.0A.
     * It is not possible to directly embed these images in a web page.
     */
    public final String mediaUrlHttps;

    /**
     * An object showing available sizes for the media file.
     */
    public final Sizes sizes;

    /**
     * For Tweets containing media that was originally associated with a different tweet, this ID
     * points to the original Tweet.
     */
    public final long sourceStatusId;

    /**
     * For Tweets containing media that was originally associated with a different tweet, this
     * string-based ID points to the original Tweet.
     */
    public final String sourceStatusIdStr;

    /**
     * Type of uploaded media.
     */
    public final String type;

    /**
     * An object showing details for the video file. This field is present only when there is a
     * video in the payload.
     */
    public final VideoInfo videoInfo;

    public final String altText;

    public MediaEntity(String url, String expandedUrl, String displayUrl,
            long id, String idStr, String mediaUrl, String mediaUrlHttps, Sizes sizes,
            long sourceStatusId, String sourceStatusIdStr, String type, VideoInfo videoInfo,
            String altText) {
        super(url, expandedUrl, displayUrl);
        this.id = id;
        this.idStr = idStr;
        this.mediaUrl = mediaUrl;
        this.mediaUrlHttps = mediaUrlHttps;
        this.sizes = sizes;
        this.sourceStatusId = sourceStatusId;
        this.sourceStatusIdStr = sourceStatusIdStr;
        this.type = type;
        this.videoInfo = videoInfo;
        this.altText = altText;
    }

    public static class Sizes implements Serializable {
        /**
         * Information for a medium-sized version of the media.
         */
        public final Size medium;

        /**
         * Information for a thumbnail-sized version of the media.
         */
        public final Size thumb;

        /**
         * Information for a small-sized version of the media.
         */
        public final Size small;

        /**
         * Information for a large-sized version of the media.
         */
        public final Size large;

        public Sizes(Size thumb, Size small, Size medium, Size large) {
            this.thumb = thumb;
            this.small = small;
            this.medium = medium;
            this.large = large;
        }
    }

    public static class Size implements Serializable {
        /**
         * Width in pixels of this size.
         */
        public final int w;

        /**
         * Height in pixels of this size.
         */
        public final int h;

        /**
         * Resizing method used to obtain this size. A value of fit means that the media was resized
         * to fit one dimension, keeping its native aspect ratio. A value of crop means that the
         * media was cropped in order to fit a specific resolution.
         */
        public final String resize;

        public Size(int w, int h, String resize) {
            this.w = w;
            this.h = h;
            this.resize = resize;
        }
    }
}
