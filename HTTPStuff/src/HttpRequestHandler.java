import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author ashraf
 *
 */
@SuppressWarnings("restriction")
public class HttpRequestHandler implements HttpHandler 
{
	
	private static final String PASSWORD = "password";
	private static final String SECRET_PASSWORD = "secretPassword";
	private static final String REGION = "Region";
	
	private static final int PARAM_NAME_IDX = 0;
	private static final int PARAM_VALUE_IDX = 1;
	
	private static final int HTTP_OK_STATUS = 200;
	
	private static final String AND_DELIMITER = "&";
	private static final String EQUAL_DELIMITER = "=";
	
	String currentRegion;
	
	public void handle(HttpExchange t) throws IOException 
	{
		//Create a response form the request query parameters
		URI uri = t.getRequestURI();
		String response = getResponse(uri);
		System.out.println("Response: " + response);
		//Set the response header status and length
		t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
		//Write the response string
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	public String getResponse(URI uri) 
	{
		String password, secretPassword, regionName;
		
		String query = uri.getQuery();
		
		if(query.contains("password=") && query.contains("&secretPassword="))
		{
			if(queryDatabase(getPasswordFromURI(uri), getSecretPasswordFromURI(uri)))
			{
				return mainHTML;
			}
			else
				return loginHTMLAlert;
		}
		else
		{
			System.out.println(getRegionFromURI(uri));
			return mainHTML;
		}
	}
	
	private String getPasswordFromURI(URI uri)
	{
		String password = "";
		String secretPassword = "";
		//Get the request query
		String query = uri.getQuery();
		if (query != null) 
		{
			String[] queryParams = query.split(AND_DELIMITER);
			
			if (queryParams.length > 0) 
			{
				for (String qParam : queryParams) 
				{
					String[] param = qParam.split(EQUAL_DELIMITER);
					
					if (param.length > 0) 
					{
						for (int i = 0; i < param.length; i++) 
						{
							if (PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								password = param[PARAM_VALUE_IDX];
							}
							if (SECRET_PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								secretPassword = param[PARAM_VALUE_IDX];
							}
						}
					}
				}
			}
		}
		return password;
	}
		
	private String getSecretPasswordFromURI(URI uri)
	{
		String password = "";
		String secretPassword = "";
		//Get the request query
		String query = uri.getQuery();
		if (query != null) 
		{
			String[] queryParams = query.split(AND_DELIMITER);
			
			if (queryParams.length > 0) 
			{
				for (String qParam : queryParams) 
				{
					String[] param = qParam.split(EQUAL_DELIMITER);
					
					if (param.length > 0) 
					{
						for (int i = 0; i < param.length; i++) 
						{
							if (PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								password = param[PARAM_VALUE_IDX];
							}
							if (SECRET_PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								secretPassword = param[PARAM_VALUE_IDX];
							}
						}
					}
				}
			}
		}
		return secretPassword;
	}
	
	private Boolean queryDatabase(String password, String secretPassword)
	{
		if(password.equals("a") && secretPassword.equals("b"))
				return true;
		else
			return false;
	}
	
	private String getRegionFromURI(URI uri)
	{
		String region = "";
		String query = uri.getQuery();
		if (query != null) 
		{
			String[] queryParams = query.split(AND_DELIMITER);
			
			if (queryParams.length > 0) 
			{
				for (String qParam : queryParams) 
				{
					String[] param = qParam.split(EQUAL_DELIMITER);
					
					if (param.length > 0) 
					{
						for (int i = 0; i < param.length; i++) 
						{
							if (REGION.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								region = param[PARAM_VALUE_IDX];
							}
						}
					}
				}
			}
		}
		return region;
	}
	
	
	private String createResponseFromQueryParams(URI uri) 
	{
		
		String fName = "";
		String lName = "";
		//Get the request query
		String query = uri.getQuery();
		if (query != null) 
		{
			System.out.println("Query: " + query);
			String[] queryParams = query.split(AND_DELIMITER);
			
			if (queryParams.length > 0) 
			{
				for (String qParam : queryParams) 
				{
					String[] param = qParam.split(EQUAL_DELIMITER);
					
					if (param.length > 0) 
					{
						for (int i = 0; i < param.length; i++) 
						{
							if (PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								fName = param[PARAM_VALUE_IDX];
							}
							if (SECRET_PASSWORD.equalsIgnoreCase(param[PARAM_NAME_IDX])) 
							{
								lName = param[PARAM_VALUE_IDX];
							}
						}
					}
				}
			}
		}
		//file:///C:/Users/Muller/Desktop/Eclipse/HTTPStuff/demo_form.asp?password=&secretPassword=
		return " <html><header><title>This is title</title></header><body>Hello world</body></html>";

	}
	
	String loginHTML =
            "<!doctype html>\n" +
"<html>\n" +
"	<head>\n" +
"		<title>Amazon Web Services Network Visualizer</title>\n" +
"		\n" +
"		<link href=\"http://visjs.org/dist/vis.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
"\n" +
"		<style type=\"text/css\">		    \n" +
"			#banner \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 10px;\n" +
"				left: 10px;\n" +
"				width: 1500px;\n" +
"				height: 150px;\n" +
"				border: 2px solid black;\n" +
"			}\n" +
"			\n" +
"			#loginDiv \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 170px;\n" +
"				left: 525px;\n" +
"				width: 450px;\n" +
"				height: 150px;\n" +
"				border: 2px solid black;\n" +
"				background: white;\n" +
"			}\n" +
"			\n" +
"			#p1 \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 10px;\n" +
"				left: 10px;\n" +
"			}\n" +
"			\n" +
"			#p2 \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 50px;\n" +
"				left: 10px;\n" +
"			}\n" +
"			\n" +
"			#passwordInput \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 25px;\n" +
"				left: 130px;\n" +
"				width: 300px;\n" +
"			}\n" +
"			\n" +
"			#secretPasswordInput \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 65px;\n" +
"				left: 130px;\n" +
"				width: 300px;\n" +
"			}\n" +
"			\n" +
"			#submitPasswords \n" +
"			{\n" +
"				position: absolute;\n" +
"				top: 100px;\n" +
"				left: 175px;\n" +
"				width: 100px;\n" +
"			}\n" +
"		</style>\n" +
"	</head>\n" +
"	\n" +
"	<body style=\"background-color:#FF9933;\">\n" +
"\n" +
"		<img id=\"banner\" src=\"Banner.jpg\" alt=\"AWS Banner\">\n" +
"		\n" +
"		<div id=\"loginDiv\" >\n" +
"			<form action=\"http://localhost:8000/\">\n" +
"				 <p id=\"p1\">Password:</p> \n" +
"				 <input type=\"text\" id=\"passwordInput\" name=\"password\">\n" +
"				 <p id=\"p2\">Secret password:</p> \n" +
"				<input type=\"text\" id=\"secretPasswordInput\" name=\"secretPassword\">\n" +
"				\n" +
"				<input id=\"submitPasswords\" type=\"submit\" value=\"Submit\">\n" +
"			</form>\n" +
"		</div>\n" +
"\n" +
"	</body>\n" +
"</html>";

	String loginHTMLAlert =
			"<!doctype html>\n" +
					"<html>\n" +
					"	<head>\n" +
					"		<title>Amazon Web Services Network Visualizer</title>\n" +
					"		\n" +
					"		<link href=\"http://visjs.org/dist/vis.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
					"\n" +
					"		<style type=\"text/css\">		    \n" +
					"			#banner \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 10px;\n" +
					"				left: 10px;\n" +
					"				width: 1500px;\n" +
					"				height: 150px;\n" +
					"				border: 2px solid black;\n" +
					"			}\n" +
					"			\n" +
					"			#loginDiv \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 170px;\n" +
					"				left: 525px;\n" +
					"				width: 450px;\n" +
					"				height: 150px;\n" +
					"				border: 2px solid black;\n" +
					"				background: white;\n" +
					"			}\n" +
					"			\n" +
					"			#p1 \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 10px;\n" +
					"				left: 10px;\n" +
					"			}\n" +
					"			\n" +
					"			#p2 \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 50px;\n" +
					"				left: 10px;\n" +
					"			}\n" +
					"			\n" +
					"			#passwordInput \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 25px;\n" +
					"				left: 130px;\n" +
					"				width: 300px;\n" +
					"			}\n" +
					"			\n" +
					"			#secretPasswordInput \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 65px;\n" +
					"				left: 130px;\n" +
					"				width: 300px;\n" +
					"			}\n" +
					"			\n" +
					"			#submitPasswords \n" +
					"			{\n" +
					"				position: absolute;\n" +
					"				top: 100px;\n" +
					"				left: 175px;\n" +
					"				width: 100px;\n" +
					"			}\n" +
					"		</style>\n" +
					"	</head>\n" +
					"	\n" +
					"	<body style=\"background-color:#FF9933;\" onload=\"wrongPasswords() \">\n" +
					"	\n" +
					"		<script>\n" +
					"			function wrongPasswords() \n" +
					"			{\n" +
					"			    alert(\"Incorrect password or secret password\");\n" +
					"			}\n" +
					"		</script>\n" +
					"\n" +
					"		<img id=\"banner\" src=\"Banner.jpg\" alt=\"AWS Banner\">\n" +
					"		\n" +
					"		<div id=\"loginDiv\" >\n" +
					"			<form action=\"http://localhost:8000/\">\n" +
					"				 <p id=\"p1\">Password:</p> \n" +
					"				 <input type=\"text\" id=\"passwordInput\" name=\"password\">\n" +
					"				 <p id=\"p2\">Secret password:</p> \n" +
					"				<input type=\"text\" id=\"secretPasswordInput\" name=\"secretPassword\">\n" +
					"				\n" +
					"				<input id=\"submitPasswords\" type=\"submit\" value=\"Submit\">\n" +
					"			</form>\n" +
					"		</div>\n" +
					"\n" +
					"	</body>\n" +
					"</html>";
	
    String mainHTML =
    		"<!doctype html>\n" +
    				"<html>\n" +
    				"	<head>\n" +
    				"		<title>Amazon Web Services Network Visualizer</title>\n" +
    				"		\n" +
    				"		<script type=\"text/javascript\" src=\"http://visjs.org/dist/vis.js\"></script>\n" +
    				"		<link href=\"http://visjs.org/dist/vis.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
    				"\n" +
    				"		<style type=\"text/css\">\n" +
    				"			#buttonHolder \n" +
    				"			{\n" +
    				"				position: absolute;\n" +
    				"				left: 10px;\n" +
    				"				top: 170px;\n" +
    				"				width: 150px;\n" +
    				"				height: 700px;\n" +
    				"				border: 2px solid black;\n" +
    				"				background: white;\n" +
    				"			}\n" +
    				"			  \n" +
    				"			#mynetwork \n" +
    				"			{\n" +
    				"				position: absolute;\n" +
    				"				left: 170px;\n" +
    				"				top: 170px;\n" +
    				"				width: 1340px;\n" +
    				"				height: 700px;\n" +
    				"				border: 2px solid black;\n" +
    				"				background: white;\n" +
    				"			}\n" +
    				"			    \n" +
    				"			#banner \n" +
    				"			{\n" +
    				"				position: absolute;\n" +
    				"				top: 10px;\n" +
    				"				left: 10px;\n" +
    				"				width: 1500px;\n" +
    				"				height: 150px;\n" +
    				"				border: 2px solid black;\n" +
    				"			}\n" +
    				"			\n" +
    				"			#regionSelect \n" +
    				"			{\n" +
    				"				position: absolute;\n" +
    				"				top: 1px;\n" +
    				"				left: 2px;\n" +
    				"				width: 146px;\n" +
    				"				height: 50px;\n" +
    				"			}\n" +
    				"			\n" +
    				"			#regionButton\n" +
    				"			{\n" +
    				"				position: absolute;\n" +
    				"				top: 55px;\n" +
    				"				left: 2px;\n" +
    				"				width: 146px;\n" +
    				"				height: 50px;\n" +
    				"			}\n" +
    				"		</style>\n" +
    				"	</head>\n" +
    				"	\n" +
    				"	<body style=\"background-color:#FF9933;\">\n" +
    				"		<img id=\"banner\" src=\"Banner.jpg\" alt=\"Mountain View\">\n" +
    				"\n" +
    				"		<div id=\"buttonHolder\">\n" +
    				"			<form action=\"http://localhost:8000/\">\n" +
    				"				<select id=\"regionSelect\" name=\"Region\">\n" +
    				"					<option value=\"USE\">US East (N. Virginia)</option>\n" +
    				"					<option value=\"USW\">US West (Oregon)</option>\n" +
    				"					<option value=\"APSi\">Asia Pacific (Singapore)</option>\n" +
    				"					<option value=\"APSy\">Asia Pacific (Sydney)</option>\n" +
    				"					<option value=\"APT\">Asia Pacific (Tokyo)</option>\n" +
    				"					<option value=\"EUF\">EU (Frankfurt)</option>\n" +
    				"					<option value=\"EUI\">EU (Ireland)</option>\n" +
    				"				</select>\n" +
    				"				  <br><br>\n" +
    				"				  <input type=\"submit\" value=\"submit\" id=\"regionButton\" type=\"button\">\n" +
    				"			\n" +
    				"			</form>\n" +
    				"\n" +
    				"		\n" +
    				"		</div>\n" +
    				"		<div id=\"mynetwork\"></div>\n" +
    				"\n" +
    				"		<script type=\"text/javascript\">\n" +
    				"		  // create an array with nodes\n" +
    				"		  var nodes= new vis.DataSet([\n" +
    				"		    {id: 'd44ff3ed-760a-4bed-a9bc-994397b34a4e',	 label: 'AWS', 		shape: 'dot', level: 1, color: {background:'white', border:'black'} ,font: {background: 'white'}	,title: 'Name:  VPC count:'},\n" +
    				"		    {id: 'fef375e2-3032-4ffe-ab24-b2d2cdf841a4' ,	 label: 'Network 1', 	shape: 'dot', level: 2 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'bfe55c92-ad8d-4abb-80c2-0ec46a295dc5' ,	 label: 'AWSRegion 1', 	shape: 'dot', level: 3, color: {background:'white', border:'black'}   ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '90a7ae9a-9e79-4c82-9e63-a2a1ec713df3' ,	label: 'VPC 1',		 shape: 'dot', level: 4  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '399b9413-05e8-47a6-8c42-2a6e0931ade2' ,	 label: 'AVZone 1', 	shape: 'dot', level: 5  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '9f35a11f-c847-40fc-87d2-13084fd67aab' ,	 label: 'SubNetwork 1', shape: 'dot', level: 6  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'fba1ac67-00a2-46b4-b7d9-566cedd96ca1' ,	 label: 'SubNetwork 2', shape: 'dot', level: 7  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '6a3d3d09-59b6-43e9-8c72-066abebe0a82' ,	 label: 'AWSInstance 2', shape: 'dot', level: 8 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'e4a96768-dc41-4255-bc24-af78ad5597da' ,	 label: 'SubNetwork 3', shape: 'dot', level: 7 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '4bf1c40b-2001-4e19-8de7-05b1338611a4' ,	label: 'SubNetwork 4', shape: 'dot', level: 8 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'd6c2f1a1-7d79-4151-86e4-b267aac46d88' ,	 label: 'AWSInstance 3', shape: 'dot', level: 8 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'dcd973e0-b24f-4056-ab4d-a1bce732922f' , 	label: 'AWSInstance 1', shape: 'dot', level: 7  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'b9200bcd-93a2-4efe-b91d-c73a631c2907' ,	 label: 'VPC 2', shape: 'dot', level: 4  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '56c4abb1-6f36-4a2f-a92b-da4152068f0c' , 	label: 'AWSRegion 2', shape: 'dot', level: 3 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'a945579e-346f-49bd-b2e8-4ba18c3b817a' ,	 label: 'VPC 3', shape: 'dot', level: 4  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '1061ba0e-5e87-44af-b710-1bbcbd9e45d3' , 	label: 'VPC 4', shape: 'dot', level: 4 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '2be6bdf9-ce38-4ccf-a666-9587d9f6639e' ,	 label: 'Network 2', shape: 'dot', level: 2  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '668f0385-0e2e-4125-b618-d1f77d71030c' ,	 label: 'AWSRegion 3', shape: 'dot', level: 3  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '2f0f2b1c-4ad2-4424-b6be-2290fa8a5466' 	, label: 'VPC 5', shape: 'dot', level: 4 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '6ea48917-acee-4ce6-bedf-fb1e8abfc3ee' ,	 label: 'VPC 6', shape: 'dot', level: 4 , color: {background:'white', border:'black'}  ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'e293801d-3a35-4730-95f8-5b3c0d5b111b' ,	 label: 'AWSRegion 4', shape: 'dot', level: 3, color: {background:'white', border:'black'}   ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: '2c9e2803-1173-4385-a0e2-fdf26acece2e' , 	label: 'VPC 7', shape: 'dot', level: 4  , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		    {id: 'a2184f1d-2883-415b-a53e-b66f6eb87975' ,	 label: 'VPC 8', shape: 'dot', level: 4 , color: {background:'white', border:'black'} ,font: {background: 'white'},title: 'aaa'},\n" +
    				"		  ]);  var edges = new vis.DataSet([\n" +
    				"		    {from: 'd44ff3ed-760a-4bed-a9bc-994397b34a4e', to: 'fef375e2-3032-4ffe-ab24-b2d2cdf841a4'},\n" +
    				"		    {from: 'fef375e2-3032-4ffe-ab24-b2d2cdf841a4', to: 'bfe55c92-ad8d-4abb-80c2-0ec46a295dc5'},\n" +
    				"		    {from: 'bfe55c92-ad8d-4abb-80c2-0ec46a295dc5', to: '90a7ae9a-9e79-4c82-9e63-a2a1ec713df3'},\n" +
    				"		    {from: '90a7ae9a-9e79-4c82-9e63-a2a1ec713df3', to: '399b9413-05e8-47a6-8c42-2a6e0931ade2'},\n" +
    				"		    {from: '399b9413-05e8-47a6-8c42-2a6e0931ade2', to: '9f35a11f-c847-40fc-87d2-13084fd67aab'},\n" +
    				"		    {from: '9f35a11f-c847-40fc-87d2-13084fd67aab', to: 'fba1ac67-00a2-46b4-b7d9-566cedd96ca1'},\n" +
    				"		    {from: 'fba1ac67-00a2-46b4-b7d9-566cedd96ca1', to: '6a3d3d09-59b6-43e9-8c72-066abebe0a82'},\n" +
    				"		    {from: '9f35a11f-c847-40fc-87d2-13084fd67aab', to: 'e4a96768-dc41-4255-bc24-af78ad5597da'},\n" +
    				"		    {from: 'e4a96768-dc41-4255-bc24-af78ad5597da', to: '4bf1c40b-2001-4e19-8de7-05b1338611a4'},\n" +
    				"		    {from: 'e4a96768-dc41-4255-bc24-af78ad5597da', to: 'd6c2f1a1-7d79-4151-86e4-b267aac46d88'},\n" +
    				"		    {from: '9f35a11f-c847-40fc-87d2-13084fd67aab', to: 'dcd973e0-b24f-4056-ab4d-a1bce732922f'},\n" +
    				"		    {from: 'bfe55c92-ad8d-4abb-80c2-0ec46a295dc5', to: 'b9200bcd-93a2-4efe-b91d-c73a631c2907'},\n" +
    				"		    {from: 'fef375e2-3032-4ffe-ab24-b2d2cdf841a4', to: '56c4abb1-6f36-4a2f-a92b-da4152068f0c'},\n" +
    				"		    {from: '56c4abb1-6f36-4a2f-a92b-da4152068f0c', to: 'a945579e-346f-49bd-b2e8-4ba18c3b817a'},\n" +
    				"		    {from: '56c4abb1-6f36-4a2f-a92b-da4152068f0c', to: '1061ba0e-5e87-44af-b710-1bbcbd9e45d3'},\n" +
    				"		    {from: 'd44ff3ed-760a-4bed-a9bc-994397b34a4e', to: '2be6bdf9-ce38-4ccf-a666-9587d9f6639e'},\n" +
    				"		    {from: '2be6bdf9-ce38-4ccf-a666-9587d9f6639e', to: '668f0385-0e2e-4125-b618-d1f77d71030c'},\n" +
    				"		    {from: '668f0385-0e2e-4125-b618-d1f77d71030c', to: '2f0f2b1c-4ad2-4424-b6be-2290fa8a5466'},\n" +
    				"		    {from: '668f0385-0e2e-4125-b618-d1f77d71030c', to: '6ea48917-acee-4ce6-bedf-fb1e8abfc3ee'},\n" +
    				"		    {from: '2be6bdf9-ce38-4ccf-a666-9587d9f6639e', to: 'e293801d-3a35-4730-95f8-5b3c0d5b111b'},\n" +
    				"		    {from: 'e293801d-3a35-4730-95f8-5b3c0d5b111b', to: '2c9e2803-1173-4385-a0e2-fdf26acece2e'},\n" +
    				"		    {from: 'e293801d-3a35-4730-95f8-5b3c0d5b111b', to: 'a2184f1d-2883-415b-a53e-b66f6eb87975'},\n" +
    				"		  ]);\n" +
    				"\n" +
    				"			var container = document.getElementById('mynetwork');\n" +
    				"			var data = \n" +
    				"			{\n" +
    				"				nodes: nodes,\n" +
    				"				edges: edges\n" +
    				"			};\n" +
    				"			\n" +
    				"			var options = {layout: {hierarchical: {direction: 'UD'}}, nodes: {borderWidth: 2}, edges: {width: 2}};\n" +
    				"			var network = new vis.Network(container, data, options);\n" +
    				"		</script>\n" +
    				"\n" +
    				"		<script src=\"../googleAnalytics.js\"></script>\n" +
    				"	</body>\n" +
    				"</html>";

}
