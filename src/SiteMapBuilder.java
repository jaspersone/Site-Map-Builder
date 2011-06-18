import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.Scanner;

public class SiteMapBuilder {
	private WebPage root;
	private String body;
	private HashSet<WebPage> links;

	public static void main(String[] abc) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter a web site URL:");

		String webSite = s.next();
		SiteMapBuilder site = new SiteMapBuilder(webSite);
		site.printMap();
	}
	
	public SiteMapBuilder(String webSite) {
		webSite = formatWebSiteURI(webSite);
		root = new WebPage(webSite);
		body = fetchBody(root.getURL());

		int bodyLocation = body.indexOf("<body");

		try {
			body = body.substring(bodyLocation);
		} catch (StringIndexOutOfBoundsException ob) {
			System.out.println("Could not find <body>");
			body = body.substring(0);
		}

		links = getLinks(body);
		int linkCount = links.size();
		System.out.println("There are " + linkCount + " links found on " + root + ":");
	}

	private String formatWebSiteURI (String address) {
		if (address.startsWith("http")) {
			return address;
		} else {
			return "http://" + address;
		}
	}
	
	private void printMap() {
		for (WebPage page : links) {
			System.out.println(page);
		}
	}
	
	private String fetchBody(String urlStr) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr);
			InputStreamReader isr = new InputStreamReader(url.openStream());
			BufferedReader reader = new BufferedReader(isr);
			int c;
			while((c = reader.read()) != -1)
				sb.append((char)c);
			reader.close();
		} catch(Exception exe) {
			System.out.println("This is the URL string that broke the internet: \n" + urlStr);
			System.out.println("Internet is broken");
		}
		return sb.toString();
	}

	private static HashSet<WebPage> getLinks(String page) {
		HashSet<WebPage> result = new HashSet<WebPage>();
		return getLinksHelper(page, result);
	}

	private static HashSet<WebPage> getLinksHelper(String page, HashSet<WebPage> result) {
		String searchTerm = "href=";
		if (page == "" || page.indexOf(searchTerm) == -1) {
			return result; // if page is empty or 'searchTerm' is not found in page
		} else {
			int searchTermLength = searchTerm.length();
			int startLocation = page.indexOf(searchTerm) + searchTermLength + 1; 
			String quoteMark = page.substring(startLocation - 1, startLocation); // grabs the correct quote mark (single or double)
			int endLocation = page.indexOf(quoteMark, startLocation); // searches for index of next matching closing quote
			String linkCandidate = page.substring(startLocation, endLocation); 
			if (isValidLink(linkCandidate))
				result.add(new WebPage(linkCandidate));
			return getLinksHelper(page.substring(endLocation), result);
		}
	}

	private static boolean isValidLink(String link) {
		if (link == null || link == "")
			return false;
		else {
			if (link.startsWith("#")) // if link is an internal link
				return false;
			else if (link.equals("/")) // if self link, do not include
				return false;
			else return true; // if not one of the exceptions, return true
		}

	}

}

