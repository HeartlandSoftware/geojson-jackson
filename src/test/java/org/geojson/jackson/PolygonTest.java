package org.geojson.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PolygonTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void itShouldSerialize() throws Exception {
		Polygon polygon = new Polygon(MockData.EXTERNAL);
		assertEquals("{\"type\":\"Polygon\",\"coordinates\":"
				+ "[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]}",
				mapper.writeValueAsString(polygon));
	}

	@Test
	public void itShouldSerializeWithHole() throws Exception {
		Polygon polygon = new Polygon(MockData.EXTERNAL);
		polygon.addInteriorRing(MockData.INTERNAL);
		assertEquals("{\"type\":\"Polygon\",\"coordinates\":"
				+ "[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],"
				+ "[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.8],[100.2,0.2]]]}", mapper.writeValueAsString(polygon));
	}

	@Test(expected = RuntimeException.class)
	public void itShouldFailOnAddInteriorRingWithoutExteriorRing() throws Exception {
		Polygon polygon = new Polygon();
		polygon.addInteriorRing(MockData.EXTERNAL);
	}

	@Test
	public void itShouldDeserialize() throws Exception {
		Polygon polygon = mapper.readValue("{\"type\":\"Polygon\",\"coordinates\":"
				+ "[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],"
				+ "[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.8],[100.2,0.2]]]}", Polygon.class);
		assertListEquals(MockData.EXTERNAL, polygon.getExteriorRing());
		assertListEquals(MockData.INTERNAL, polygon.getInteriorRing(0));
		assertListEquals(MockData.INTERNAL, polygon.getInteriorRings().get(0));
	}
	
	@Test
	public void malformedTest() throws Exception {
	    Polygon polygon = mapper.readValue("{\"type\": \"Polygon\",\"coordinates\":" +
	            "\"MULTIPOLYGON(((-97.2359378539002 49.8565162489406,-97.2044866378176 49.9391234958738," +
	            "-97.0761807168735 49.9187449099505,-97.1079012403885 49.8361938952035,-97.2359378539002 49.8565162489406)))" +
	            "\"}", Polygon.class);
	    assertEquals(MockData.MALFORMED_POLYGON, polygon.getCoordinateString());
	}

	private void assertListEquals(List<LngLatAlt> expectedList, List<LngLatAlt> actualList) {
		for (int x = 0; x < actualList.size(); x++) {
			LngLatAlt expected = expectedList.get(x);
			LngLatAlt actual = actualList.get(x);
			PointTest.assertLngLatAlt(expected.getLongitude(), expected.getLatitude(), expected.getAltitude(), actual);
		}
	}
}
