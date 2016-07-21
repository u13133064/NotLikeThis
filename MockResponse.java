package ResponeObjects;

public class MockResponse implements ResponseObject {
    public String toString(){
		String result="";
		for(int i =0; i<getSubnets().size();i++)
		{
			result+=possibleNames.get(rng.nextInt(20))+",";
		}
		
		result=result.substring(0,result.length()-1);
		
		return result;
	}
	
	private List<String> possibleNames;
	Private Random rng;
	public constructor(int rngSeed){
		rng = new Random(rngSeed)
		possibleNames = new LinkedList<>();
		for (int i = 0; i < 20; ++i){
			String adding = "Node ";
			adding += i;
			possibleNames.add(adding);
		}
	}
}