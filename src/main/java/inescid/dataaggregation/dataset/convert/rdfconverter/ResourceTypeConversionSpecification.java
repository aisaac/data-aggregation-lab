package inescid.dataaggregation.dataset.convert.rdfconverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class ResourceTypeConversionSpecification {
	Resource type;
	Map<Property, Property> propertiesMapping;
	Map<Property, Property[]> propertiesMerge;
	Map<Property, DerivedPropertyConversionSpecification> derivedProperties;
	Map<ImmutablePair<Property, Resource>, ResourceTypeConversionSpecification> propertiesMappingFromReferencedResources;
//	Set<Property> propertiesMappedToReferencedResources=new HashSet<>();
	List<Property> propertiesMappingToUri;
	
	public ResourceTypeConversionSpecification(Resource type) {
		super();
		this.type = type;
		propertiesMapping=new HashMap<>();
		propertiesMerge=new HashMap<>();
		propertiesMappingFromReferencedResources=new HashMap<>();
		propertiesMappingToUri=new ArrayList<>(1);
		derivedProperties=new HashMap<>(3);
	}
	
	
	public void putDerivedProperty(Property from, DerivedPropertyConversionSpecification spec) {
		derivedProperties.put(from, spec);
	}
	public void putPropertyMapping(Property from, Property to) {
		propertiesMapping.put(from, to);
	}
	public DerivedPropertyConversionSpecification getDerivedPropertyMapping(Property from) {
		return derivedProperties.get(from);
	}
	public Property getPropertyMapping(Property from) {
		return propertiesMapping.get(from);
	}
	public void putPropertyMerge(Property from, Property... fromProperties) {
		propertiesMerge.put(from, fromProperties);
	}
	public Property[] getPropertyMerge(Property from) {
		return propertiesMerge.get(from);
	}
	
	
	
	public void putPropertyMappingFromReferencedResource(Property property, Resource type, Property srcTypeProp, Property targetProp) {
		ResourceTypeConversionSpecification mapping = getCreatePropertyMappingFromReferencedResource(property, type);
		mapping.putPropertyMapping(srcTypeProp , targetProp);
		propertiesMapping.put(property, targetProp);
	}
	
	public ResourceTypeConversionSpecification getPropertyMappingFromReferencedResource(Property property, Resource type) {
		ImmutablePair<Property, Resource> key = new ImmutablePair<>(property, type);
		ResourceTypeConversionSpecification spec = propertiesMappingFromReferencedResources.get(key);
		return spec;
	}
	
	public ResourceTypeConversionSpecification getCreatePropertyMappingFromReferencedResource(Property property, Resource type) {
		ImmutablePair<Property, Resource> key = new ImmutablePair<>(property, type);
		ResourceTypeConversionSpecification spec = getPropertyMappingFromReferencedResource(property, type);
		if(spec==null) {
			spec=new ResourceTypeConversionSpecification(type);
			propertiesMappingFromReferencedResources.put(key, spec);
		}
		return spec;
	}
//	public boolean isPropertyMappedToReferencedResource(Property property) {
//		return propertiesMappedToReferencedResources.contains(property);
//	}

	public Resource getType() {
		return type;
	}


	public void addPropertyMappingToUri(Property propForUri) {
		propertiesMappingToUri.add(propForUri);
	}
	
	public List<Property> getPropertiesMappingToUri() {
		return propertiesMappingToUri;
	}


	public Map<Property, Property> getPropertiesMapping() {
		return propertiesMapping;
	}


	public List<ResourceTypeConversionSpecification> searchPropertyMappingFromReferencedResource(Property prop) {
		List<ResourceTypeConversionSpecification> hits=new ArrayList<>();
		for (ImmutablePair<Property, Resource> k: propertiesMappingFromReferencedResources.keySet()) {
			if(k.getKey().equals(prop))
				hits.add(propertiesMappingFromReferencedResources.get(k));
		}
		return hits;
	}
}
