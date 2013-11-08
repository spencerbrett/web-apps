package edu.ucla.cs.cs144;

public class SearchResult implements Comparable<SearchResult> {
	private String itemId;
	private String name;
    
	public SearchResult() {}
    
	public SearchResult(String itemId, String name) {
		this.itemId = itemId;
		this.name = name;
	}
    
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
    
    public int compareTo(SearchResult sr) {
        if (Integer.parseInt(this.itemId) < Integer.parseInt(sr.itemId)) {
            return -1;
        } else if (this.itemId.equals(sr.itemId)) {
            return 0;
        } else {
            return 1;
        }
    }
}