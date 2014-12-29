<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<c:url value="/css" var="cssPath" />
<c:url value="/js" var="jsPath" />
<link rel="stylesheet" href="${cssPath}/jqx.base.css" type="text/css" />
<script type="text/javascript" src="${jsPath}/jqxcore.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdraw.js"></script>
<script type="text/javascript" src="${jsPath}/jqxchart.core.js"></script>
<script type="text/javascript" src="${jsPath}/jqxchart.rangeselector.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdata.js"></script>
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
				<i class="icon-tag"></i> 群组每日报表
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>	
		<div class="box-content">
			<s:form action="list.do" method="post" id="searchForm">
			<div class="row-fluid">			
				<div class="span1">选择设备群组</div>
				<div class="span2">
					<div class="span2">
					<s:select id="paraGroupId" name="paraGroupId" list="groupMap" listKey="key" listValue="value.groupName" headerKey="-1" headerValue="无群组设备" theme="simple"/>						
				</div>	
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
	            { name: 'target_temperature', type: 'number' },
	            { name: 'max_temperature', type: 'number' },
	            { name: 'min_temperature', type: 'number' },
	            { name: 'humidity', type: 'number' },
	            { name: 'target_humidity', type: 'number' },
	            { name: 'max_humidity', type: 'number' },
	            { name: 'min_humidity', type: 'number' }            
            ],
        url: 'jsonData.do?paraGroupId=${paraGroupId}'
    };
    var dataAdapter = new $.jqx.dataAdapter(source, { async: false, autoBind: true, 
    	loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); },
    	loadComplete: function (records) {
    		if(records.length == 0) {
    			<s:if test="paraGroupId != null">
    			alert('無顯示數據');
    			</s:if>
    		}
		}
    });
    var toolTipT = function (value, itemIndex, serie, group, categoryValue, categoryAxis) {
        var dataItem = dataAdapter.records[itemIndex];
        return '<DIV style="text-align:left"><b>记录时间:</b> ' + $.jqx.dataFormat.formatdate(categoryValue, "yyyy-MM-dd", 'en-us') +
                '<br /><b>平均温度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.temperature, 'd2') + 
                ' °C<br /><b>目标温度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.target_temperature, 'd2') +
                ' °C<br /><b>最高温度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.max_temperature, 'd2') +
                ' °C<br /><b>最低温度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.min_temperature, 'd2') +
                ' °C </DIV>';
    };
    var toolTipH = function (value, itemIndex, serie, group, categoryValue, categoryAxis) {
        var dataItem = dataAdapter.records[itemIndex];
        return '<DIV style="text-align:left"><b>记录时间:</b> ' + $.jqx.dataFormat.formatdate(categoryValue, "yyyy-MM-dd", 'en-us') +
                '<br /><b>平均湿度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.humidity, 'd2')  +
                ' %<br /><b>目标湿度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.target_humidity, 'd2') +
                ' %<br /><b>最高湿度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.max_humidity, 'd2') +
                ' %<br /><b>最低湿度:</b> ' + $.jqx.dataFormat.formatnumber(dataItem.min_humidity, 'd2') +
                ' % </DIV>';
    };
    // prepare jqxChart settings
    var recordDateStart = new Date(<fmt:formatDate value="${recordDateStart}" pattern="yyyy"/>, <fmt:formatDate value="${recordDateStart}" pattern="MM"/>-1, <fmt:formatDate value="${recordDateStart}" pattern="dd"/>);    
    var recordDateEnd = new Date(<fmt:formatDate value="${recordDateEnd}" pattern="yyyy"/>, <fmt:formatDate value="${recordDateEnd}" pattern="MM"/>-1, <fmt:formatDate value="${recordDateEnd}" pattern="dd"/>);
    var rangeStart = new Date(recordDateEnd);
    rangeStart.setDate(rangeStart.getDate()-7);
    var settings = {
        title: "设备群组: ${groupObj.groupName}",
        description: " ",
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
                minValue: rangeStart,
                maxValue: recordDateEnd,
                formatFunction: function (value) {
                	return $.jqx.dataFormat.formatdate(value, "MM-dd", 'en-us');                	
                },
                type: 'date',
                baseUnit: 'day',
                textRotationAngle: -45,
        		textOffset: { x: -10, y: 0 },
                rangeSelector: {
                    padding: { /*left: 0, right: 0,*/top: 20, bottom: 0 },
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
                        description: '温度°C',
                        showGridLines: false
                    },
                    series: [
                        { dataField: 'temperature', displayText: '温度', lineWidth: 2, lineWidthSelected: 2, lineColor: '#F44C73', fillColor: '#F44C73' }                        
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
                        description: '湿度%',
                        showGridLines: false
                    },
                    series: [
                        { dataField: 'humidity', displayText: '湿度', lineWidth: 2, lineWidthSelected: 2, lineColor: '#4492DB', fillColor: '#4492DB' }
                    ]
                }
            ]
    };
    <s:if test="paraGroupId != null">
    $('#chartContainer').jqxChart(settings);
    </s:if>
});
</script>
<div class="row-fluid">
	<div id='chartContainer' style="width:800px; height:500px;">
	</div>	
</div>
