package Visualizer;

import java.util.UUID;

public class Relationship 
{
		UUID from, to;
		
		public Relationship(UUID from, UUID to)
		{
			this.from = from;
			this.to = to;
		}
		
		public void setFrom(UUID f)
		{
			from = f;
		}
		
		public void setTo(UUID t)
		{
			to = t;
		}
		
		public UUID getFrom()
		{
			return from;
		}
		
		public UUID getTo()
		{
			return to;
		}

}
