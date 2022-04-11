function getGrafico(tipo)
{
	var chart;
	if (tipo == 5)
		chart = getTorta();
	else if (tipo == 10)
		chart = getTorta3D();
	else if (tipo == 9)
		chart = getCascada();
	else if (tipo == 11)
	{
		chart = getBarra3D();
		
		function showValues() 
		{
	        $('#R0-value').html(chart.options.chart.options3d.alpha);
	        $('#R1-value').html(chart.options.chart.options3d.beta);
	    }

	    // Activate the sliders
	    $('#R0').on('change', function () 
		{
	        chart.options.chart.options3d.alpha = this.value;
	        showValues();
	        chart.redraw(false);
	    });
	    $('#R1').on('change', function () 
		{
	        chart.options.chart.options3d.beta = this.value;
	        showValues();
	        chart.redraw(false);
	    });

	    showValues();						
	}
	else if (tipo == 12)
	{
		chart = getCascada3D();
		
		function showValues() 
		{
	        $('#R0-value').html(chart.options.chart.options3d.alpha);
	        $('#R1-value').html(chart.options.chart.options3d.beta);
	    }

	    // Activate the sliders
	    $('#R0').on('change', function () 
		{
	        chart.options.chart.options3d.alpha = this.value;
	        showValues();
	        chart.redraw(false);
	    });
	    $('#R1').on('change', function () 
		{
	        chart.options.chart.options3d.beta = this.value;
	        showValues();
	        chart.redraw(false);
	    });

	    showValues();						
	}
	else if (tipo == 14)
		chart = getGauge();
	else if (tipo == 8)
		chart = getCombinado();
	else if (tipo == 6)
		chart = getPareto();
	else
		chart = getDefault();

	if (tipo != 5 && tipo != 10 && tipo != 14 && typeof(_ajustarEscala) != "undefined" && !_ajustarEscala)
		if (typeof(chart) != "undefined" && typeof(chart.yAxis) != "undefined")
			chart.yAxis[0].setExtremes(0,null);
}

function getTorta()
{
	if (typeof(_showLeyenda) == "undefined")
		_showLeyenda = false;

	if (!_showLeyenda)
	{
		return new Highcharts.Chart({
			chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,								
				renderTo: _containerGraphic,
				type: _typeGraphic
			},
			title: {
				text: _name,
				style: 
				{
					fontSize: _fontSizeGraphTitulo,
					fontFamily: 'Verdana, sans-serif',
					fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
				}
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						format: '<b>{point.name}</b>: {point.percentage:.1f} %',
						style: {
							color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					}
				}
			},							
			legend: {
				align: 'center'
			},
			credits: 
			{
				enabled: false
			},
			series: _serie
		});
	}
	else
	{
		return new Highcharts.Chart({
			chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,								
				renderTo: _containerGraphic,
				type: _typeGraphic
			},
			title: {
				text: _name,
				style: 
				{
					fontSize: _fontSizeGraphTitulo,
					fontFamily: 'Verdana, sans-serif',
					fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
				}
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false,
					},
                    showInLegend: true
				}
			},							
			legend: {
				align: 'center'
			},
			credits: 
			{
				enabled: false
			},
			series: _serie
		});
	}
}

function getTorta3D()
{
	if (typeof(_showLeyenda) == "undefined")
		_showLeyenda = false;

	if (!_showLeyenda)
	{
		return new Highcharts.Chart({
			chart: {
				renderTo: _containerGraphic,
				type: _typeGraphic,
				options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }								
			},
			title: {
				text: _name,
				style: 
				{
					fontSize: _fontSizeGraphTitulo,
					fontFamily: 'Verdana, sans-serif',
					fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
				}
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
						format: '<b>{point.name}</b>: {point.percentage:.1f} %',
						style: {
							color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
	                }
	            }
	        },							
			credits: 
			{
				enabled: false
			},
			series: _serie
		});
	}
	else
	{
		return new Highcharts.Chart({
			chart: {
				renderTo: _containerGraphic,
				type: _typeGraphic,
				options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }								
			},
			title: {
				text: _name,
				style: 
				{
					fontSize: _fontSizeGraphTitulo,
					fontFamily: 'Verdana, sans-serif',
					fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
				}
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: false
	                },
	                showInLegend: true			
	            }
	        },							
			credits: 
			{
				enabled: false
			},
			series: _serie
		});
	}
}

function getCascada()
{
	return new Highcharts.Chart({
		chart: {
			renderTo: _containerGraphic,
			type: _typeGraphic
		},
		title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
		},
		xAxis: {
			categories: _ejeX,
			labels: 
			{
				rotation: -45,
				align: 'right',
				style: 
				{
					fontSize: _fontSizeGraph,
					fontFamily: 'Verdana, sans-serif'
				}
			}
		},
		yAxis: {
			title: {
				text: _ejeYTitle
			},
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}],
			labels: 
			{
				style: 
				{
					fontSize: _fontSizeGraph,
					fontFamily: 'Verdana, sans-serif'
				}
			}
		},
		tooltip: {
			formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					this.x +': '+ this.y + _ejeYTitle;
			}
		},
		plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },							
		legend: {
			align: 'center'
		},
		credits: 
		{
			enabled: false
		},
		series: _serie
	});
}

