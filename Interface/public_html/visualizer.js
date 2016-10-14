/*Containers used by vis.js to store nodes*/
var Nodes;

/*Containers used by vis.js to store relationships*/
var Relationships;


var SecurityGroupNodes;
var SecurityGroupRelationships;

var SecurityGroupEdgeCount = 0


/*
An integer that keeps track of the current index of the .json being  processed.
This buffer is dedicated to reading from the server sent .json.
*/
var bufferCount = 0;

/*
An integer that keeps track of the current index of the .json being  processed.
This buffer is dedicated to reading from the local file .json.
*/
var fileBufferCount = 0;

/*
An array that stores objects from the .json
This buffer is dedicated to reading from the server sent .json.
*/
var ServerJSONBuffer = [];

/*
An array that stores objects from the .json
This buffer is dedicated to reading from the local file .json.
*/
var FileJSONBuffer = [];

/*
An integer that keeps track of the amount of nodes.
edgeNum is also used as the id for relationships in the Relationships container.
*/
var edgeNum = 0;

/*
An interval variable that determines the rate at which nodes are added to the visualiser,
when read from a local file.
*/
var timer;

var scanFinished=false;
var scanPaused = false;

var informationJSON;

var readingFromFile = 0;
var readingFromServer = 0;

var atSecurityGroups = false;

/*Starts the timer*/
function startTimer() 
{
	timer = setInterval(addNodesAndEdgesFile, 10);
	timerIsActive = true;
}

/*Stops the timer*/
function stopTimer()
{
	clearInterval(timer);
	timerIsActive = false;
}

