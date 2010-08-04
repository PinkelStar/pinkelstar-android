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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.PinkelstarStatable;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.tasks.RevokeTask;
import com.pinkelstar.android.ui.util.ImageCache;
import com.pinkelstar.android.ui.util.ImageCallback;

public class PSSettings extends Activity {

	private Server psServer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		PinkelstarStatable app = (PinkelstarStatable) getApplication();
		this.psServer = app.getPinkelstarServer();

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

	void setupNetworkSelectors() {
		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout ll = (LinearLayout) li.inflate(R.layout.pssettings, null);

		for (String networkName : psServer.getKnownNetworks()) {
			RelativeLayout rl = addNetworkSelector(networkName);
			ll.addView(rl, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}

		setContentView(ll);
	}

	
	RelativeLayout addNetworkSelector(String networkName) {
		RelativeLayout rl = new RelativeLayout(PSSettings.this);
		rl.setPadding(10, 10, 10, 10);
		
		createNetworkIcon(networkName, rl);
		createNetworkTitle(networkName, rl);
		createNetworkButton(networkName, rl);

		return rl;
	}

	private void createNetworkIcon(String networkName, RelativeLayout rl) {
		final ImageView iv = new ImageView(PSSettings.this);
		String imageUrl = Utils.buildImageUrl(networkName, Constants.SMALL_IMAGES);

		ImageCache.getInstance().loadDrawable(imageUrl, new ImageCallback() {
			public void setDrawable(Drawable d) {
				iv.setImageDrawable(d);
			}
		});

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rl.addView(iv, rlp);
	}

	private void createNetworkTitle(String networkName, RelativeLayout rl) {
		TextView tv = new TextView(PSSettings.this);
		tv.setText(networkName);

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		rl.addView(tv, rlp);
	}

	private void createNetworkButton(final String networkName, RelativeLayout rl) {
		ToggleButton tb = new ToggleButton(PSSettings.this);

		tb.setButtonDrawable(R.drawable.togglebuttons);
		tb.setBackgroundResource(R.drawable.nullimg);
		tb.setChecked(psServer.isNetworkAuthenticated(networkName));

		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					psServer.startAuth(PSSettings.this, networkName);
				} else {
					new RevokeTask(psServer, PSSettings.this).execute(networkName);
				}
			}
		});

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);

		rl.addView(tb, rlp);
	}

}
