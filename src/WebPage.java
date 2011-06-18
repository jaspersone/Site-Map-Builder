import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashSet;

public class WebPage {
	private String rawURI; // stores original string passed to WebPage
	private URI myURI;
	private double linkRank;
	private HashSet<WebPage> children;

	public WebPage (String address) {
		rawURI = address;
		try {
			myURI = new URI(address);
		} catch (URISyntaxException e) {
			try { // clean up links that are not properly URL encoded.
				myURI = new URI(URLEncoder.encode(address, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Error on:     " + rawURI);
			System.out.println("Converted to: " + myURI);
		}
		linkRank = 0;
		children = new HashSet<WebPage>();
	}
	
	public String getURL() {			return myURI.toString(); }
	public WebPage[] getChildren() {	return (WebPage[])children.toArray(); }
	public double getRank() {			return linkRank; }
	
	public int hashCode() {
		return myURI.hashCode();
	}
	public boolean equals(Object other) {
		if (other instanceof WebPage && this.getURL().equals(((WebPage) other).getURL())) {
			return true;
		} else
			return false;
	}
	public String toString() {
		return myURI.toString();
	}
	
}
