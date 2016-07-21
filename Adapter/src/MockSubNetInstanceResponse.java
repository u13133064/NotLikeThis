package ResponeObjects;

public class MockSubNetInstanceResponse extends MockResponse {
    public String toString(){
		String result="";
		if (owned.size != 0)
		{
			for(int i=0; i<3;i++)
			{
				int next = rng.nextInt(30);
				if (!used.contains(next))
				{
					if (!owned.contains(next))
					{
						result += possibleNames.get(next) + ":";
						owned.add(next);
						next = rng.nextInt(used.size());
						result += used.get(next) + ",";
						owned.add(used.get(next);
					}else{i--;}
				}else{i--;}
						
				owned.add(next);
				used.add(next);
			}
		}
		else
		{
			for (int i=0; i<owned.size(); ++i)
			{
				result += possibleName.get(owned.get(i));
				if (i % 2 == 0)
					result += ":";
				else
					result += ",";
			}
		}

		result=result.substring(0,result.length()-1);
		
		return result;
	}
}