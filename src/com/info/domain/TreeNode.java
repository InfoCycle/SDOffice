package com.info.domain;
import java.util.ArrayList; 
import java.util.List;

public class TreeNode {
	 /* 
     * •id: node id, which is important to load remote data 
     *  
     * •text: node text to show 
     *  
     * •state: node state, 'open' or 'closed', default is 'open'. When set to 
     * 'closed', the node have children nodes and will load them from remote 
     * site 
     *  
     * •checked: Indicate whether the node is checked selected. 
     *  
     * •attributes: custom attributes can be added to a node 
     *  
     * •children: an array nodes defines some children nodes 
     */ 
    public static String STATE_OPEN = "open"; 
    public static String STATE_CLOSED = "closed"; 
    private String id; 
    private String text; 
    private boolean chedcked; 
    private String state = STATE_OPEN; 
    private String attributes; 
    private List<TreeNode> children; 
 
    public TreeNode() { 
        this(null, null, STATE_OPEN); 
    } 
 
    public TreeNode(String id, String text) { 
        this(id, text, STATE_OPEN); 
    } 
 
    public TreeNode(String id, String text, String state) { 
        this.id = id; 
        this.text = text; 
        this.state = state; 
        this.children = new ArrayList<TreeNode>(); 
    } 
 
    public void addChild(TreeNode ctd) { 
        this.children.add(ctd); 
    } 
 
    public String getId() { 
        return id; 
    } 
 
    public void setId(String id) { 
        this.id = id; 
    } 
 
    public String getText() { 
        return text; 
    } 
 
    public void setText(String text) { 
        this.text = text; 
    } 
 
    public String getState() { 
        return state; 
    } 
 
    public void setState(String state) { 
        this.state = state; 
    } 
 
    public String getAttributes() { 
        return attributes; 
    } 
 
    public void setAttributes(String attributes) { 
        this.attributes = attributes; 
    } 
 
    public List<TreeNode> getChildren() { 
        return children; 
    } 
 
    public void setChildren(List<TreeNode> children) { 
        this.children = children; 
    } 
 
    public boolean getChedcked() { 
        return chedcked; 
    } 
 
    public void setChedcked(boolean chedcked) { 
        this.chedcked = chedcked; 
    }
}
