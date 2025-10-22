/*
MIT License

Copyright (c) 2016-2023, Openkoda CDX Sp. z o.o. Sp. K. <openkoda.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice
shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.dto.web;

import com.openkoda.dto.CanonicalObject;

import java.net.URI;
import java.net.URISyntaxException;
//TODO Rule 5.2: DTO class name must end with "Dto"
public class WebPage implements CanonicalObject {

    public String url;

    public WebPage(String url) {
        this.url = url;
    }

    public WebPage() {
    }

    //TODO Rule 5.5: DTO should not have code
    public static String getDomain(String url){

        if(!url.contains("http") && !url.contains("https")){
            url = "http://" + url;
        }

        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain;

        } catch (URISyntaxException e) {
            return null;
        }

    }

    @Override
    public String notificationMessage() {
        return String.format("Page: %s", url);
    }
}
