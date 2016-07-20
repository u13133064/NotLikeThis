package Visualizer;

import java.util.UUID;

public class Node 
{
	String name;
	UUID id;
	
	public Node(String name, UUID id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public UUID getID()
	{
		return id;
	}
}