function getBarra3D()
{
	return new Highcharts.Chart({
		chart: {
			renderTo: _containerGraphic,
			type: _typeGraphic,
			margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }								
		},
		title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
		},
		subtitle: {
            text: _subtitle
        },
        yAxis: {
            title: {
                text: _ejeYTitle
            }
        },					        
        plotOptions: {
            column: {
                depth: 25
            }
        },					        
		tooltip: {
			formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					this.x +': '+ this.y + _ejeYTitle;
			}
		},
		legend: {
			align: 'center'
		},
		credits: 
		{
			enabled: false
		},
		series: _serie
	});
}

function getCascada3D()
{
	return new Highcharts.Chart({
		chart: {
			renderTo: _containerGraphic,
			type: _typeGraphic,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25,
                depth: 40
            },
            marginTop: 80,
            marginRight: 40								
		},
		title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
		},
		subtitle: {
            text: _subtitle
        },
        xAxis: {
            categories: _ejeX
        },
        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: _ejeYTitle
            }
        },					        
        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                depth: 40
            }
        },					        
        legend: {
			align: 'center'
		},
		credits: 
		{
			enabled: false
		},
		series: _serie
	});
}

function getDefault()
{
	return new Highcharts.Chart({
		chart: {
			renderTo: _containerGraphic,
			type: _typeGraphic
		},
		title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
		},
		xAxis: {
			categories: _ejeX,
			labels: 
			{
				rotation: -45,
				align: 'right',
				style: 
				{
					fontSize: _fontSizeGraph,
					fontFamily: 'Verdana, sans-serif'
				}
			}
		},
		yAxis: {
			title: {
				text: _ejeYTitle
			},
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}],
			labels: 
			{
				style: 
				{
					fontSize: _fontSizeGraph,
					fontFamily: 'Verdana, sans-serif'
				}
			}
		},
		tooltip: {
			formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					this.x +': '+ formatNumber(this.y) + ' ' + _ejeYTitle;
			}
		},
		legend: {
			align: 'center',
			itemStyle: {
                fontSize: (typeof(_fontSizeLegend) != "undefined" ? _fontSizeLegend : '12px')
            }		
		},
		credits: 
		{
			enabled: false
		},
		exporting:
		{
			  enabled:true
		},		
		series: _serie
	});
}

function getGauge()
{
	return $('#'+_containerGraphic).highcharts({
		chart: {
			renderTo: _containerGraphic,
			type: _typeGraphic,
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false
		},
		title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
        },		
		pane: {
            startAngle: -150,
            endAngle: 150,
            background: [{
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#FFF'],
                        [1, '#333']
                    ]
                },
                borderWidth: 0,
                outerRadius: '109%'
            }, {
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#333'],
                        [1, '#FFF']
                    ]
                },
                borderWidth: 1,
                outerRadius: '107%'
            }, {
                // default background
            }, {
                backgroundColor: '#DDD',
                borderWidth: 0,
                outerRadius: '105%',
                innerRadius: '103%'
            }]
        },
        yAxis: {
            min: 0,
            max: (!isNaN(parseFloat(_meta)) ? parseFloat(_meta) : 200),

            minorTickInterval: 'auto',
            minorTickWidth: 1,
            minorTickLength: 10,
            minorTickPosition: 'inside',
            minorTickColor: '#666',

            tickPixelInterval: 30,
            tickWidth: 2,
            tickPosition: 'inside',
            tickLength: 10,
            tickColor: '#666',
            labels: {
                step: 2,
                rotation: 'auto'
            },
            title: {
                text: _ejeYTitle
            },
            plotBands: _plotBands
        },
		credits: 
		{
			enabled: false
		},
        series: _serie
	});
}

function getCombinado()
{
	return $('#'+_containerGraphic).highcharts({
        title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
        },
        yAxis: {
            title: {
                text: _ejeYTitle
            }
        },					        
        xAxis: {
            categories: _ejeX
        },
		credits: 
		{
			enabled: false
		},
        series: _serie
    });
}

function getPareto()
{
	return $('#'+_containerGraphic).highcharts({
		chart: {
            zoomType: 'xy'
        },
        title: {
			text: _name,
			style: 
			{
				fontSize: _fontSizeGraphTitulo,
				fontFamily: 'Verdana, sans-serif',
				fontWeight: (typeof(_negrilla) != "undefined" && _negrilla ? 'bold' : 'normal')
			}
        },
        xAxis: {
            categories: _ejeX,
            crosshair: true
        },
        yAxis: _yAxis,
        tooltip: {
            shared: true
        },
        legend: {
			align: 'center'
		},
		credits: 
		{
			enabled: false
		},
        series: _serie
    });
}

function formatNumber(number)
{
	return numeral(number).format('0,0.00');
}