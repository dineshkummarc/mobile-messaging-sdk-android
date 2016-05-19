package org.infobip.mobile.messaging;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import org.infobip.mobile.messaging.stats.MobileMessagingError;
import org.infobip.mobile.messaging.stats.MobileMessagingStats;
import org.infobip.mobile.messaging.tasks.RegisterMsisdnResult;
import org.infobip.mobile.messaging.tasks.RegisterMsisdnTask;
import org.infobip.mobile.messaging.util.PreferenceHelper;

import static org.infobip.mobile.messaging.MobileMessaging.TAG;

/**
 * @author mstipanov
 * @since 07.04.2016.
 */
class MsisdnSynchronizer {

    void syncronize(Context context, String deviceApplicationInstanceId, long msisdn, boolean msisdnReported, MobileMessagingStats stats) {
        if (null != deviceApplicationInstanceId && msisdnReported) {
            return;
        }

        reportMSISDN(context, msisdn, stats);
    }

    private void reportMSISDN(final Context context, final long msisdn, final MobileMessagingStats stats) {
        if (msisdn <= 0) {
            MobileMessagingCore.getInstance(context).setMsisdnReported(false);
            return;
        }

        new RegisterMsisdnTask(context) {
            @Override
            protected void onPostExecute(RegisterMsisdnResult registerMsisdnResult) {
                if (null == registerMsisdnResult) {
                    Log.e(TAG, "MobileMessaging API didn't return any value!");
                    stats.reportError(MobileMessagingError.MSISDN_SYNC_ERROR);

                    Intent msisdnReportError = new Intent(Event.API_COMMUNICATION_ERROR.getKey());
                    context.sendBroadcast(msisdnReportError);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(msisdnReportError);
                    return;
                }
                MobileMessagingCore.getInstance(context).setMsisdnReported(true);

                Intent msisdnSynced = new Intent(Event.MSISDN_SYNCED.getKey());
                msisdnSynced.putExtra("msisdn", msisdn);
                context.sendBroadcast(msisdnSynced);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msisdnSynced);
            }

            @Override
            protected void onCancelled() {
                Log.e(TAG, "Error reporting MSISDN!");
                stats.reportError(MobileMessagingError.MSISDN_SYNC_ERROR);
            }
        }.execute();
    }
}
