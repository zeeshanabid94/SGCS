
public class Vector2D {
	double _x;
	double _y;
	
	public Vector2D() {
		_x = 0;
		_y = 0;
	}
	
	public Vector2D(double x, double y) {
		_x = x;
		_y = y;
	}
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}
	
	public void add(Vector2D anotherVec) {
		_x = _x + anotherVec.getX();
		_y = _y + anotherVec.getY();
	}
	
	public void divide(double scalar) {
		if (scalar == 0) {
			return;
		}
		_x /= scalar;
		_y /= scalar;
	}
	
	public double distance(Vector2D anotherVec) {
		return Math.sqrt(Math.pow((anotherVec.getX() - _x), 2) + Math.pow((anotherVec.getY() - _y), 2));
	}
	
	public double length() {
		return Math.sqrt(Math.pow(_x, 2) + Math.pow(_y, 2));
	}
}
