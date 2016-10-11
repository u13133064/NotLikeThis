/*Containers used by vis.js to store nodes*/
var Nodes;

/*Containers used by vis.js to store relationships*/
var Relationships;

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
nodeCount is also used as the id for nodes in the Nodes container.
*/
var nodeCount = 0;

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


var finished=false;
var paused = false;

var informationJSON;

var readingFromFile = 0;
var readingFromServer = 0;

//var securityGroupsArr = [];
//var securityGroupCount = 0;

//var networkInterfacesArr = [];
//var networkInterfaceCount = 0;

//var allNodes = [];
//var allEdges = [];

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

/*Reads node from the server's .json*/
function getNodeFromServer() 
{
	var xhttp = new XMLHttpRequest();
	
	if(finished)
	{
		alert("Scan Finished");
		stopScan();
		return;
	}
 		
	xhttp.onreadystatechange = function() 
	{
		 if(finished)
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
			
			if(!finished&&!paused)    
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
	
/*Reads node from the server's .json*/
function addNodesAndEdges() 
{
	if(ServerJSONBuffer.length > bufferCount)
	{
		for(var k = 0; k< ServerJSONBuffer[bufferCount].NodesArray.length; k++)
		{
			addNode(ServerJSONBuffer[bufferCount].NodesArray[k].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Name, ServerJSONBuffer[bufferCount].NodesArray[k].Level);
			allNodes.push(ServerJSONBuffer[bufferCount].NodesArray[k].UUID);
			
			if(ServerJSONBuffer[bufferCount].NodesArray[k].Relationships.length != 0)
			{
				for(j = 0; j < ServerJSONBuffer[bufferCount].NodesArray[k].Relationships.length; j++)
				{
					
					addEdge(edgeNum, ServerJSONBuffer[bufferCount].NodesArray[k].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Relationships[j].UUID, ServerJSONBuffer[bufferCount].NodesArray[k].Relationships[j].type, edgeNum, ServerJSONBuffer[bufferCount].NodesArray[k].Level);
					allEdges.push(edgeNum, ServerJSONBuffer[bufferCount].NodesArray[k].UUID);
					edgeNum = edgeNum + 1;
				}
			}
			
		
			
		}
		bufferCount=bufferCount+1;
	}
}


function addNodesAndEdgesFile() 
{
	if(FileJSONBuffer.length > fileBufferCount)
	{
		addNode(FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Name, FileJSONBuffer[fileBufferCount].Level);
		allNodes.push(FileJSONBuffer[fileBufferCount].UUID);
		
		if(FileJSONBuffer[fileBufferCount].Relationships.length != 0)
		{
			for(j = 0; j < FileJSONBuffer[fileBufferCount].Relationships.length; j++)
			{
				addEdge(edgeNum, FileJSONBuffer[fileBufferCount].UUID, FileJSONBuffer[fileBufferCount].Relationships[j].UUID, FileJSONBuffer[fileBufferCount].Relationships[j].type, FileJSONBuffer[fileBufferCount].Level);
				allEdges.push(edgeNum, edgeNum);
				edgeNum = edgeNum + 1;
			}
		}
		/*
		if(FileJSONBuffer[fileBufferCount].SecurityGroups.length != 0)
		{
			for(j = 0; j < FileJSONBuffer[fileBufferCount].SecurityGroups.length; j++)
			{
				addToSecurityGroups(FileJSONBuffer[fileBufferCount].SecurityGroups[j].UUID, FileJSONBuffer[fileBufferCount].UUID)
			}
		}
		
		if(FileJSONBuffer[fileBufferCount].Networkinterfaces.length != 0)
		{
			for(j = 0; j < FileJSONBuffer[fileBufferCount].Networkinterfaces.length; j++)
			{
				addToNetworkInterfaces(FileJSONBuffer[fileBufferCount].Networkinterfaces[j].UUID, FileJSONBuffer[fileBufferCount].UUID)
			}
		}
		*/
		fileBufferCount = fileBufferCount + 1;
	}
}



function addToSecurityGroups(groupId, nodeID)
{
	var contains = false;
	var index;
	
	index = containsSecurityGroup(groupId);
	if(index == -1)	
	{
		securityGroupsArr[securityGroupCount] = {ID: groupId, Nodes:[], Relationships:[]};
		
		index = securityGroupCount;
		securityGroupCount = securityGroupCount + 1;
		securityGroupsArr[index].Nodes.push(nodeID);
		allNodes.push(nodeID);
	}
	else if(!securityGroupContainsNode(index, nodeID))
	{
		securityGroupsArr[index].Nodes.push(nodeID);
		//allNodes.push(nodeID);
	}
	
	var id;
	
	for(var i = 0; i < securityGroupsArr[index].Nodes.length; i++)
	{
		id = groupId + "_" + nodeID + "_" + securityGroupsArr[index].Nodes[i];
		
		if(securityGroupsArr[index].Nodes[i] != nodeID)
		{
			addEdgeSecurity(id, securityGroupsArr[index].Nodes[i], nodeID,groupId);
			//allEdges.push(id);
			securityGroupsArr[index].Relationships.push(id);
		}
	}
}

function containsSecurityGroup(groupId)
{
	for(var i = 0; i < securityGroupsArr.length; i++)
	{
		if(securityGroupsArr[i].ID == groupId)
		{
			return i;
		}
	}
	
	return -1;
}

function securityGroupContainsNode(index, nodeID)
{
	for(var i = 0; i < securityGroupsArr[index].Nodes.length; i++)
	{
		if(securityGroupsArr[index].Nodes[i] == nodeID)
		{
			return true;
		}
	}
	
	return false;
}

function showSecurityGroup(nodeID)
{
	hideAllNodesAndEdges();
	
	for(var i = 0; i < securityGroupsArr.length; i++)
	{
		if(securityGroupContainsNode(i, nodeID))
		{
			for(var j = 0; j < securityGroupsArr[i].Nodes.length; j++)
				showNode(securityGroupsArr[i].Nodes[j]);
			
			for(var j = 0; j < securityGroupsArr[i].Relationships.length; j++)
				showEdge(securityGroupsArr[i].Relationships[j]);
		}
	}
}

function hideSecurityGroup()
{
	showAllNodesAndEdges();
	
	for(var i = 0; i < securityGroupsArr.length; i++)
	{
		for(var j = 0; j < securityGroupsArr[i].Relationships.length; j++)
			hideEdge(securityGroupsArr[i].Relationships[j]);
	}
}



function addToNetworkInterfaces(groupId, nodeID)
{
	var contains = false;
	var index;
	
	index = containsNetworkInterface(groupId);
	if(index == -1)	
	{
		networkInterfacesArr[networkInterfaceCount] = {ID: groupId, Nodes:[], Relationships:[]};
		
		index = networkInterfaceCount;
		networkInterfaceCount = networkInterfaceCount + 1;
		networkInterfacesArr[index].Nodes.push(nodeID);
		allNodes.push(nodeID);
	}
	else if(!networkInterfaceContainsNode(index, nodeID))
	{
		networkInterfacesArr[index].Nodes.push(nodeID);
		//allNodes.push(nodeID);
	}
	
	var id;
	
	for(var i = 0; i < networkInterfacesArr[index].Nodes.length; i++)
	{
		id = groupId + "_" + nodeID + "_" + networkInterfacesArr[index].Nodes[i];
		
		if(networkInterfacesArr[index].Nodes[i] != nodeID)
		{
			addEdgeNetwork(id, networkInterfacesArr[index].Nodes[i], nodeID,groupId);
			//allEdges.push(id);
			networkInterfacesArr[index].Relationships.push(id);
		}
	}
}


function containsNetworkInterface(groupId)
{
	for(var i = 0; i < networkInterfacesArr.length; i++)
	{
		if(networkInterfacesArr[i].ID == groupId)
		{
			return i;
		}
	}
	
	return -1;
}
function networkInterfaceContainsNode(index, nodeID)
{
	for(var i = 0; i < networkInterfacesArr[index].Nodes.length; i++)
	{
		if(networkInterfacesArr[index].Nodes[i] == nodeID)
		{
			return true;
		}
	}
	
	return false;
}

function showNetworkInterface(nodeID)
{
	//hideAllNodesAndEdges();
	
	for(var i = 0; i < networkInterfacesArr.length; i++)
	{
		if(networkInterfaceContainsNode(i, nodeID))
		{
			for(var j = 0; j < securityGroupsArr[i].Nodes.length; j++)
				showNode(networkInterfacesArr[i].Nodes[j]);
			
			for(var j = 0; j < securityGroupsArr[i].Relationships.length; j++)
				showEdge(networkInterfacesArr[i].Relationships[j]);
		}
	}
}
function hideNetworkInterface()
{
	//showAllNodesAndEdges();
	
	for(var i = 0; i < networkInterfacesArr.length; i++)
	{
		for(var j = 0; j < networkInterfacesArr[i].Relationships.length; j++)
			hideEdge(networkInterfacesArr[i].Relationships[j]);
	}
}



/*


*/












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
function addEdgeSecurity(idIn, fromIn, toIn, group)
{
	Relationships.add(
	{
		id: idIn,
		from: fromIn,
		to: toIn,
		dashes: false,
		hidden: true,
		label: group,
		color: 
		{
			color:'black',
			highlight:'#D35A1A',
			hover: '#D35A1A'
		},

	});
}

function addEdgeNetwork(idIn, fromIn, toIn, group)
{
	Relationships.add(
	{
		id: idIn,
		from: fromIn,
		to: toIn,
		dashes: false,
		hidden: true,
		label: group,
		color: 
		{
			color:'black',
			highlight:'#D35A1A',
			hover: '#D35A1A'
		},

	});
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
	clearNodesAndEdges();
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



function hideNode(idIn)
{
	try 
	{
		Nodes.update(
		{
			id: idIn,
			hidden: true
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function showNode(idIn)
{
	try 
	{
		Nodes.update(
		{
			id: idIn,
			hidden: false
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function hideEdge(idIn)
{
	try 
	{
		Relationships.update(
		{
			id: idIn,
			hidden: true
		});
	}
	catch (err)
	{
		alert(err);
	}
}

function showEdge(idIn)
{
	try 
	{
		Relationships.update(
		{
			id: idIn,
			hidden: false
		});
	}
	catch (err)
	{
		alert(err);
	}
}









function hideAllNodesAndEdges()
{
	for(i = 0; i < allNodes.length; i++)
		hideNode(allNodes[i]);

	for(j = 0; j < allEdges.length; j++)
		hideEdge(allEdges[j]);
}

function showAllNodesAndEdges()
{
	for(i = 0; i < allNodes.length; i++)
		showNode(allNodes[i]);

	for(j = 0; j < allEdges.length; j++)
		showEdge(allEdges[j]);
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
		
		
	
	});
	
}