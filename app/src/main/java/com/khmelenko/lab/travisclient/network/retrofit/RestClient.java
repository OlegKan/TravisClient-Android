package com.khmelenko.lab.travisclient.network.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khmelenko.lab.travisclient.common.Constants;
import com.khmelenko.lab.travisclient.storage.AppSettings;
import com.khmelenko.lab.travisclient.util.PackageUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * REST client for Network communication
 *
 * @author Dmytro Khmelenko
 */
public final class RestClient {
    private static final String GITHUB_URL = Constants.GITHUB_URL;

    private TravisApiService mApiService;
    private GithubApiService mGithubApiService;
    private RawApiService mRawApiService;

    private OkHttpClient mHttpClient;

    private RestClient() {

        initHttpClient();

        final String travisUrl = AppSettings.getServerUrl();
        updateTravisEndpoint(travisUrl);

        // rest adapter for github API service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(GITHUB_URL)
                .setConverter(new GsonConverter(constructGsonConverter()))
                .setErrorHandler(new RestErrorHandling())
                .build();
        mGithubApiService = restAdapter.create(GithubApiService.class);
    }

    /**
     * Initializes HTTP client
     */
    private void initHttpClient() {
        mHttpClient = new OkHttpClient();
        mHttpClient.setFollowRedirects(false);
    }

    /**
     * Creates new instance of the rest client
     *
     * @return Instance
     */
    public static RestClient newInstance() {
        return new RestClient();
    }

    /**
     * Updates Travis endpoint
     *
     * @param newEndpoint New endpoint
     */
    public void updateTravisEndpoint(String newEndpoint) {
        // rest adapter for travis API service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(newEndpoint)
                .setConverter(new GsonConverter(constructGsonConverter()))
                .setRequestInterceptor(constructRequestInterceptor())
                .setErrorHandler(new RestErrorHandling())
                .build();
        mApiService = restAdapter.create(TravisApiService.class);

        // rest adapter for raw calls
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(newEndpoint)
                .setConverter(new GsonConverter(constructGsonConverter()))
                .setErrorHandler(new RestErrorHandling())
                .setClient(new OkClient(mHttpClient))
                .build();
        mRawApiService = restAdapter.create(RawApiService.class);
    }

    /**
     * Constructs request interceptor
     *
     * @return Request interceptor
     */
    private RequestInterceptor constructRequestInterceptor() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String userAgent = String.format("TravisClient/%1$s", PackageUtils.getAppVersion());
                request.addHeader("User-Agent", userAgent);
                request.addHeader("Accept", "application/vnd.travis-ci.2+json");

                String accessToken = AppSettings.getAccessToken();
                if (!TextUtils.isEmpty(accessToken)) {
                    String headerValue = String.format("token %1$s", accessToken);
                    request.addHeader("Authorization", headerValue);
                }
            }
        };
        return requestInterceptor;
    }

    /**
     * Construct Gson converter
     *
     * @return Gson converter
     */
    private Gson constructGsonConverter() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();
        return gson;
    }

    /**
     * Gets travis API service
     *
     * @return Travis API service
     */
    public TravisApiService getApiService() {
        return mApiService;
    }

    /**
     * Gets Github API service
     *
     * @return Github API service
     */
    public GithubApiService getGithubApiService() {
        return mGithubApiService;
    }

    /**
     * Gets Raw API service
     *
     * @return Raw API service
     */
    public RawApiService getRawApiService() {
        return mRawApiService;
    }

    /**
     * Executes single request
     *
     * @param url URL for request
     * @return Response
     */
    public Response singleRequest(String url) throws IOException {

        mHttpClient.setFollowRedirects(false);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mHttpClient.newCall(request).execute();
        return response;
    }
}
