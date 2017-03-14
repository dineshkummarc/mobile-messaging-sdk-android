package org.infobip.mobile.messaging;

import org.infobip.mobile.messaging.mobile.BatchReporter;
import org.infobip.mobile.messaging.tools.InfobipAndroidTestCase;
import org.infobip.mobile.messaging.util.PreferenceHelper;
import org.mockito.Mockito;

/**
 * @author sslavin
 * @since 07/07/16.
 */
public class BatchReporterTest extends InfobipAndroidTestCase {

    private BatchReporter batchReporter;
    private Runnable runnable;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PreferenceHelper.saveLong(getInstrumentation().getContext(), MobileMessagingProperty.BATCH_REPORTING_DELAY, 50L);

        batchReporter = new BatchReporter(getInstrumentation().getContext());
        runnable = Mockito.mock(Runnable.class);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test_scheduleMultipleRunOne() throws Exception {

        for (int i = 0; i < 5; i++) {
            batchReporter.put(runnable);
        }

        Mockito.verify(runnable, Mockito.after(500).atMost(1)).run();
    }

    public void test_scheduleMultipleRunMultiple() throws Exception {

        for (int i = 0; i < 5; i++) {
            batchReporter.put(runnable);
            Thread.sleep(200);
        }

        Mockito.verify(runnable, Mockito.times(5)).run();
    }
}
