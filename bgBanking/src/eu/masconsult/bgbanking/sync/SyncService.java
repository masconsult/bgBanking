/*******************************************************************************
 * Copyright (c) 2012 MASConsult Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package eu.masconsult.bgbanking.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import eu.masconsult.bgbanking.BankingApplication;

/**
 * Service to handle Account sync. This is invoked with an intent with action
 * ACTION_AUTHENTICATOR_INTENT. It instantiates the syncadapter and returns its
 * IBinder.
 */
public class SyncService extends Service {

    private static final String TAG = BankingApplication.TAG + "SyncService";

    private static final Object sSyncAdapterLock = new Object();

    private static SyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.v(TAG, getClass().getName() + ".onCreate()");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, getClass().getName() + ".onBind()");
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
