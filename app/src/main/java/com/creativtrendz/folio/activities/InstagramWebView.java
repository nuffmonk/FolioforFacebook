package com.creativtrendz.folio.activities;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.creativtrendz.folio.services.Connectivity;


public class InstagramWebView extends WebViewClient {


	private boolean refreshed;

	private static Context context = FolioApplication.getContextOfApplication();


	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

		if (Uri.parse(url).getHost().endsWith("instagram.com")
				|| Uri.parse(url).getHost().endsWith("instagram.com/accounts/signup/")
				|| Uri.parse(url).getHost().endsWith("facebook.com")
				|| Uri.parse(url).getHost().endsWith("*facebook.com")) {
			return false;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		try {
			view.getContext().startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Log.e("shouldOverrideUrlLoad", "" + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}


	@SuppressWarnings("deprecation")
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

		if (Connectivity.isConnected(context) && !refreshed) {
			view.loadUrl(failingUrl);

			refreshed = true;
		}
	}

	@TargetApi(android.os.Build.VERSION_CODES.M)
	@Override
	public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {

		onReceivedError(view, err.getErrorCode(), err.getDescription().toString(), req.getUrl().toString());
	}

}
