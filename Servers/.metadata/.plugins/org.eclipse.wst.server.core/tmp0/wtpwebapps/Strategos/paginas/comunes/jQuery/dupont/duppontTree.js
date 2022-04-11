function update(source) 
{
	// Compute the new tree layout.
	var tooltipDiv;
	var nodes = tree.nodes(root).reverse(), links = tree.links(nodes);

	// Normalize for fixed-depth.
	nodes.forEach(function(d) { d.y = d.depth * 180; });

	// Update the nodes…
	var node = svg.selectAll("g.node").data(nodes, function(d) { return d.id || (d.id = ++i); });

	// Enter any new nodes at the parent's previous position.
	var nodeEnter = node.enter().append("g")
		.attr("class", "node")
		.attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
		.on("click", function (d) 
		{ 
			if (typeof (tooltipDiv) != "undefined")
				tooltipDiv.remove();
			showGraph(d); 
		})
		.on("contextmenu", function (d) { click(d); });

	nodeEnter.append("image")
	.attr("xlink:href", function(d) {
		var icon = "";
		if (d.icon != "")
			icon = _alertaInalterada;
		return icon; 
	})
	.attr("x", "-8px")
	.attr("y", "-8px")
	.attr("width", "16px")
	.attr("height", "16px")	
	.attr("id", function(d) { return d.id; })
	.on("mouseover", function (d) 
			{ 
				// Clean up lost tooltips
				d3.select(_containerNodes).selectAll('div.tooltipHelper').remove();
				if (!_graphShowed)
				{
					// Append tooltip
					tooltipDiv = d3.select(_containerNodes).append('div').attr('class', 'tooltipHelper');
					tooltipDiv.style('left', (d3.event.pageX + 10)+'px')
						.style('top', (d3.event.pageY - 15)+'px')
						.style('position', 'absolute') 
						.style('z-index', 1001);
						
					// Add text using the accessor function
					var tooltipText = typeof (getTooltipText) == "function" ? getTooltipText(d) : d.name;
				}
			})
			.on('mousemove', function(d) 
			{
				if (!_graphShowed)
				{
					// Move tooltip
					if (typeof (tooltipDiv) == "undefined")
						tooltipDiv = d3.select(_containerNodes).append('div').attr('class', 'tooltipHelper')
					
					tooltipDiv.style('left', (d3.event.pageX + 10)+'px')
							.style('top', (d3.event.pageY - 15)+'px');
					
					var tooltipText = typeof (getTooltipText) == "function" ? getTooltipText(d) : d.name;
					tooltipDiv.html(tooltipText);
				}
			})
			.on("mouseout", function(d)
			{
				// Remove tooltip
				if (typeof (tooltipDiv) != "undefined")
					tooltipDiv.remove();
			});

	nodeEnter.filter(function(d) {
        				return (d.icon == "");
    		})
    		.append("circle")
			.attr("r", function(d) { return d.value; })
			.attr("id", function(d) { return d.id; })
			.style("stroke", function(d) { return d.type; })
			.style("fill", function(d) { return d.level; })
			.on("mouseover", function (d) 
			{ 
				// Clean up lost tooltips
				d3.select(_containerNodes).selectAll('div.tooltipHelper').remove();
				if (!_graphShowed)
				{
					// Append tooltip
					tooltipDiv = d3.select(_containerNodes).append('div').attr('class', 'tooltipHelper');
					tooltipDiv.style('left', (d3.event.pageX + 10)+'px')
						.style('top', (d3.event.pageY - 15)+'px')
						.style('position', 'absolute') 
						.style('z-index', 1001);
						
					// Add text using the accessor function
					var tooltipText = typeof (getTooltipText) == "function" ? getTooltipText(d) : d.name;
				}
			})
			.on('mousemove', function(d) 
			{
				if (!_graphShowed)
				{
					// Move tooltip
					if (typeof (tooltipDiv) == "undefined")
						tooltipDiv = d3.select(_containerNodes).append('div').attr('class', 'tooltipHelper')
					
					tooltipDiv.style('left', (d3.event.pageX + 10)+'px')
							.style('top', (d3.event.pageY - 15)+'px');
					
					var tooltipText = typeof (getTooltipText) == "function" ? getTooltipText(d) : d.name;
					tooltipDiv.html(tooltipText);
				}
			})
			.on("mouseout", function(d)
			{
				// Remove tooltip
				if (typeof (tooltipDiv) != "undefined")
					tooltipDiv.remove();
			});
	
	// Original
	//nodeEnter.append("circle")
	//		.attr("r", 1e-6)
	//		.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

	nodeEnter.append("text")
			.attr("class", "node")
			.attr("x", function(d) { return d.children || d._children ? -13 : 13; })
			.attr("y", function (d) { return d.parent.py; })
			.attr("dy", ".20em")
			.attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
			.text(function(d) { return (d.name.length > 23 ? (d.name.substring(0, 23) + "...") : d.name); })
			.style("fill-opacity", 1e-6)
			//.style("font-size", (typeof (_fontSizeNode) == "undefined" ? "11px" : _fontSizeNode))
			.style("font-size", function(d) { return (typeof (_fontSizeNode) == "undefined" ? (Math.min(2 * d.r, (2 * d.r - 8) / this.getComputedTextLength() * 24) + "px") : (_fontSizeNode + "px")); });
			//.call(wrap, 110);
	  
	//node.on("mouseover", function (d) 
	//{
		//var g = d3.select(this); // The node
		//var div = d3.select("body").append("div")
			//.attr('id', 'tooltip-node')
			//.attr('pointer-events', 'none')
			//.attr("class", "tooltip")
			//.style("opacity", 1)
			//.html("<b>" + d.name + "</b><br> grafico <br> x = " + (event.clientX) + ", " + (event.clientX + 5) + "<br> y = " + (event.clientY + 5))
			//.style("left", ((event.clientX + 5) + "px"))
			//.style("top", ((event.clientY + 5) + "px"));
		//showGraph(d);
	//});

	//node.on("mouseout", function (d) 
	//{
		//d3.select("body").select('div.tooltip').remove();
		//hideGraph();
	//});				

	// Transition nodes to their new position.
	var nodeUpdate = node.transition().duration(duration).attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

	/*nodeEnter.append("image")
      .attr("xlink:href", "img/plus.gif")
      .attr("x", "-6px")
      .attr("y", "-6px")
      .attr("width", "12px")
      .attr("height", "12px");*/
	  
	nodeUpdate.select("circle")
			.attr("r", 8)	// Tamano del circulo
			.style("fill", function(d) {
											if (d._children)
											{
												var color = "lightsteelblue";
												if (d.alerta == "0")
													color = "#B22C2C"; // Rojo Recogido
												else if (d.alerta == "2")
													color = "#64926C"; // Verde Recogido
												else if (d.alerta == "3")
													color = "#b2ba23"; // Amarillo Recogido
												return color;
											}
											else
												return d.level; 
										}); // Color cuando recoge el nodo

	nodeUpdate.select("text")
			.style("fill-opacity", 1);

	// Transition exiting nodes to the parent's new position.
	var nodeExit = node.exit().transition()
			.duration(duration)
			.attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
			.remove();

	nodeExit.select("circle")
			.attr("r", 1e-6);

	nodeExit.select("text")
			.style("fill-opacity", 1e-6);

	// Update the links…
	var link = svg.selectAll("path.link").data(links, function(d) { return d.target.id; });

	// Enter any new links at the parent's previous position.
	link.enter().insert("path", "g")
			.attr("class", "link")
			.attr("d", function(d) 
			{
				var o = {x: source.x0, y: source.y0};
				return diagonal({source: o, target: o});
			});

	// Transition links to their new position.
	link.transition().duration(duration).attr("d", diagonal);

	// Transition exiting nodes to the parent's new position.
	link.exit().transition()
			.duration(duration)
			.attr("d", function(d) 
			{
				var o = {x: source.x, y: source.y};
				return diagonal({source: o, target: o});
			})
			.remove();

	// Stash the old positions for transition.
	nodes.forEach(function(d) 
	{
		d.x0 = d.x;
		d.y0 = d.y;
	});
	
	if (typeof (Indicador) == "function")
	{
		indicadores = new Array();
		nodes.forEach(function(d) 
		{
			var objeto = new Indicador();
			objeto.Id = d.indicadorId;
			objeto.Desplegado = d.desplegado == "true" ? true : false;
			
			indicadores.push(objeto);
		});
	};
}

