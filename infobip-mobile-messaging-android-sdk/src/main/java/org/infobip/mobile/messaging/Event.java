package org.infobip.mobile.messaging;

import android.content.BroadcastReceiver;

/**
 * Enumerates all events generated by the Mobile Messaging library.
 * It is intended to be used in <i>BroadcastReceivers</i> registered locally or at the application level.
 * <p>
 * You can register receivers in AndroidManifest.xml by adding:
 * <pre>
 * {@code <receiver android:name=".MyMessageReceiver" android:exported="false">
 *       <intent-filter>
 *           <action android:name="org.infobip.mobile.messaging.MESSAGE_RECEIVED"/>
 *        </intent-filter>
 *   </receiver>}
 * </pre>
 * <p>
 * You can also register receivers in you Activity by adding:
 * <pre>
 * {@code
 * public class MyActivity extends AppCompatActivity {
 *        private boolean isReceiverRegistered;
 *        private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
 *            public void onReceive(Context context, Intent intent) {
 *                Message message = new Message(intent.getExtras());
 *                ... process your message here
 *            }
 *        };
 *
 *        protected void onCreate(Bundle savedInstanceState) {
 *            super.onCreate(savedInstanceState);
 *
 *            registerReceiver();
 *        }
 *
 *        protected void onResume() {
 *            super.onResume();
 *            registerReceiver();
 *        }
 *
 *        protected void onPause() {
 *            LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
 *            isReceiverRegistered = false;
 *            super.onPause();
 *        }
 *
 *        private void registerReceiver() {
 *            if (isReceiverRegistered) {
 *                return;
 *            }
 *            LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
 *            new IntentFilter(Event.MESSAGE_RECEIVED.getKey()));
 *            isReceiverRegistered = true;
 *        }
 *    }}
 * </pre>
 *
 * @author mstipanov
 * @see BroadcastReceiver
 * @see Event#MESSAGE_RECEIVED
 * @see Event#REGISTRATION_ACQUIRED
 * @see Event#REGISTRATION_CREATED
 * @see Event#DELIVERY_REPORTS_SENT
 * @see Event#API_COMMUNICATION_ERROR
 * @since 01.03.2016.
 */
public enum Event {
    /**
     * It is triggered when GCM registration token is received.
     * <p>
     * Contains the GCM registration token.
     * <pre>
     * {@code
     * String gcmRegistrationToken = intent.getStringExtra({@link BroadcastParameter#EXTRA_CLOUD_TOKEN });
     * }
     * </pre>
     */
    REGISTRATION_ACQUIRED("org.infobip.mobile.messaging.REGISTRATION_ACQUIRED"),

    /**
     * It is triggered when GCM registration token successfully stored on the registration server.
     * <p>
     * Contains the GCM registration token and Infobip device application instance ID
     * (which identifies every application instance).
     * <pre>
     * {@code
     * String registrationId = intent.getStringExtra({@link BroadcastParameter#EXTRA_CLOUD_TOKEN });
     * String pushRegistrationId = intent.getStringExtra({@link BroadcastParameter#EXTRA_INFOBIP_ID});
     * }
     * </pre>
     */
    REGISTRATION_CREATED("org.infobip.mobile.messaging.REGISTRATION_CREATED"),

    /**
     * It is triggered when message is received.
     * <p>
     * Contains the received message information.
     * <pre>
     * {@code
     * Message message = Message.createFrom(intent.getExtras());
     * }
     * </pre>
     *
     * @see Message
     */
    MESSAGE_RECEIVED("org.infobip.mobile.messaging.MESSAGE_RECEIVED"),

    /**
     * It is triggered when messages are sent.
     * <p>
     * Contains messages that were sent and status information for each message.
     * <pre>
     * {@code
     * List<Message> messages = Message.createFrom(intent.getParcelableArrayListExtra(BroadcastParameter.EXTRA_MESSAGES));
     * }
     * </pre>
     *
     * @see Message
     */
    MESSAGES_SENT("org.infobip.mobile.messaging.MESSAGES_SENT"),

    /**
     * It is triggered on every error returned by API.
     * <p>
     * Contains the exception information.
     * <pre>
     * {@code
     * MobileMessagingError mobileMessagingError = (MobileMessagingError) intent.getSerializableExtra(EXTRA_EXCEPTION);
     * }
     * </pre>
     */
    API_COMMUNICATION_ERROR("org.infobip.mobile.messaging.API_COMMUNICATION_ERROR"),

    /**
     * It is triggered when message delivery is reported.
     * <p>
     * Contains the list of all reported message IDs.
     * <pre>
     * {@code
     * String[] messageIDs = intent.getStringArrayExtra({@link BroadcastParameter#EXTRA_MESSAGE_IDS});
     * }
     * </pre>
     */
    DELIVERY_REPORTS_SENT("org.infobip.mobile.messaging.DELIVERY_REPORTS_SENT"),

    /**
     * It is triggered when message seen is reported.
     * <p>
     * Contains the list of all reported message IDs.
     * <pre>
     * {@code
     * String[] messageIDs = intent.getStringArrayExtra({@link BroadcastParameter#EXTRA_MESSAGE_IDS});
     * }
     * </pre>
     */
    SEEN_REPORTS_SENT("org.infobip.mobile.messaging.SEEN_REPORTS_SENT"),

    /**
     * It is triggered when notification is tapped.
     * <p>
     * Contains message that was sent.
     * <pre>
     * {@code
     * Message message = Message.createFrom(intent.getExtras());
     * }
     * </pre>
     */
    NOTIFICATION_TAPPED("org.infobip.mobile.messaging.NOTIFICATION_TAPPED"),

    /**
     * It is triggered when user data is successfully reported to the server.
     * <pre>
     * {@code
     * UserData userData = UserData.createFrom(intent.getExtras());
     * }
     * </pre>
     */
    USER_DATA_REPORTED("org.infobip.mobile.messaging.USER_DATA_REPORTED"),

    /**
     * It is triggered when system data is successfully reported to the server.
     * <pre>
     * {@code
     * SystemData systemData = SystemData.createFrom(intent.getExtras());
     * }
     * </pre>
     */
    SYSTEM_DATA_REPORTED("org.infobip.mobile.messaging.SYSTEM_DATA_REPORTED"),

    /**
     * It is triggered when user is successfully logged out from the server.
     */
    USER_LOGGED_OUT("org.infobip.mobile.messaging.USER_LOGGED_OUT"),

    /**
     * It is triggered when Google Play Services are not available.
     * <pre>
     * {@code
     * int playServicesErrorCode = intent.getIntExtra(BroadcastParameter.EXTRA_PLAY_SERVICES_ERROR_CODE);
     * }
     * </pre>
     */
    GOOGLE_PLAY_SERVICES_ERROR("org.infobip.mobile.messaging.GOOGLE_PLAY_SERVICES_ERROR"),

    /**
     * It is triggered when push registration status is changed.
     * <pre>
     * {@code
     * boolean isPushRegistrationEnabled = intent.getBooleanExtra(BroadcastParameter.EXTRA_PUSH_REGISTRATION_ENABLED);
     * }
     * </pre>
     */
    PUSH_REGISTRATION_ENABLED("org.infobip.mobile.messaging.PUSH_REGISTRATION_ENABLED"),

    /**
     * It is triggered when primary setting changes for device.
     * <pre>
     * {@code
     * boolean isPrimary = intent.getBooleanExtra(BroadcastParameter.EXTRA_IS_PRIMARY);
     * }
     * </pre>
     */
    PRIMARY_CHANGED("org.infobip.mobile.messaging.PRIMARY_CHANGED");

    private final String key;

    Event(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
