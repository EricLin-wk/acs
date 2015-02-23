package com.acs.biz.report.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.acs.biz.log.service.LogDeviceDailyService;
import com.acs.biz.log.service.LogDeviceHourlyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/report")
public class DeviceReportResource extends SpringBeanAutowiringSupport {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogDeviceHourlyService logDeviceHourlyService;
	@Autowired
	private LogDeviceDailyService logDeviceDailyService;

	public DeviceReportResource() {
		super();
	}

	@GET
	@Path("/device/hourly/{deviceId}")
	@Produces("application/json")
	public String getDeviceHourlyData(@PathParam("deviceId") long deviceId) {
		Calendar cal = Calendar.getInstance();
		Date recordDateEnd = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -60);
		Date recordDateStart = cal.getTime();

		try {
			List<Map<String, Object>> result = logDeviceHourlyService.listByDeviceId_RecordDate(deviceId, recordDateStart,
					recordDateEnd);
			Gson gson = new GsonBuilder().create();
			String jsonStr = gson.toJson(result);
			return jsonStr;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/device/daily/{deviceId}")
	@Produces("application/json")
	public String getDeviceDailyData(@PathParam("deviceId") long deviceId) {
		Calendar cal = Calendar.getInstance();
		Date recordDateEnd = cal.getTime();
		cal.add(Calendar.YEAR, -5);
		Date recordDateStart = cal.getTime();

		try {
			List<Map<String, Object>> result = logDeviceDailyService.listByDeviceId_RecordDate(deviceId, recordDateStart,
					recordDateEnd);
			Gson gson = new GsonBuilder().create();
			String jsonStr = gson.toJson(result);
			return jsonStr;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/group/hourly/{groupId}")
	@Produces("application/json")
	public String getGroupHourlyData(@PathParam("groupId") long groupId) {
		Calendar cal = Calendar.getInstance();
		Date recordDateEnd = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -60);
		Date recordDateStart = cal.getTime();

		try {
			List<Map<String, Object>> result = logDeviceHourlyService.listGroupByGroupId_RecordDate(groupId, recordDateStart,
					recordDateEnd);
			Gson gson = new GsonBuilder().create();
			String jsonStr = gson.toJson(result);
			return jsonStr;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/group/daily/{groupId}")
	@Produces("application/json")
	public String getGroupDailyData(@PathParam("groupId") long groupId) {
		Calendar cal = Calendar.getInstance();
		Date recordDateEnd = cal.getTime();
		cal.add(Calendar.YEAR, -5);
		Date recordDateStart = cal.getTime();

		try {
			List<Map<String, Object>> result = logDeviceDailyService.listGroupByGroupId_RecordDate(groupId, recordDateStart,
					recordDateEnd);
			Gson gson = new GsonBuilder().create();
			String jsonStr = gson.toJson(result);
			return jsonStr;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
