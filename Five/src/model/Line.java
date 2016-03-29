package model;

import java.util.ArrayList;
import java.util.List;

public class Line {
	private List<Cell> cells;
	private Direction direction;

	public Line(List<Cell> cells, Direction direction) {
		this.cells = cells;
		this.direction = direction;
	}
	
	public Line(Cell from, Direction direction) {
		this(calcCells(from, direction), direction);
	}
	
	private static List<Cell> calcCells(Cell from, Direction direction) {
		List<Cell> cells = new ArrayList<Cell>();
		for (Cell cell = from; cell != null; cell = cell
				.getNearbyCell(direction)) {
			cells.add(cell);
		}
		return cells;
	}
	
	public int size() {
		return cells.size();
	}

	public Direction getDirection() {
		return direction;
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public Cell getCell(int index) {
		return cells.get(index);
	}
	
	public Line getSubLine(int fromIndex, int toIndex) {
		List<Cell> list = cells.subList(fromIndex, toIndex + 1);
		Line subLine = new Line(list, direction);
		return subLine;
	}
	
	public boolean containsCell(Cell cell) {
		for (Cell p : cells) {
			if(p.equals(cell)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Cell getForwardCell() {
		Cell fromCell = cells.get(0);
		return fromCell.getNearbyCell(direction.getOpposite());
	}
	
	public Cell getBackwardCell() {
		Cell toCell = cells.get(cells.size() - 1);
		return toCell.getNearbyCell(direction);
	}
	
	public int indexOf(Cell cell) {
		return cells.indexOf(cell);
	}
	
	public String getStr(Stone currentFocus) {
		StringBuffer sb = new StringBuffer();
		
		Cell forwardCell = getForwardCell();
		if (forwardCell == null
				|| forwardCell.getStone() == currentFocus.getOpposite()) {
			sb.append("2");
		}
		
		for (Cell c : cells) {
			if (c.getStone() == currentFocus) {
				sb.append("1");
			} else if (c.getStone() == currentFocus.getOpposite()) {
				sb.append("2");
			} else {
				sb.append("0");
			}
		}
		
		Cell backwardCell = getForwardCell();
		if (backwardCell == null
				|| backwardCell.getStone() == currentFocus.getOpposite()) {
			sb.append("2");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(direction).append(":");
		for (Cell c : cells) {
			sb.append(c.getStone().str);
		}
		
		sb.append(cells.get(0).getPoint());
		sb.append("->");
		sb.append(cells.get(cells.size() - 1).getPoint());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (direction != other.direction)
			return false;
		return true;
	}
	
	
}
