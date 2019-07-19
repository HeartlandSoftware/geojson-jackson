package org.geojson.jackson;

import java.util.Arrays;
import java.util.List;

import org.geojson.LngLatAlt;

public class MockData {

	public static final List<LngLatAlt> EXTERNAL = Arrays.asList(new LngLatAlt(100, 0), new LngLatAlt(101, 0),
			new LngLatAlt(101, 1), new LngLatAlt(100, 1), new LngLatAlt(100, 0));
	public static final List<LngLatAlt> INTERNAL = Arrays.asList(new LngLatAlt(100.2, 0.2), new LngLatAlt(100.8, 0.2),
			new LngLatAlt(100.8, 0.8), new LngLatAlt(100.2, 0.8), new LngLatAlt(100.2, 0.2));
	public static final String MALFORMED_POLYGON = "MULTIPOLYGON(((-97.2359378539002 49.8565162489406,-97.2044866378176 49.9391234958738,-97.0761807168735 49.9187449099505,-97.1079012403885 49.8361938952035,-97.2359378539002 49.8565162489406)))";
}
