package org.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineString extends MultiPoint {

	public LineString() {
	}

	public LineString(LngLatAlt... points) {
		super(points);
	}

	@Override
	public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
		return geoJsonObjectVisitor.visit(this);
	}

	@Override
	public String toString() {
		return "LineString{} " + super.toString();
	}
}