/*Reads in nodes from the server's .json objects passed to the browser*/
function getNodeFromServer() 
{
	var xhttp = new XMLHttpRequest();
	
	if(scanFinished)
	{
		alert("Scan Finished");
		stopScan();
		return;
	}
 		
	xhttp.onreadystatechange = function() 
	{
		 if(scanFinished)
		{
			alert("Scan Finished");
			document.getElementById("scanNetworkButton").innerHTML = "Scan Network";
			return;
		}
	  
		if (this.readyState == 4 && this.status == 200) 
		{
			if(this.responseText=="null")
			{	
				alert("Scan Finished");
				document.getElementById("scanNetworkButton").innerHTML = "Scan Network";
				return;
			}
			
			if(!scanFinished && !scanPaused)    
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
	
/*The .json objects from the server are processed and their information is added to the visualiser*/
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


/*The .json objects from the file are processed and their information is added to the visualiser.*/
function addNodesAndEdgesFile() 
{
	if(FileJSONBuffer.length > fileBufferCount)
	{
		if(FileJSONBuffer[fileBufferCount].UUID == "LOADING_SECURITY_GROUPS" && FileJSONBuffer[fileBufferCount].Name == "LOADING_SECURITY_GROUPS")
		{
			atSecurityGroups = true;
		}
		
		if(!atSecurityGroups)
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
		}
		else
		{
			addSecurityGroupNode(FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Name);
			
			if(FileJSONBuffer[fileBufferCount].Relationships.length != 0)
			{
				for(j = 0; j < FileJSONBuffer[fileBufferCount].Relationships.length; j++)
				{
					addSecurityGroupEdge(SecurityGroupEdgeCount, FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Relationships[j].UUID);
					
					SecurityGroupEdgeCount = SecurityGroupEdgeCount + 1;
				}
			}
		}
		fileBufferCount = fileBufferCount + 1;
	}
}

/*Adds nodes to the visualiser. They are formatted according to the 'levelIn' variable*/
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
				{
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

/*Adds efges to the visualiser. They are formatted according to the 'level' variable*/
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


function addSecurityGroupNode(idIn, labelIn)
{
	try
	{
		SecurityGroupNodes.add(
			{
				id: idIn,
				label: labelIn,
				//level: levelIn,
				color: 
				{
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
		}
		catch (err) 
		{
			alert(err);
		}
}

function addSecurityGroupEdge(idIn, fromIn, toIn)
{
	try
	{
		SecurityGroupRelationships.add(
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
		
	}
	catch (err) 
	{
		alert(err);
	}
}


/*
The update node function as specified in vis.js tutorials.
Currently, is is not in use, but is useful as a reference, should it become needed.
*/
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

/*
The remove node function as specified in vis.js tutorials.
Currently, is is not in use, but is useful as a reference, should it become needed.
*/
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

/*
The update edge function as specified in vis.js tutorials.
Currently, is is not in use, but is useful as a reference, should it become needed.
*/
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

/*
The remove edge function as specified in vis.js tutorials.
Currently, is is not in use, but is useful as a reference, should it become needed.
*/
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

/*
This function is made possible using the FileSaver.js.
It opens a standard window, so that the user may choose which file to read from.
*/
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

/*Takes in the json object from the server and adds it to the buffer..*/
function readInJSONFromServer(jsonIn)
{
	var obj = JSON.parse(jsonIn);
	ServerJSONBuffer.push(obj);
}

/*Takes in the json object from the file and adds it to the buffer.*/
function readInJSONFromFile(jsonIn)
{
	var obj = JSON.parse(jsonIn);
	var obj2;
	
	clearNodesAndEdges();
	
	for(var k = 0; k< obj.NodesArray.length; k++)
	{
		obj2 = obj.NodesArray[k];
		FileJSONBuffer.push(obj2);
	}

	startTimer();
}

/*Cleans out the entire visualisation*/
function clearNodesAndEdges()
{
	var removedIds = Nodes.clear();
	removedIds = Relationships.clear();
	removedIds =  SecurityGroupNodes.clear();
	removedIds = SecurityGroupRelationships.clear();
	ServerJSONBuffer = [];
	FileJSONBuffer = [];
	fileBufferCount = 0;
	bufferCount = 0;
	scanFinished=false;
	scanPaused = false;
	SecurityGroupEdgeCount = 0
	
	atSecurityGroups = false;
	
	draw();

}

/*Returns the contents of the buffer*/
function getBufferContents()
{
	return ServerJSONBuffer;
}


//function load







/*Initiates the visualisation upon startup.*/
function draw()
{
	Nodes = new vis.DataSet();
	Relationships = new vis.DataSet();
	SecurityGroupNodes = new vis.DataSet();
	SecurityGroupRelationships = new vis.DataSet();

	var options =
        {
		interaction: 
		{
			navigationButtons: true, 
			keyboard: true, 
			hover: true, 
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
				type:"continuous",
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
		
		
		
		document.getElementById("hierarchyVisualizerDiv").style.visibility='hidden';
		document.getElementById("securityGroupDiv").style.visibility='visible';
		document.getElementById("closeSecurityImg").style.visibility='visible';

		drawSecurity(ids);
	});	
}

function getSecurityGroupNode(id)
{
	for(var i = 1; i < SecurityGroupNodes.length; i++)
	{
		if(SecurityGroupNodes.get(i).id == id)
		{
			return SecurityGroupNodes.get(i);
		}
	}
	
	return -1;
}

function getSecurityGroupRelationships(id)
{
	var cur = new vis.DataSet();
	
	for(var i = 0; i < SecurityGroupRelationships.length; i++)
	{
		if(SecurityGroupRelationships.get(i).from == id)
		{
			cur.add(SecurityGroupRelationships.get(i));
			SecurityGroupNodes.add(getSecurityGroupNode(SecurityGroupRelationships.get(i).to));
		}
	}
	
	return cur;
}




var temp1;
var temp2;

function getsus(id)
{
	temp1 = new vis.DataSet();
 temp2 = new vis.DataSet();
	var root = false;
	
	for(var i = 0; i < SecurityGroupRelationships.length; i++)
	{
		if(SecurityGroupRelationships.get(i).from == id)
		{
			if(!root)
			{
				temp2.add(getSecurityGroupNode(SecurityGroupRelationships.get(i).from));
				root = true;
			}
			
			temp1.add(SecurityGroupRelationships.get(i));
			//alert(SecurityGroupRelationships.get(i).to);
			temp2.add(getSecurityGroupNode(SecurityGroupRelationships.get(i).to));
		}
	}
	
	//return cur;
	
	//nodes: SecurityGroupNodes,
       //         edges: SecurityGroupRelationships
}








function drawSecurity(id)
{
	var currentNode, currentRelationships;
getsus(id);
	var options =
        {
		interaction: 
		{
			navigationButtons: true, 
			keyboard: true, 
			hover: true, 
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
				type:"continuous",
				forceDirection: "none",
				roundness: 0.0
			}
		},
		physics: 
		{
			enabled: false
		}	
	};

	var data =
	{
                nodes: temp2,
                edges: temp1
	};

	var container = document.getElementById("securityGroupDiv");

	networkSecurity = new vis.Network(container, data, options);
		
}