function rightClick(d)
{
	alert('Right Click');
}

// Toggle children on click.
function click(d) 
{
	hideGraph();
	if (d.hadChildren == 'true' && d.desplegado == 'true')
	{
		if (d.children) 
		{
			d._children = d.children;
			d.children = null;
			d.nodeState = "C";
			//if ((typeof (validar) == "function") && validar())
			//{
			//	if (typeof (refrescar) == "function")
			//		refrescar(d.indicadorId, false);
			//}
		} 
		else 
		{
			d.children = d._children;
			d._children = null;
			d.nodeState = "E";
		}
	}
	else
	{
		if (d.hadChildren == 'true' && d.desplegado == 'false' && typeof (_nivel) != "undefined" && (typeof (validar) == "function") && (typeof (refrescar) == "function"))
		{
			if ((typeof (validar) == "function") && validar())
			{
				if (typeof (refrescar) == "function")
				{
					refrescar(d.indicadorId, true);
					return;
				}
			}
		}
		else
		{
			d.children = d._children;
			d._children = null;
			d.nodeState = "E";
		}
	}
  
	update(d);
}

function preparedGraph(node)
{
	_node = node;
	_ejeYTitle = _node.unidad;
	_ejeX = _node.ejeX.split(",");
	_serie = [];
	var series = _node.serieName.split(_separadorSeries);
	var datas = _node.serieData.split(_separadorSeries);
	for (var i = 0; i < series.length; i++)
	{
		_serie.push({
            name: series[i],
            data: []
        });
		var data = datas[i].split(",");
		for (var k = 0; k < data.length; k++)
		{
			if (!isNaN(parseFloat(data[k])))
				_serie[i].data.push(parseFloat(data[k]));
			else
				_serie[i].data.push(null);
		}
	}
	_showGraph = true;
	showGraph(_node);
}
	
