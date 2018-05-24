import java.util.ArrayList;
import java.util.List;

class StarTree {

	public String attribute = "";
	public int count = 0;
	public List<StarTree> children = new ArrayList<StarTree>();
	public boolean isLeaf = false;
	public boolean hasSibling = false;

	public StarTree() {
	}

	public StarTree(int c) {
		this.count = c;
	}

	public StarTree(String attr, int c) {
		this.attribute = attr;
		this.count = c;
	}
}