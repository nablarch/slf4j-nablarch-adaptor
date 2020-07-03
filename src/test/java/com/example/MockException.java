package com.example;

public class MockException extends Exception {

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof MockException;
	}
}
