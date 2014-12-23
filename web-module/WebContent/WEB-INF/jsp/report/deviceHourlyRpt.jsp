<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<c:url value="../css" var="cssPath" />
<c:url value="../js" var="jsPath" />
<link rel="stylesheet" href="${cssPath}/jqx.base.css" type="text/css" />
<script type="text/javascript" src="${jsPath}/jqxcore.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdraw.js"></script>
<script type="text/javascript" src="${jsPath}/jqxchart.core.js"></script>
<script type="text/javascript" src="${jsPath}/jqxchart.rangeselector.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdata.js"></script>
<style>
#deviceSearchForm label.error  {
	width: auto;
	display: inline;
	color: #B94A48;
}
#groupSearchForm label.error  {
	width: auto;
	display: inline;
	color: #B94A48;
}
</style>
<script>
$().ready(function() {
	$("#deviceSearchForm").validate({
		rules: {
			"paraDeviceId": "required"						
		},
		messages: {
			"paraDeviceId": "必须选择设备"	
		}
	});
	
});
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success" >
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionmessage cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionerror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:fielderror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>	
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 设备每小时报表
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>	
		<div class="box-content">
			<s:form action="list.do" method="post" id="deviceSearchForm">
			<div class="row-fluid">			
				<div class="span1">选择设备</div>
				<div class="span2">
					<select data-placeholder="选择设备" id="paraDeviceId" name="paraDeviceId" data-rel="chosen" >
					<c:forEach items="${deviceMap}" var="entry">
						<optgroup label="${entry.key}">
						<c:forEach items="${entry.value}" var="device">
						<option value="${device.oid}" <c:if test="${device.oid == paraDeviceId}">selected</c:if>>${device.deviceName}</option>
						</c:forEach>
						</optgroup>
					</c:forEach>
					</select>		
				</div>
				<div class="span1">
						<button type="submit" class="btn btn-primary">搜索</button>
					</div>
			</div>
			</s:form>					
		</div>
	</div>	
</div>
<script>
$().ready(function() {
	// prepare the data
    var source = {
        datatype: "json",
        datafields: [
            { name: 'record_date', type: 'date' },
            { name: 'temperature', type: 'number' },
            { name: 'humidity', type: 'number' }
            ],
        url: 'jsonData.do?paraDeviceId=${paraDeviceId}'
    };
    var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
    var toolTipT = function (value, itemIndex, serie, group, categoryValue, categoryAxis) {
        var dataItem = dataAdapter.records[itemIndex];
        return '<DIV style="text-align:left"><b>記錄時間:</b> ' + $.jqx.dataFormat.formatdate(categoryValue, "yyyy-MM-dd HH:mm", 'en-us') +
                '<br /><b>溫度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.temperature, 'd2') + ' °C </DIV>';
    };
    var toolTipH = function (value, itemIndex, serie, group, categoryValue, categoryAxis) {
        var dataItem = dataAdapter.records[itemIndex];
        return '<DIV style="text-align:left"><b>記錄時間:</b> ' + $.jqx.dataFormat.formatdate(categoryValue, "yyyy-MM-dd HH:mm", 'en-us') +
                '<br /><b>溼度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.humidity, 'd2')  + ' % </DIV>';
    };
    // prepare jqxChart settings
    var recordDateStart = new Date(${recordDateStart.year + 1900}, ${recordDateStart.month}, ${recordDateStart.date});
    var recordDateEnd = new Date(${recordDateEnd.year + 1900}, ${recordDateEnd.month}, ${recordDateEnd.date});
    var rangeStart = new Date(${recordDateEnd.year + 1900}, ${recordDateEnd.month}, ${recordDateEnd.date-7});
    var settings = {
        title: "设备:${deviceObj.deviceName}",
        description: "设备类型:${menuDeviceType.options[deviceObj.deviceType].name} / 型号:${deviceObj.model} / 序列号:${deviceObj.serialNum} / " + $.jqx.dataFormat.formatdate(recordDateStart, "yyyy-MM-dd", 'en-us') + " ~ "  + $.jqx.dataFormat.formatdate(recordDateEnd, "yyyy-MM-dd", 'en-us'),
        enableAnimations: true,
        animationDuration: 1500,
        enableCrosshairs: true,
        crosshairsLineWidth: 1.5,
        crosshairsColor: '#5E5E5E',
        padding: { left: 5, top: 5, right: 15, bottom: 5 },
        source: dataAdapter,
        xAxis:
            {
                dataField: 'record_date',
                formatFunction: function (value) {
                	return $.jqx.dataFormat.formatdate(value, "MM-dd HH:mm", 'en-us');                	
                },
                type: 'date',
                baseUnit: 'hour',
                textRotationAngle: -45,
        		textOffset: { x: -10, y: 0 },
                rangeSelector: {
                    //size: 120,
                    padding: { /*left: 0, right: 0,*/top: 20, bottom: 0 },
                    minValue: recordDateStart,
                    maxValue: recordDateEnd,
                    backgroundColor: 'white',
                    dataField: 'temperature',
                    baseUnit: 'day',
                    showGridLines: false,
                    formatFunction: function (value) {
                    	return $.jqx.dataFormat.formatdate(value, "yyyy-MM-dd", 'en-us');  
                    },
                    range: {min: 'day', from: rangeStart, to: recordDateEnd}
                }
            },
        colorScheme: 'scheme24',
        seriesGroups:
            [
                {
                    type: 'line',
                    toolTipFormatFunction: toolTipT,
                    valueAxis:
                    {
                        description: '溫度°C',
                        showGridLines: false
                    },
                    series: [
                        { dataField: 'temperature', displayText: '溫度', lineWidth: 2, lineWidthSelected: 2, lineColor: '#F44C73', fillColor: '#F44C73' }                        
                    ]
                },
                {
                    type: 'line',
                    toolTipFormatFunction: toolTipH,
                    valueAxis:
                    {
                    	position: 'right',
                    	unitInterval: 20,
                    	maxValue: 100,
                        description: '溼度%',
                        showGridLines: false
                    },
                    series: [
                        { dataField: 'humidity', displayText: '溼度', lineWidth: 2, lineWidthSelected: 2, lineColor: '#4492DB', fillColor: '#4492DB' }
                    ]
                }
            ]
    };
    <s:if test="paraDeviceId != null">
    $('#chartContainer').jqxChart(settings);
    </s:if>
});
</script>
<div class="row-fluid">
	<div id='chartContainer' style="width:800px; height:500px;">
	</div>	
</div>