function showGraph(node)
{
	if (node.nodeState == null || node.nodeState == "E")
	{
		if ((typeof (preparedGraph) == "function") && !_showGraph)
		{
			preparedGraph(node);
			_showGraph = false;
		}
		else
		{
			if (typeof (_fontSizeGraph) == "undefined")
				_fontSizeGraph = '8px';
			createGraph(node.name);
			_showGraph = true;
		}
		
		if (_showGraph)
		{
			var g = $('#tooltip-node');
			
			var objeto = document.getElementById('div-body');
			var posicionY = (event.clientY + 5);
			var posicionX = (event.clientX + 5);
			if (objeto != null)
			{
				var posicion = parseInt(objeto.style.height.replace("px", "")) - 310;
				if (posicion < posicionY)
					posicionY = posicion; 
				posicion = parseInt(objeto.style.width.replace("px", "")) - 375;
				if (posicion < posicionX)
					posicionX = posicion; 
			}

			g.css("left", posicionX);
			g.css("top", posicionY);
			g.show();
			_showGraph = false;
			_graphShowed = true;
		}
	}
}
		
function hideGraph()
{
	var objeto = document.getElementById('tooltip-node');
	if (objeto != null)
	{
		objeto.style.width = "375px";
		objeto.style.height = "310px";
	}
	
	objeto = document.getElementById('container-tooltip');
	if (objeto != null)
	{
		objeto.style.width = "370px";
		objeto.style.height = "300px";
	}
	objeto = document.getElementById('spanMaximize');
	if (objeto != null)
		objeto.style.display = "";
	_fontSizeGraph = '8px';
	
	$('#tooltip-node').hide();
	
	_graphShowed = false;
}

function maximize()
{
	$('#container-tooltip')
	
	var objeto = document.getElementById('tooltip-node');
	if (objeto != null)
	{
		var w = parseInt(_myWidth) - 50; 
		var h = parseInt(_myHeight) - 50;
		objeto.style.width = (parseInt(w)) + "px";
		objeto.style.height = (parseInt(h)) + "px";

		objeto = document.getElementById('container-tooltip');
		if (objeto != null)
		{
			objeto.style.width = (parseInt(w) - 5) + "px";
			objeto.style.height = (parseInt(h) - 10) + "px";
		}
		
		objeto = document.getElementById('spanMaximize');
		if (objeto != null)
			objeto.style.display = "none";
		
		_fontSizeGraph = '14px';
		createGraph(_node.name);

		var g = $('#tooltip-node');
		
		g.css("left", 20);
		g.css("top", 20);
		g.show();
	}
}

function wrap(text, width) 
{
    text.each(function () 
    {
        var text = d3.select(this),
            words = text.text().split(/\s+/).reverse(),
            word,
            line = [],
            lineNumber = 0,
            lineHeight = 1.1, // ems
            x = text.attr("x"),
            y = text.attr("y"),
            dy = parseFloat(text.attr("dy")),
            tspan = text.text(null)
                        .append("tspan")
                        .attr("x", x)
                        .attr("y", y)
                        .attr("dy", dy + "em");
        while (word = words.pop()) 
        {
            line.push(word);
            tspan.text(line.join(" "));
            if (tspan.node().getComputedTextLength() > width) 
            {
				line.pop();
				tspan.text(line.join(" "));
				line = [word];
				tspan = text.append("tspan")
							.attr("x", x)
							.attr("y", y)
							.attr("dy", ++lineNumber * lineHeight + dy + "em")
							.text(word);
            }
        }
    });
}
