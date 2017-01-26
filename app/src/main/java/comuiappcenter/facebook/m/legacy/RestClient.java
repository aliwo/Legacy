package comuiappcenter.facebook.m.legacy;

/**
 * Created by Administrator on 2017-01-03.
 */

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by Youngdo on 2016-02-20.
 */
public class RestClient
{
    private static Context context;
    public static final String BASE_URL = "http://192.168.219.107:3000"; //서버 주소
    private static AsyncHttpClient client = new AsyncHttpClient();

    public RestClient(Context context)
    {
        if(this.context == null) {this.context = context;}
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(getAbsoluteUrl(url), params, responseHandler);
        PersistentCookieStore CookieStore = new PersistentCookieStore(context);
        client.setCookieStore(CookieStore);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public CookieStore getCookie()
    {
        HttpContext httpContext = client.getHttpContext();
        CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
        return cookieStore;
    }
}

