var Nodes, Relationships;

var fileBufferCount = 0;


var nodeInfo = new Array();

var edgeNum = 0;
var nodeCount = 0;

var timer;
var  timerIsActive = false;

var ServerJSONBuffer = [];
var bufferCount = 0;

var FileJSONBuffer = [];

var finished=false;
var informationJSON;

var readingFromFile = 0;
var readingFromServer = 0;

function startTimer() 
{
	timer = setInterval(addNodesAndEdgesFile, 10);
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
		timer = setInterval(flashText, 10);
}

function getNodeFromServer() 
{
	var xhttp = new XMLHttpRequest();
	
	if(finished)
	{
		alert("Scan finnished");
		return;
	}
 		
	xhttp.onreadystatechange = function() 
	{
		 if(finished)
		{
			alert("Scan finnished");
			return;
		}
	  
		if (this.readyState == 4 && this.status == 200) 
		{
			if(this.responseText=="null")
			{	
				alert("Scan finnished");
				finished=true;
			}
			
			if(!finished)    
			{
	
				var jsonIn = this.responseText;
				readInJSONFromServer(jsonIn);
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
	if(ServerJSONBuffer.length > bufferCount)
	{
		for(var k = 0; k< ServerJSONBuffer[bufferCount].NodesArray.length; k++)
		{
			addNode(ServerJSONBuffer[bufferCount].NodesArray[k].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Name, ServerJSONBuffer[bufferCount].NodesArray[k].Level);
			
			if(ServerJSONBuffer[bufferCount].NodesArray[k].Relationships.length != 0)
			{
				for(j = 0; j < ServerJSONBuffer[bufferCount].NodesArray[k].Relationships.length; j++)
				{
					
					addEdge(edgeNum, ServerJSONBuffer[bufferCount].NodesArray[k].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Relationships[j].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Relationships[j].type, edgeNum, ServerJSONBuffer[bufferCount].NodesArray[k].Level);
					edgeNum = edgeNum + 1;
				}
			}
		}
		
				
					bufferCount=bufferCount+1;
	}
}


function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}


function addNodesAndEdgesFile() 
{
	
	if(FileJSONBuffer.length > fileBufferCount)
	{
		addNode(FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Name, FileJSONBuffer[fileBufferCount].Level);
			
		if(FileJSONBuffer[fileBufferCount].Relationships.length != 0)
		{
			for(j = 0; j < FileJSONBuffer[fileBufferCount].Relationships.length; j++)
			{
				addEdge(edgeNum, FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Relationships[j].UUID, FileJSONBuffer[fileBufferCount].Relationships[j].type, FileJSONBuffer[fileBufferCount].Level);
				edgeNum = edgeNum + 1;
			}
		}


		fileBufferCount = fileBufferCount + 1;
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
				color: 
				{//#ff8c1a
					background:'#ff8c1a', 
					border:'black', 
					highlight:
					{
						background:'#ff8c1a',
						border: '#D35A1A'
					},
					hover: 
					{
						background:'#ff8c1a',
						border: '#D35A1A'
					}
				}
			});
			break;
			case "2":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				color: 
				{
					background:'#00ccff', 
					border:'black', 
					highlight:
					{
						background:'#00ccff',
						border: '#0C11A8'
					},
					hover: 
					{
						background:'#00ccff',
						border: '#0C11A8'
					}
				}
			});
			break;
			case "3":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				color: 
				{
					background:'#66ff33', 
					border:'black', 
					highlight:
					{
						background:'#66ff33',
						border: '#0C943D'
					},
					hover: 
					{
						background:'#66ff33',
						border: '#0C943D'
					}
				}				
			});
			break;
			case "4":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				color: 
				{
					background:'#FF3C28', 
					border:'black', 
					highlight:
					{
						background:'#FF3C28',
						border: '#990000 '
					},
					hover: 
					{
						background:'#FF3C28',
						border: '#990000'
					}
				}				
			});
			break;
			case "5":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				color: 
				{
					background:'#F9EE45', 
					border:'black', 
					highlight:
					{
						background:'#F9EE45',
						border: '#D5A611'
					},
					hover: 
					{
						background:'#F9EE45',
						border: '#D5A611'
					}
				}				
			});
			break;
			default:
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				shape: 'dot',  
				color: {background:'white', border:'black',highlight:{background:'white',border:'black'}}
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

function addEdge(idIn, fromIn, toIn, type, level)
{
		try
	{
		switch (level) 
		{
			case "1":
			Relationships.add(
			{
				id: idIn,
				from: fromIn,
				to: toIn,
				dashes: false,
				color: 
				{
					color:'black',
					highlight:'#D35A1A',
					hover: '#D35A1A'
				},

			});
			break;
			case "2":
			Relationships.add(
			{
				id: idIn,
				from: fromIn,
				to: toIn,
				dashes: false,
				//label: "lable",
				color: 
				{
					color:'black',
					highlight:'#0C11A8',
					hover: '#0C11A8'
				},
			});
			break;
			case "3":
			Relationships.add(
			{
				id: idIn,
				from: fromIn,
				to: toIn,
				dashes: false,
				//label: "lable",
				color: 
				{
					color:'black',
					highlight:'#0C943D',
					hover: '#0C943D'
				},
			});
			break;
			case "4":
			Relationships.add(
			{
				id: idIn,
				from: fromIn,
				to: toIn,
				dashes: false,
				//label: "lable",
				color: 
				{
					color:'black',
					highlight:'#990000',
					hover: '#990000'
				},
			});
			break;
			default:
			Relationships.add(
			{
				id: idIn,
				from: fromIn,
				to: toIn,
				color: 'black'
			});
		}
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
		readInJSONFromFile(text);
	};
	
	reader.readAsText(input.files[0]);
};

function readInJSONFromServer(jsonIn)
{
	var obj = JSON.parse(jsonIn);

	ServerJSONBuffer.push(obj);
}

function readInJSONFromFile(jsonIn)
{
	var obj = JSON.parse(jsonIn);
	var obj2;
	
	for(var k = 0; k< obj.NodesArray.length; k++)
	{
		obj2 = obj.NodesArray[k];
		FileJSONBuffer.push(obj2);
	}
	//readingFromFile = 1;
	//addNodesAndEdgesFile() ;
	startTimer();
}

function clearNodesAndEdges()
{
	var removedIds = Nodes.clear();
	removedIds = Relationships.clear();
	ServerJSONBuffer = [];
	FileJSONBuffer = [];
	fileBufferCount = 0;
	bufferCount = 0;
	
	draw();

}

function getBufferContents()
{
	return ServerJSONBuffer;
}

function draw()
{
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
			shape: 'dot', 
			borderWidth:3,
			size:40,
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
					background:'white',
					border: 'black '
				},
				hover: 
				{
					border: 'black'
				}

			},
			font: 
			{
				size:15, color:'white', face:'courier', strokeWidth:3, strokeColor:'black'
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
				levelSeparation: 200,
				nodeSpacing: 200,
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
					document.getElementById("information").value =this.responseText ;
					document.getElementById("fromUUID").value =ids ;
				}
				
			}
			
			
		};
		xhttp.open("GET", "http://localhost:8080/NotLikeThisRESTServer_war_exploded/services/getInformation?uuid="+ids, true);
		xhttp.send();

	});
}