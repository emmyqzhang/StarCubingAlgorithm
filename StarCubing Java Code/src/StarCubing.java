import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StarCubing {

	private Animal animal = new Animal();

	// One-Dimensional Aggregates
	private Table table = new Table();

	// Don't meet the condition
	// private ReductionTable starNodeTable = new ReductionTable();

	int totalCount = 0;

	// star-table
	Map<Integer, List<String>> starTable = new HashMap<Integer, List<String>>();

	// Compressed Base Table: After Star Reduction
	Map<List<String>, Integer> reducedStarTable = new HashMap<List<String>, Integer>();

	public void initData() {
		String line = null;
		try {
			InputStream is = this.getClass().getResourceAsStream("/resource/zoo.data");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

			int index = 0;
			String[] columns = null;
			while ((line = bufferedReader.readLine()) != null) {
				columns = line.split(",");
				animal.name.put(index, columns[0]);
				animal.hair.put(index, columns[1]);
				animal.feathers.put(index, columns[2]);
				animal.eggs.put(index, columns[3]);
				animal.milk.put(index, columns[4]);
				animal.airborne.put(index, columns[5]);
				animal.aquatic.put(index, columns[6]);
				animal.predator.put(index, columns[7]);
				animal.toothed.put(index, columns[8]);
				animal.backbone.put(index, columns[9]);
				animal.breathes.put(index, columns[10]);
				animal.venomous.put(index, columns[11]);
				animal.fins.put(index, columns[12]);
				animal.legs.put(index, columns[13]);
				animal.tail.put(index, columns[14]);
				animal.domestic.put(index, columns[15]);
				animal.catsize.put(index, columns[16]);
				animal.type.put(index, columns[17]);
				index++;
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * perform the computation for One-Dimensional Aggregates
	 * 
	 * @param animalColumn
	 * @param table
	 */
	public void aggregateOneDimension() {
		aggregate(animal.airborne, table.airborneCount);
		aggregate(animal.aquatic, table.aquaticCount);
		aggregate(animal.backbone, table.backboneCount);
		aggregate(animal.breathes, table.breathesCount);
		aggregate(animal.catsize, table.catsizeCount);
		aggregate(animal.domestic, table.domesticCount);
		aggregate(animal.eggs, table.eggsCount);
		aggregate(animal.feathers, table.feathersCount);
		aggregate(animal.fins, table.finsCount);
		aggregate(animal.hair, table.hairCount);
		aggregate(animal.legs, table.legsCount);
		aggregate(animal.milk, table.milkCount);
		aggregate(animal.name, table.nameCount);
		aggregate(animal.predator, table.predatorCount);
		aggregate(animal.tail, table.tailCount);
		aggregate(animal.toothed, table.toothedCount);
		aggregate(animal.type, table.typeCount);
		aggregate(animal.venomous, table.venomousCount);
	}

	/**
	 * calculating the count
	 * 
	 * @param animalColumn
	 * @param table
	 */
	private void aggregate(Map<Integer, String> animalColumn, Map<String, Integer> table) {
		Iterator<Integer> it = animalColumn.keySet().iterator();
		Integer key = null;
		while (it.hasNext()) {
			key = it.next();
			if (!table.containsKey(animalColumn.get(key))) {
				table.put(animalColumn.get(key), 1);
			} else {
				int t = table.get(animalColumn.get(key));
				table.put(animalColumn.get(key), t + 1);
			}
		}
	}

	/**
	 * generate the star-table
	 */
	public void createStarTable(int aprioric) {

		updateData(animal.airborne, table.airborneCount, aprioric);
		updateData(animal.aquatic, table.aquaticCount, aprioric);
		updateData(animal.backbone, table.backboneCount, aprioric);
		updateData(animal.breathes, table.breathesCount, aprioric);
		updateData(animal.catsize, table.catsizeCount, aprioric);
		updateData(animal.domestic, table.domesticCount, aprioric);
		updateData(animal.eggs, table.eggsCount, aprioric);
		updateData(animal.feathers, table.feathersCount, aprioric);
		updateData(animal.fins, table.finsCount, aprioric);
		updateData(animal.hair, table.hairCount, aprioric);
		updateData(animal.legs, table.legsCount, aprioric);
		updateData(animal.milk, table.milkCount, aprioric);
		updateData(animal.name, table.nameCount, aprioric);
		updateData(animal.predator, table.predatorCount, aprioric);
		updateData(animal.tail, table.tailCount, aprioric);
		updateData(animal.toothed, table.toothedCount, aprioric);
		updateData(animal.type, table.typeCount, aprioric);
		updateData(animal.venomous, table.venomousCount, aprioric);

		// star-table initialization 
		List<String> temp = null;
		for (int i = 0; i < animal.airborne.size(); i++) {
			temp = new ArrayList<String>();
			temp.add(animal.name.get(i));
			temp.add(animal.hair.get(i));
			temp.add(animal.feathers.get(i));
			temp.add(animal.eggs.get(i));
			temp.add(animal.milk.get(i));
			temp.add(animal.airborne.get(i));
			temp.add(animal.aquatic.get(i));
			temp.add(animal.predator.get(i));
			temp.add(animal.toothed.get(i));
			temp.add(animal.backbone.get(i));
			temp.add(animal.breathes.get(i));
			temp.add(animal.venomous.get(i));
			temp.add(animal.fins.get(i));
			temp.add(animal.legs.get(i));
			temp.add(animal.tail.get(i));
			temp.add(animal.domestic.get(i));
			temp.add(animal.catsize.get(i));
			temp.add(animal.type.get(i));

			starTable.put(i, temp);
		}
	}

	/**
	 * update the original data set by iceberg cube condition 
	 * 
	 * @param columnData
	 * @param colunmReduction
	 */
	private void updateData(Map<Integer, String> columnData, Map<String, Integer> columnCount, int aprioric) {
		for (Map.Entry<Integer, String> i : columnData.entrySet()) {
			if (columnCount.get(i.getValue()) < aprioric) {
				i.setValue("*");
			}
		}
	}

	/**
	 * Compressed Base Table: After Star Reduction
	 */
	public void reduceStarTable() {
		Iterator<Integer> it = starTable.keySet().iterator();
		Integer key = null;
		Integer t = null;
		while (it.hasNext()) {
			key = it.next();
			if (!reducedStarTable.containsKey(starTable.get(key))) {
				reducedStarTable.put(starTable.get(key), 1);
			} else {
				t = reducedStarTable.get(starTable.get(key));
				reducedStarTable.put(starTable.get(key), t + 1);
			}
		}
	}

	public void totalCount() {
		for (Map.Entry<List<String>, Integer> i : reducedStarTable.entrySet()) {
			int t = i.getValue();
			totalCount = totalCount + t;
		}
	}

	StarTree root = new StarTree(totalCount);

	public StarTree checkChild(StarTree root, String s) {

		List<StarTree> child = root.children;
		StarTree temp = null;

		if (!child.isEmpty()) {
			for (int i = 0; i < child.size(); i++) {
				temp = child.get(i);
				if (temp.attribute.equals(s))
					return temp;
			}
		}
		return null;
	}

	public void createStarTree(List<String> row, int iCount) {
		StarTree currentNode = root;

		for (int i = 0; i < row.size(); i++) {
			StarTree status = checkChild(currentNode, row.get(i));
			if (status == null) {
				StarTree newNode = new StarTree(row.get(i), iCount);
				if (i == row.size() - 1) {
					newNode.isLeaf = true;
				}
				currentNode.children.add(newNode);
				if (currentNode.children.size() > 1) {
					currentNode.hasSibling = true;
				}
				currentNode = newNode;
			} else {
				currentNode = status;
				currentNode.count = currentNode.count + 1;
			}
		}
	}

	public void getRow() {
		Iterator<List<String>> it = reducedStarTable.keySet().iterator();
		List<String> key = null;
		while (it.hasNext()) {
			key = it.next();
			createStarTree(key, reducedStarTable.get(key));
		}
	}

	public void starCubing() {
		dfs(root);
	}

	public void dfs(StarTree root) {
		if (root.children.size() <= 0) {
			return;
		}
		for (int i = 0; i < root.children.size(); i++) {
			dfs(root.children.get(i));
		}
		return;
	}

	public static void main(String[] args) {
		StarCubing red = new StarCubing();
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter apriori codition");
		int aprioric = sc.nextInt();
		sc.close();
		red.initData();

		Date start = new Date();

		red.aggregateOneDimension();
		red.createStarTable(aprioric);
		red.reduceStarTable();
		red.totalCount();
		red.getRow();
		red.starCubing();

		Date end = new Date();
		System.out.println("Run-Time: " + ((end.getTime() - start.getTime()) / 1000.0) + "s");
		System.out.println();

		// the order of output value
		// 1. animal name: Unique for each instance
		// 2. hair Boolean
		// 3. feathers Boolean
		// 4. eggs Boolean
		// 5. milk Boolean
		// 6. airborne Boolean
		// 7. aquatic Boolean
		// 8. predator Boolean
		// 9. toothed Boolean
		// 10. backbone Boolean
		// 11. breathes Boolean
		// 12. venomous Boolean
		// 13. fins Boolean
		// 14. legs Numeric (set of values: {0,2,4,5,6,8})
		// 15. tail Boolean
		// 16. domestic Boolean
		// 17. catsize Boolean
		// 18. type Numeric (integer values in range [1,7])

		System.out.println("Star Table");
		for (Map.Entry<Integer, List<String>> i : red.starTable.entrySet()) {
			System.out.print(i.getKey() + " : ");
			List<String> t = i.getValue();
			for (int j = 0; j < t.size(); j++) {
				System.out.print(t.get(j) + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Compressed Base Table");
		for (Map.Entry<List<String>, Integer> i : red.reducedStarTable.entrySet()) {
			List<String> t = i.getKey();
			for (int j = 0; j < t.size(); j++) {
				System.out.print(t.get(j) + " ");
			}
			System.out.println(":" + i.getValue());
		}
	}
}