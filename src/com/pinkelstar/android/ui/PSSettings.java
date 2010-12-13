/*
 *  Copyright (c) <2010> PinkelStar by MillMobile BV

 Permission is hereby granted, free of charge, to any person obtaining a copy

 of this software and associated documentation files (the "Software"), to deal

 in the Software without restriction, including without limitation the rights

 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell

 copies of the Software, and to permit persons to whom the Software is

 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in

 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR

 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,

 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE

 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER

 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,

 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN

 THE SOFTWARE.

Except as contained in this notice, the name(s) and/or trademarks of the above copyright holders shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization.

 
 */
package com.pinkelstar.android.ui;

import pinkelstar.android.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.tasks.RevokeTask;
import com.pinkelstar.android.ui.util.ImageCache;
import com.pinkelstar.android.ui.util.ImageCallback;

public class PSSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.pssettings);
		
		// Run in a 32-bit window, which improves the appearance of some artwork in the  UI 
        Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    
		setupNetworkSelectors();
	}

	/**
	 * called when the authentication webview completes.
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setupNetworkSelectors();
	}

	private void setupNetworkSelectors() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.NetworkList);
		ll.removeAllViews();
		for (String networkName : Server.getInstance().getKnownNetworks()) {
			ll.addView(addNetworkSelector(networkName));
		}
	}

	private ViewGroup addNetworkSelector(String networkName) {
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.psnetworklistitem, null);
		
		createNetworkIcon(networkName, vg);
		createNetworkTitle(networkName, vg);
		createNetworkButton(networkName, vg);

		return vg;
	}

	private void createNetworkIcon(String networkName, ViewGroup vg) {
		final ImageView iv = (ImageView) vg.findViewById(R.id.NetworkIcon);
		String imageUrl = Utils.buildImageUrl(networkName, Constants.SMALL_IMAGES);
		iv.setImageDrawable(getResources().getDrawable(R.drawable.small_placeholder_button_icon));

		ImageCache.getInstance().loadDrawable(imageUrl, new ImageCallback() {
			public void setDrawable(Drawable d) {
				iv.setImageDrawable(d);
			}
		});
	}

	private void createNetworkTitle(String networkName, ViewGroup vg) {
		TextView tv = (TextView) vg.findViewById(R.id.NetworkName);
		tv.setText(networkName);
	}

	private void createNetworkButton(final String networkName, ViewGroup vg) {
		ToggleButton tb = (ToggleButton) vg.findViewById(R.id.NetworkButton);

		tb.setChecked(Server.getInstance().isNetworkAuthenticated(networkName));

		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Server.getInstance().startAuthentication(PSSettings.this, networkName);
				} else {
					new RevokeTask(PSSettings.this).execute(networkName);
				}
			}
		});
	}

}