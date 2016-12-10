package com.andro2d.framework.math;


public final class Vector2D {
	public float x, y;
	
	public Vector2D() {}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D setX(float x) {
		this.x = x;
		return this;
	}
	
	public Vector2D setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector2D negate() {
		if(x > 0) x *= -1;
		if(y > 0) y *= -1;
		return this;
	}
	
	public Vector2D add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2D add(Vector2D v2d) {
		this.x += v2d.x;
		this.y += v2d.y;
		return this;
	}
	
	public Vector2D sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2D mult(float f) {
		this.x *= f;
		this.y *= f;
		return this;
	}
	
	public Vector2D div(float f) {
		this.x /= f;
		this.y /= f;
		return this;
	}

	public float mag(){
		return Vector2D.mag(this);
	}
	
	public Vector2D normalize() {
		float mag = mag();
		if(mag != 0){
			div(mag);
		}
		return this;
	}
	
	public void limit(float scalar) {
		if(mag() > scalar){
			normalize();
			mult(scalar);
		}
	}

    public boolean equals(Vector2D other) {
        return (this.x == other.x && this.y == other.y);
    }

    public static float mag(Vector2D v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static Vector2D sub(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x-v2.x, v1.y-v2.y);
    }

    public static double distance(Vector2D a, Vector2D b) {
        Vector2D vsub = Vector2D.sub(a, b);
        return Vector2D.mag(vsub);
    }

	@Override
	public Vector2D clone(){
		return new Vector2D(x, y);
	}
}
