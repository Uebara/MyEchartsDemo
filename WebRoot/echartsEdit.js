$(function() {
	findDimensions();
	showChart();
	clickEvents();
	window.onresize = resizeAction;
});

function findDimensions() {
	var maxWidth = 0;
	var maxHeight = 0;
	// 获取窗口宽度
	if (window.innerWidth)
		maxWidth = window.innerWidth;
	else if ((document.body) && (document.body.clientWidth))
		maxWidth = document.body.clientWidth;
	// 获取窗口高度
	if (window.innerHeight)
		maxHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		maxHeight = document.body.clientHeight;
	// 通过深入Document内部对body进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight
			&& document.documentElement.clientWidth) {
		maxHeight = document.documentElement.clientHeight;
		maxWidth = document.documentElement.clientWidth;
	}

	$("body").css('height', maxHeight);
	$("body").css('width', maxWidth);
}

function resizeAction() {
	findDimensions();
	myChart.resize();
}

var myChart;// = echarts.init(document.getElementById('main'));

option = {
	title : {
		text : '示例'
	},
	animationDurationUpdate : 1500,
	animationEasingUpdate : 'quinticInOut',
	tooltip : {
		trigger : 'item',
		triggerOn : 'mousemove',
		formatter : function(params, ticket, callback) {
			if (params.data.attribute) {
				return params.data.attribute;
			} else {
				return params.name;
			}
		}
	},
	series : [ {
		type : 'graph',
		layout : 'force',
		data : [],
		edges : [],
		label : {
			emphasis : {
				position : 'right',
				show : true
			}
		},
		force : {
			repulsion : 1000
		},
		roam : true,
		focusNodeAdjacency : true,
		lineStyle : {
			normal : {
				width : 0.5,
				curveness : 0.3,
				opacity : 0.7
			}
		},
		draggable : true
	} ]
};

function showChart() {
	myChart = echarts.init(document.getElementById('main'));
	myChart.showLoading();
	$.ajax({
		url : 'echartsDisplay',
		type : 'POST',
		data : "{}",
		dataType : 'json',
		success : function(data) {
			myChart.hideLoading();
			option.series[0].data = data.nodes.map(function(node) {
				return {
					name : node.name,
					itemStyle : {
						normal : {
							color : node.color
						}
					},
					symbolSize : node.size
				};
			});
			option.series[0].edges = data.links.map(function(edge) {
				return {
					source : edge.source,
					target : edge.target,
					attribute : edge.value,
				};
			});
			myChart.setOption(option, true);
		},
		error : function(errorMsg) {
			alert("不好意思，大爷，图表请求数据失败啦!");
		}
	});
};

function addNodes() {
	$.ajax({
		type : 'POST',
		url : 'echartsInsertNodes',
		data : GetNodeData(),
		dataType : 'json',
		success : function(data) {
			var oneNode = {
				name : data.name,
				itemStyle : {
					normal : {
						color : data.color
					}
				},
				symbolSize : data.size,

			};
			option.series[0].data.push(oneNode);
			myChart.setOption(option);
		},
		error : function(errorMsg) {
			alert("节点提交失败!八成是输入了重复的节点名称");
		}
	});
};

function GetNodeData() {
	var json = {
		"name" : $('#NodeName').val(),
		"color" : $('#NodeColor option:selected').val(),
		"size" : $('#NodeSize').val()
	};
	return json;
}

function addLinks() {

	$.ajax({
		url : 'echartsInsertLinks',
		type : 'POST',
		data : GetLinkData(),
		dataType : 'json',
		success : function(data) {
			var oneLink = {
				source : data.source,
				target : data.target,
				attribute : data.value,
			};
			option.series[0].edges.push(oneLink);
			myChart.setOption(option);
		},
		error : function(errorMsg) {
			alert("关系提交失败!八成是输入了错误的节点名称");
		}
	});
};

function GetLinkData() {
	var json = {
		"attributes" : $('#attributes').val(),
		"target" : $('#target').val(),
		"source" : $('#source').val()
	};
	return json;
}

function clickEvents() {
	$(".addNode").bind('click', function(event) {
		addNodes();
	});
	$(".addLink").bind('click', function(event) {
		addLinks();
	});
};
