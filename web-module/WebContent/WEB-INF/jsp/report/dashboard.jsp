<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<c:url value="../css" var="cssPath" />
<c:url value="../js" var="jsPath" />
<link rel="stylesheet" href="${cssPath}/jqx.base.css" type="text/css" />
<script type="text/javascript" src="${jsPath}/jqxcore.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdraw.js"></script>
<script type="text/javascript" src="${jsPath}/jqxchart.core.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdata.js"></script>
<script type="text/javascript" src="${jsPath}/jqxprogressbar.js"></script>
<script type="text/javascript">
//save and load scoll position
$().ready(function() {
	var position = $.cookie('scroll_position');
	if(!position){return}
    var ar = position.split("_")
    if(ar.length == 2){
    	$('html,body').scrollLeft(parseInt(ar[0]));
    	$('html,body').scrollTop(parseInt(ar[1]));        
    }	
});

$(window).bind('beforeunload', function () {
	var x = $('html,body').scrollLeft();
    var y = $('html,body').scrollTop();
    var position=x + "_" + y;
    $.cookie('scroll_position', position, { expires: 1 });	
});


$(document).ready(function () {
	//json data	
	var jsonData = ${jsonData};
	var seriesDataTemperature = ${seriesDataTemperature};
	var seriesDataHumidity = ${seriesDataHumidity};
	
	var source = {
        localdata: jsonData,        
        datatype: "json"
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    var xAxis = {
		dataField: 'recordDate',
		type: 'date',
		baseUnit: 'minute',
		unitInterval: 5,
		formatFunction: function (value) {
		    return $.jqx.dataFormat.formatdate(value, "hh:mm", 'en-us');
		},
		gridLinesInterval: 200,
		valuesOnTicks: true,
		textRotationAngle: -45,
		textOffset: { x: -10, y: 0 }
	};
	// prepare jqxChart settings
    var settingsTemperature = {
        title: " ",
        description: " ",
        enableAnimations: true,
        showLegend: true,
        padding: { left: 0, top: 0, right: 10, bottom: 0 },
        //titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
        source: dataAdapter,
        xAxis: xAxis,
        colorScheme: 'scheme01',
        seriesGroups:
            [
                {
                    type: 'line',
                    toolTipFormatSettings: { sufix: '°C' },
                    valueAxis:
                    {
                        displayValueAxis: true,
                        description: '温度°C',
                        axisSize: 'auto',
                        tickMarksColor: '#888888'
                    },
                    series: seriesDataTemperature
                }
            ]
    };
    var settingsHumidity = {
            title: " ",
            description: " ",
            enableAnimations: true,
            showLegend: true,
            padding: { left: 0, top: 0, right: 10, bottom: 0 },
            //titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
            source: dataAdapter,
            xAxis: xAxis,
            colorScheme: 'scheme01',
            seriesGroups:
                [
                    {
                        type: 'line',
                        toolTipFormatSettings: { sufix: '%' },
                        valueAxis:
                        {
                            displayValueAxis: true,
                            description: '湿度%',
                            axisSize: 'auto',
                            tickMarksColor: '#888888'
                        },
                        series: seriesDataHumidity
                    }
                ]
        };
    // setup the chart
    $('#jqxChartTemperature').jqxChart(settingsTemperature);
    $('#jqxChartHumidity').jqxChart(settingsHumidity);
    // set up progress bar
    $("#jqxProgressBar").jqxProgressBar({width: 150, height: 20, value: 0});    
	
});
//setTimeout(function () {location.reload(true); }, 60000);
var refreshTime = 60;
setInterval(function(){ 
	var newValue = $('#jqxProgressBar').jqxProgressBar('value') + (100/refreshTime);
	$('#jqxProgressBar').jqxProgressBar({  value: newValue});
	if(newValue >= 100)
		location.reload(true);
}, 1000);
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
				<i class="icon-tag"></i> 仪表板
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>
		<div class="box-content">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>设备名称</th>
						<th>设备类型</th>
						<th>连线状态</th>
						<th>温度/目标温度</th>
						<th>湿度/目标湿度</th>
						<th>更新时间</th>
						<th>报表</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${deviceMap}" var="entry" >
						<tr>
							<td class="left" colspan="7" style="background-color: #EDEDED;"><strong>${entry.key.groupName}</strong></td>				
						</tr>
						<c:forEach items="${entry.value}" var="obj" varStatus="rows_index">
						<tr>
							<td class="center" style="padding-left: 20px;"><span data-rel="popover" title="${obj.deviceName}" data-content="序列号: ${obj.serialNum} <br/>设备类型: ${menuDeviceType.options[obj.deviceType].name} <br/>型号: ${obj.model}">${obj.deviceName}</span></td>
							<td class="center">${menuDeviceType.options[obj.deviceType].name}</td>
							<td class="center"><span class='label 
							<c:choose><c:when test="${statusMap[obj.oid].operationStatus eq statusMap[obj.oid].statusConnected}">label-success</c:when>
								<c:when test="${statusMap[obj.oid].operationStatus eq statusMap[obj.oid].statusDisconnected}">label-important</c:when>
								<c:when test="${statusMap[obj.oid].operationStatus eq statusMap[obj.oid].statusConnectionError}">label-important</c:when>
								<c:otherwise></c:otherwise></c:choose>' data-rel="popover" title="${statusMap[obj.oid].operationStatus}" data-content="IP地址: ${obj.ipAddress} <br/>端口: ${obj.port}">
								${statusMap[obj.oid].operationStatus}</span></td>
							<td class="center"><fmt:formatNumber type="number" pattern="0.0" value="${statusMap[obj.oid].temperature}" />°C / <fmt:formatNumber type="number" pattern="0.0" value="${statusMap[obj.oid].targetTemperature}" />°C </td>
							<td class="center"><fmt:formatNumber type="number" pattern="0.0" value="${statusMap[obj.oid].humidity}" />% / <fmt:formatNumber type="number" pattern="0.0" value="${statusMap[obj.oid].targetHumidity}" />%</td>
							<td class="center"><fmt:formatDate value="${statusMap[obj.oid].statusDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class="center">DDL</td>
						</tr>
						</c:forEach>			
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="box span6">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 近60分钟温度
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>
		<div class="box-content">
			<div id="jqxChartTemperature" style="height: 300px; position: relative; left: 0px; top: 0px;">
            </div>
		</div>
	</div>
	<div class="box span6">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 近60分钟湿度
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>
		<div class="box-content">
			<div id="jqxChartHumidity" style="height: 300px; position: relative; left: 0px; top: 0px;">
            </div>
		</div>
	</div>
	<div class="row-fluid">
		自动刷新 <div id="jqxProgressBar" style="display:inline-block;margin-bottom: -5px;"></div>
	</div>	
</div>
