<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.teecs.com/jsp/teecs" prefix="teecs"%>
<%@ page import="com.acs.core.user.utils.AdminHelper"%>
<%@page import="com.acs.core.user.entity.*" %>
<div id="menu" class="span2 main-menu-span accordion">	
	<div class="well nav-collapse sidebar-nav" style="height: auto;">	
	<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
		<div id="menuGroup1" class="accordion-heading" data-collapse="persist">
			<a class="accordion-toggle"> 监控报表</a>
			<div><!-- 此div为缩放范围 -->
				<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
					<div class="accordion-inner">
						<s:a value="/dashboard/list.do">仪表板</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
					<div class="accordion-inner">
						<s:a value="/deviceHourlyRpt/init.do">设备每小时报表</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
					<div class="accordion-inner">
						<s:a value="/deviceDailyRpt/init.do">设备每日报表</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
					<div class="accordion-inner">
						<s:a value="/groupHourlyRpt/init.do">群组每小时报表</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_MONITOR_RPT">
					<div class="accordion-inner">
						<s:a value="/groupDailyRpt/init.do">群组每日报表</s:a>
					</div>
				</teecs:checkPermission>
			</div>
		</div>	
	</teecs:checkPermission>	
	<teecs:checkPermission permissions="ROLE_DEVICE_MGMT">	
		<div id="menuGroup2" class="accordion-heading" data-collapse="persist">
			<a class="accordion-toggle"> 设备管理</a>
			<div><!-- 此div为缩放范围 -->
				<teecs:checkPermission permissions="ROLE_DEVICE_MGMT">
					<div class="accordion-inner">
						<s:a value="/deviceSetting/init.do">温度湿度设置</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_DEVICE_MGMT">
					<div class="accordion-inner">
						<s:a value="/device/init.do">设备维护</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_DEVICE_MGMT">
					<div class="accordion-inner">
						<s:a value="/deviceGroup/list.do">设备群组维护</s:a>
					</div>
				</teecs:checkPermission>
			</div>
		</div>	
	</teecs:checkPermission>	
		<div id="menuGroup9" class="accordion-heading"  data-collapse="persist">
			<a class="accordion-toggle"> 权限管理</a>
			<div><!-- 此div为缩放范围 -->
				<teecs:checkPermission permissions="ROLE_USER">
					<div class="accordion-inner">
						<s:a value="/userSelf/view.do">个人资料维护</s:a>
					</div>
				</teecs:checkPermission>
				<teecs:checkPermission permissions="ROLE_HR">
					<%--<div class="accordion-inner">
						<s:a value="/menu/list.do">选单维护管理 </s:a>
					</div>
					<div class="accordion-inner">
						<s:a value="/group/list.do">Group维护管理 </s:a>
					</div>
					<div class="accordion-inner">
						<s:a value="/role/list.do">Role维护管理 </s:a>
					</div>
					<div class="accordion-inner">
						<s:a value="/permission/list.do">Permission维护管理 </s:a>
					</div> --%>
					<div class="accordion-inner">
						<s:a value="/user/list.do">用户维护管理 </s:a>
					</div> 
				</teecs:checkPermission>
			</div>
		</div>
	</div>	
</div>
<!--/span-->