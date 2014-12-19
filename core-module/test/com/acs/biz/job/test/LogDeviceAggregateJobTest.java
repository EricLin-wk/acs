/**
 *
 */
package com.acs.biz.job.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acs.biz.job.service.LogDeviceAggregateJob;
import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class LogDeviceAggregateJobTest extends SpringCommonTest {

	private static LogDeviceAggregateJob logDeviceAggregateJob;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		logDeviceAggregateJob = (LogDeviceAggregateJob) ctx.getBean("logDeviceAggregateJob");
	}

	@Test
	public void aggregateLogDeviceData() {
		logDeviceAggregateJob.aggregateLogDeviceData();
	}

}
