import java.util.HashSet;

public class WebPage {
	String url;
	double linkRank;
	HashSet<WebPage> children;
	
	public WebPage () {
		url = "";
		linkRank = 0;
		children = new HashSet<WebPage>();
	}
	
	public WebPage (String address) {
		url = address;
		linkRank = 0;
		children = new HashSet<WebPage>();
	}
	
	public String getURL() {			return url; }
	public WebPage[] getChildren() {	return (WebPage[])children.toArray(); }
	public double getRank() {			return linkRank; }
	
	public int hashCode() {
		return url.hashCode();
	}
	public boolean equals(Object other) {
		if (other instanceof WebPage && url.equals(((WebPage) other).getURL())) {
			return true;
		} else
			return false;
	}
	public String toString() {
		return url;
	}
	
}
