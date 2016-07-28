package ResponeObjects;

public class MockResponse implements ResponseObject {
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
						result += possibleNames.get(next) + ",";
						owned.add(next);
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
				result += possibleName.get(owned.get(i)) + ",";
			}
		}

		result=result.substring(0,result.length()-1);
		
		return result;
	}
	
	protected List<String> possibleNames;
	protected Random rng;
	protected List<Integer> owned;
	static protected List<Integer> used;
	public constructor(int rngSeed){
		rng = new Random(rngSeed)
		possibleNames = new LinkedList<>();
		used = new LinkedList<>();
		owned = new LinkedList<>();
		for (int i = 0; i < 20; ++i){
			String adding = "Node ";
			adding += i;
			possibleNames.add(adding);
		}
	}
}