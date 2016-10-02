var Nodes, Relationships;

var nodeInfo = new Array();

var edgeNum = 0;
var nodeCount = 0;

var timer;
var  timerIsActive = false;

var JSONBuffer = [];
var bufferCount = 0;

var finished=false;
var informationJSON;

function startTimer() 
{
	timer = setInterval(addNodesAndEdges, 1000);
	
	timerIsActive = true;
}

function stopTimer()
{
	clearInterval(timer);
	timerIsActive = false;
}
			
function resumeTimer()
{
	if(!timerIsActive)
		timer = setInterval(flashText, 1000);
}

function getNodeFromServer() 
{
	var xhttp = new XMLHttpRequest();
	
	if(finished)
	{
		return;
	}
 		
	xhttp.onreadystatechange = function() 
	{
		 if(finished)
		{
			return;
		}
	  
		if (this.readyState == 4 && this.status == 200) 
		{
			if(this.responseText=="null")
			{	alert("Scan finished");
				finished=true;
			}
			
			if(!finished)    
			{
	
				var jsonIn = this.responseText;
				readInJSON(jsonIn);
				addNodesAndEdges() ;
				getNodeFromServer();
			}
		}
	}

	xhttp.open("GET", "http://localhost:8080/NotLikeThisRESTServer_war_exploded/services/getLatestTree", true);
	xhttp.send();
}
	

function addNodesAndEdges() 
{
	if(JSONBuffer.length > bufferCount)
	{
		for(var k = 0; k< JSONBuffer[bufferCount].NodesArray.length; k++)
		{
			addNode(JSONBuffer[bufferCount].NodesArray[k].UUID, JSONBuffer[bufferCount].NodesArray[k].Name, JSONBuffer[bufferCount].NodesArray[k].Level);
			
			if(JSONBuffer[bufferCount].NodesArray[k].Relationships.length != 0)
			{
				for(j = 0; j < JSONBuffer[bufferCount].NodesArray[k].Relationships.length; j++)
				{
					addEdge(edgeNum, JSONBuffer[bufferCount].NodesArray[k].UUID, JSONBuffer[bufferCount].NodesArray[k].Relationships[j].UUID);
					edgeNum = edgeNum + 1;
				}
			}
		}
		
		bufferCount=bufferCount+1;
	}
}

function addNode(idIn, labelIn, levelIn)
{
	try
	{
		switch (levelIn) 
		{
			case "1":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/root.png'
			});
			break;
			case "2":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/region.png'					
			});
			break;
			case "3":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/VPC.png'						
			});
			break;
			case "4":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/subnetwork.png'						
			});
			break;
			case "5":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/Instance.png'					
			});
			break;
			default:
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				shape: 'dot',  
				color: {background:'white', border:'black',highlight:{background:'white',border:'black'}},
				font: {background: 'white'}						
			});
		}
	}
	catch (err) 
	{
		alert(err);
	}
}

function updateNode(idIn, labelIn)
{
	try 
	{
		Nodes.update(
		{
			id: idIn,
			label: labelIn
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function removeNode(idIn)
{
	try
	{
		Nodes.remove({id: idIn});
	}
	catch (err)
	{
		alert(err);
	}
}

function addEdge(idIn, fromIn, toIn)
{
	try
	{
		Relationships.add(
		{
			id: idIn,
			from: fromIn,
			to: toIn,
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function updateEdge(idIn, fromIn, inTo)
{
	try
	{
		Relationships.update(
		{
			id: idIn,
			from: fromIn,
			to: inTo
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function removeEdge(idIn)
{
	try
	{
		Relationships.remove({id: idIn});
	}
	catch (err)
	{
		alert(err);
	}
}




var openFile = function (event)
{
	var input = event.target;
	var reader = new FileReader();
	
	reader.onload = function ()
	{
		var text = reader.result;
		console.log(reader.result.substring(0, 200));
		readInJSON(text);
	};
	
	reader.readAsText(input.files[0]);
};

function readInJSON(jsonIn)
{
	var obj = JSON.parse(jsonIn);

	JSONBuffer.push(obj);
}

function clearNodesAndEdges()
{
	var removedIds = Nodes.clear();
	removedIds = Relationships.clear();
	JSONBuffer = [];
	draw();
}

function draw()
{
	alert("redrawing");
	Nodes = new vis.DataSet();

	Relationships = new vis.DataSet();

	var options =
        {
		interaction: 
		{
			navigationButtons: true, 
			keyboard: true, hover: true, 
			hideEdgesOnDrag: true
		},
                nodes: 
		{
			shape: 'circularImage', 
			borderWidth:1, size:40,
			shapeProperties: 
			{ 
				useBorderWithImage:true
			},
			color: 
			{
				background:'white', 
				border:'black', 
				highlight:
				{
					background:' #3498db ',
					border:' #black '
				}
			},
			font: 
			{
				background: 'white', 
				size: 14
			}	
		
		},
                edges: 
		{
			width: 2,
			smooth: 
			{
				type:"dynamic",
				forceDirection: "none",
				roundness: 0.0
			}
		},
		physics: 
		{
			enabled: false
		},
		layout: 
		{
			improvedLayout:true,
			hierarchical: 
			{
				enabled:true,
				levelSeparation: 500,
				nodeSpacing: 300,
				//treeSpacing: 300,
				blockShifting: true,
				edgeMinimization: true,
				parentCentralization: true,
				direction: 'UD',        // UD, DU, LR, RL
				sortMethod: 'directed'   // hubsize, directed
			}
		}
	};

	var data =
	{
                nodes: Nodes,
                edges: Relationships
	};

	var container = document.getElementById("hierarchyVisualizerDiv");
	
	networkHierarchy = new vis.Network(container, data, options);
	
	networkHierarchy.on( 'selectNode', function(properties) 
	{
		var ids = properties.nodes;
		var xhttp = new XMLHttpRequest();
	

 		
		xhttp.onreadystatechange = function() 
		{
			
		  
			if (this.readyState == 4 && this.status == 200) 
			{
				if(this.responseText!="null")
				{	
					document.getElementById("information").innerHTML =this.responseText ;
					document.getElementById("fromUUID").value =ids ;
				}
				
			}
			
			
		};
		xhttp.open("GET", "http://localhost:8080/NotLikeThisRESTServer_war_exploded/services/getInformation?uuid="+ids, true);
		xhttp.send();

	});
}