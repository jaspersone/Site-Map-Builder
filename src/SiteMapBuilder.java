import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.lang.StringBuffer;
import java.util.HashSet;

public class SiteMapBuilder {
    public static void main(String[] abc) {
        String root = "http://www.yelp.com";
    	String a = fetchBody(root);
    	int bodyLocation = a.indexOf("<body");
    	a = a.substring(bodyLocation);
        HashSet<String> links = getLinks(a);

        int linkCount = links.size();
        System.out.println("There are " + linkCount + " links found on " + root + ":");
        for (String s : links) {
        	System.out.println(s);
        }
    }

    private static String fetchBody(String urlStr) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlStr);
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader reader = new BufferedReader(isr);
            int c;
            while((c = reader.read()) != -1)
                sb.append((char)c);
            reader.close();
        }catch(Exception exe) {
            System.out.println("Internet is broken");
        }
        return sb.toString();
    }

   private static HashSet<String> getLinks(String page) {
        HashSet<String> result = new HashSet<String>();
        return getLinksHelper(page, result);
    }
    
    private static HashSet<String> getLinksHelper(String page, HashSet<String> result) {
        String searchTerm = "href=";
        if (page == "" || page.indexOf(searchTerm) == -1) {
            return result;
        } else {
            int searchTermLength = searchTerm.length();
            int startLocation = page.indexOf(searchTerm) + searchTermLength + 1; 
            String quoteMark = page.substring(startLocation - 1, startLocation); // grabs the correct quote mark (single or double)
            int endLocation = page.indexOf(quoteMark, startLocation);
            String linkCandidate = page.substring(startLocation, endLocation); 
            if (isValidLink(linkCandidate))
            	result.add(linkCandidate);
            return getLinksHelper(page.substring(endLocation), result);
        }
    }
    
    private static boolean isValidLink(String link) {
    	if (link == null)
    		return false;
    	else {
    		if (link.startsWith("#")) // if link is an internal link
    			return false;
    		else return true; // if not one of the exceptions, return true
    	}
    	
    }
    
}

