var Nodes, Relationships;

var nodeInfo = new Array();

var edgeNum = 0;
var nodeCount = 0;

var edgeIndex = 0;
var nodeIndex = 0;

var timer;
var  timerIsActive = false;

var levelOneArr = [];
var levelTwoArr = [];
var levelThreeArr = [];
var levelFourArr = [];
var levelFiveArr = [];

var JSONBuffer = {};
	var JSONCount = 0;

var informationArray = new Object();

function startTimer() 
{
	timer = setInterval(addNodesAndEdges, 1000);
	
	timerIsActive = true;
}
	 
function addNodesAndEdges() 
{
	if(!jQuery.isEmptyObject(JSONBuffer))
	{
		addNode(JSONBuffer.NodesArray[JSONCount].UUID, JSONBuffer.NodesArray[JSONCount].Name, JSONBuffer.NodesArray[JSONCount].Level);
			
		if(JSONBuffer.NodesArray[JSONCount].Relationships.length != 0)
		{
			for(j = 0; j < JSONBuffer.NodesArray[JSONCount].Relationships.length; j++)
			{
				addEdge(edgeNum, JSONBuffer.NodesArray[JSONCount].UUID, JSONBuffer.NodesArray[JSONCount].Relationships[j].UUID);
				edgeNum = edgeNum + 1;
			}
		}
		
		JSONCount++;
	}
}
	
	
	/*
	if(!jQuery.isEmptyObject(JSONBuffer))
	{
		for(i = 0; i < JSONBuffer.NodesArray.length; i++)
		{
			addNode(JSONBuffer.NodesArray[i].UUID, JSONBuffer.NodesArray[i].Name, JSONBuffer.NodesArray[i].Level);
			alert("node");
			if(JSONBuffer.NodesArray[i].Relationships.length != 0)
			{
				for(j = 0; j < JSONBuffer.NodesArray[i].Relationships.length; j++)
				{
					addEdge(edgeNum, JSONBuffer.NodesArray[i].UUID, JSONBuffer.NodesArray[i].Relationships[j].UUID);
					edgeNum = edgeNum + 1;
				}
			}
			delete JSONBuffer[JSONBuffer.NodesArray[i].UUID];
		}
		
		
	}
	*/


	 
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
			levelOneArr.push(idIn);
			break;
			case "2":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/region.png'					
			});
			levelTwoArr.push(idIn);
			break;
			case "3":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/VPC.png'						
			});
			levelThreeArr.push(idIn);
			break;
			case "4":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/subnetwork.png'						
			});
			levelFourArr.push(idIn);
			break;
			case "5":
			Nodes.add(
			{
				id: idIn,
				label: labelIn,
				level: levelIn,
				image: 'Images/Instance.png'					
			});
			levelFiveArr.push(idIn);
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
			to: inTo,
			hidden: true
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

//Function that starts traverse
function readInJSON(jsonIn)
{
	var obj = JSON.parse(jsonIn);
	
	Object.assign(JSONBuffer, obj);
}




function traverse(node, level)
{
	nodeCount++;

	addNode(nodeCount, node.Name, level);

	level = level + 1;
	
	for (var i = 0, len = node.Children.length; i < len; i++)
	{
		//addEdge(edgeNum, nodeCount, i);
		addEdge(edgeNum, node.UUID,	node.Children[i].UUID);

		edgeNum = edgeNum + 1;
		traverse(node.Children[i], level);
	}
}

function clearNodesAndEdges()
{
	var removedIds = Nodes.clear();
	removedIds = Relationships.clear();
	
	levelOneArr = [];
	levelTwoArr = [];
	levelThreeArr = [];
	levelFourArr = [];
	levelFiveArr = [];
}

function hideParentEdges()
{
	for(var i = 0; i < Relationships.length; i++)
	{
		if(levelOneArr.indexOf(Relationships.get(i).from) != -1 && levelOneArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelTwoArr.indexOf(Relationships.get(i).from) != -1 && levelTwoArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelThreeArr.indexOf(Relationships.get(i).from) != -1 && levelThreeArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelFourArr.indexOf(Relationships.get(i).from) != -1 && levelFourArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelFiveArr.indexOf(Relationships.get(i).from) != -1 && levelFiveArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
	}
}

function hideSiblingEdges()
{
	for(var i = 0; i < Relationships.length; i++)
	{
		if(levelOneArr.indexOf(Relationships.get(i).from) != -1 && levelOneArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelTwoArr.indexOf(Relationships.get(i).from) != -1 && levelTwoArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelThreeArr.indexOf(Relationships.get(i).from) != -1 && levelThreeArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelFourArr.indexOf(Relationships.get(i).from) != -1 && levelFourArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
		else 	if(levelFiveArr.indexOf(Relationships.get(i).from) != -1 && levelFiveArr.indexOf(Relationships.get(i).to) != -1)
		{
			Relationships.update(
			{
				id: Relationships.get(i).id,
				hidden: true
			});
		}
	}
}

function draw()
{
	Nodes = new vis.DataSet();

	//Nodes.on('*', function ()
	//{	});

	Relationships = new vis.DataSet();

	//Relationships.on('*', function ()
	//{     });

	var options =
        {
		interaction: {navigationButtons: true, keyboard: true, hover: true, hideEdgesOnDrag: true},
                nodes: {shape: 'circularImage', borderWidth:3, size:40,shapeProperties: { useBorderWithImage:true}, color: {background:'white', border:'black', highlight:{background:' #3498db ',border:' #black '}},font: {background: 'white', size: 14}	},
                "edges": {
			width: 2,
    "smooth": {
	    "type":"dynamic",
      "forceDirection": "none",
      "roundness": 1.0
    }
  },
		physics: {enabled: false},
		layout: {
		    improvedLayout:true,
		    hierarchical: {
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
		alert("Send to server " + ids);
	});

	//networkHierarchy.on("selectNode", function (params)
	//{alert(params.getLabel);});
	
	//start Timer
	//get JSON based on timer

}