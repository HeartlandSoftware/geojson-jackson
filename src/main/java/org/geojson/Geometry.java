package org.geojson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Geometry<T> extends GeoJsonObject {
    
    @JsonIgnore
    private String coordinateString;

	protected List<T> coordinates = new ArrayList<T>();

	public Geometry() {
	}

	public Geometry(T... elements) {
		for (T coordinate : elements) {
			coordinates.add(coordinate);
		}
	}

	public Geometry<T> add(T elements) {
		coordinates.add(elements);
		return this;
	}

	public List<T> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Object coordinates) {
	    if (coordinates instanceof List) {
	        List list = (List)coordinates;
	        if (list.size() != 0) {
	            //make some poor assumptions and parse nested lists as LngLatAlt lists
	            if (list.get(0) instanceof List) {
	                for (int i = 0; i < list.size(); i++) {
	                    List nested = (List)list.get(i);
	                    if (nested.get(0) instanceof List) {
	                        List<LngLatAlt> polygon = new ArrayList<>();
	                        for (int j = 0; j < nested.size(); j++) {
	                            List doubleNested = (List)nested.get(j);
	                            if (doubleNested.size() == 2 || doubleNested.size() == 3) {
	                                LngLatAlt lla = new LngLatAlt();
	                                lla.setLongitude((Double)doubleNested.get(0));
	                                lla.setLatitude((Double)doubleNested.get(1));
	                                if (nested.size() == 3)
	                                    lla.setAltitude((Double)doubleNested.get(2));
	                                polygon.add(lla);
	                            }
	                        }
	                        this.coordinates.add((T)polygon);
	                    }
	                    else if (nested.size() == 2 || nested.size() == 3) {
    	                    LngLatAlt lla = new LngLatAlt();
    	                    lla.setLongitude((Double)nested.get(0));
    	                    lla.setLatitude((Double)nested.get(1));
    	                    if (nested.size() == 3)
    	                        lla.setAltitude((Double)nested.get(2));
    	                    this.coordinates.add((T)lla);
	                    }
	                }
	            }
	            else {
	                setCoordinatesList((List<T>)coordinates);
	            }
	        }
	    }
	    else if (coordinates instanceof String)
	        setCoordinatesString((String)coordinates);
	    else
	        throw new IllegalArgumentException("Invalid coordinate type " + coordinates.getClass());
	}

	@JsonIgnore
	public void setCoordinatesList(List<T> coordinates) {
        System.out.println(coordinates.size());
		this.coordinates = coordinates;
	}
	
	@JsonIgnore
	public void setCoordinatesString(String coordinates) {
	    this.coordinateString = coordinates;
	}
    
    @JsonIgnore
    public String getCoordinateString() {
        return coordinateString;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Geometry)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		Geometry geometry = (Geometry)o;
		return !(coordinates != null ? !coordinates.equals(geometry.coordinates) : geometry.coordinates != null);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Geometry{" + "coordinates=" + coordinates + "} " + super.toString();
	}
}
