package Visualizer;

import java.util.UUID;

public class Node 
{
	String name;
	UUID id;
	int level;
	
	public Node(String name, UUID id, int level)
	{
		this.name = name;
		this.id = id;
		this.level = level;
	}
	
	public Node(String name, UUID id)
	{
		this.name = name;
		this.id = id;
		level = -1;
	}
	
	public String getName()
	{
		return name;
	}
	
	public UUID getID()
	{
		return id;
	}
	
	public int getLevel()
	{
		return level;
	}
}
