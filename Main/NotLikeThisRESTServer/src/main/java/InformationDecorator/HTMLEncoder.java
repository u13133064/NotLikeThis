package InformationDecorator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jedd Shneier.
 */
public class HTMLEncoder {
    public String securityGroupToHtml(String input) {
        List<String> securityGroups = Arrays.asList(input.split("\\#"));
        String output = "";
        for (int j = 0; j < securityGroups.size(); j++) {
            List<String> list = Arrays.asList(input.split("\\r?\\n"));
            output += "<table class=\"table table-hover table-inverse\" style=\"display: inline-block;\"><tbody>";

            for (int i = 0; i < list.size(); i++) {

                String parts[] = list.get(i).split("\\:");
                System.out.println(parts[0]);
                if (parts.length > 1) {
                    output += "<tr><td>" + parts[0] + "</td><td>" + parts[1] + "</td></tr>";
                }
            }
            output += "</tbody></table>";

        }
        return output;
    }
    public String informationToHtml(LinkedList<String> list)
    {
        String output ="<table class=\"table table-hover table-inverse\"><tbody>";

        for (int i =0;i<list.size();i++)
        {
            System.out.println(list.get(i));
            String parts[]=list.get(i).split("\\:");
            System.out.println(parts[0]);
            if(parts.length>1)
            {
                output += "<tr><td>" + parts[0] + "</td><td>" + parts[1] + "</td></tr>";
            }
        }
        output+="</tbody></table>";
        return output;
    }
}
