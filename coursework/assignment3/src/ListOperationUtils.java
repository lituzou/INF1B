import java.util.*;

/**
 * All the static methods for manipulating a list
 */
public class ListOperationUtils {
    /**
     * Group the list of entities by given singleton attributes, the groups are sorted by comparing attributes
     *
     * @param entityAttributeMap Map from entity to its singleton attribute
     * @param <Attribute>        Type of comparable attribute
     * @param <Entity>           Type of entity
     * @return Sorted groups of entities
     * @throws NullPointerException if given map is null
     */
    public static <Attribute extends Comparable<Attribute>, Entity> TreeMap<Attribute, List<Entity>> groupByAttribute(AbstractMap<Entity, Attribute> entityAttributeMap) {
        Objects.requireNonNull(entityAttributeMap, "Given entity map must not be null");
        TreeMap<Attribute, List<Entity>> resultGroups = new TreeMap<>();
        for (Entity entity : entityAttributeMap.keySet()) {
            Attribute attribute = entityAttributeMap.get(entity);
            if (!resultGroups.containsKey(attribute)) {
                resultGroups.put(attribute, new ArrayList<>());
            }
            resultGroups.get(attribute).add(entity);
        }
        return resultGroups;
    }

    /**
     * Group the list of entities by given attribute lists, the groups are sorted by comparing attributes.
     * Note that the entity with multiple attributes will appear in multiple groups.
     *
     * @param entityAttributeListMap Map from entity to its attribute list
     * @param <Attribute>            Type of comparable attribute
     * @param <Entity>               Type of entity
     * @return Sorted group of entities
     * @throws NullPointerException if given map is null
     */
    public static <Attribute extends Comparable<Attribute>, Entity> TreeMap<Attribute, List<Entity>> groupByManyAttributes(AbstractMap<Entity, List<Attribute>> entityAttributeListMap) {
        Objects.requireNonNull(entityAttributeListMap, "Given entity map must not be null");
        TreeMap<Attribute, List<Entity>> resultGroups = new TreeMap<>();
        for (Entity entity : entityAttributeListMap.keySet()) {
            List<Attribute> attributeList = entityAttributeListMap.get(entity);
            for (Attribute attribute : attributeList) {
                if (!resultGroups.containsKey(attribute)) {
                    resultGroups.put(attribute, new ArrayList<>());
                }
                resultGroups.get(attribute).add(entity);
            }
        }
        return resultGroups;
    }

    /**
     * Remove the first occurrence in the list which has the same attribute to a given attribute.
     *
     * @param entities           Original entity list
     * @param entityAttributeMap Map from entity to its attribute
     * @param attribute          attribute that entity matches to
     * @param <Entity>           Type of entity
     * @param <Attribute>        Type of attribute
     * @return number of entities being removed
     * @throws NullPointerException if given entity list, entity-attribute map or attribute is null
     */
    public static <Entity, Attribute> int removeOneByAttribute(List<Entity> entities, AbstractMap<Entity, Attribute> entityAttributeMap, Attribute attribute) {
        Objects.requireNonNull(entities, "Given entity list must not be null");
        Objects.requireNonNull(entityAttributeMap, "Given entity map must not be null");
        Objects.requireNonNull(attribute, "Given attribute must not be null");

        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            if (attribute.equals(entityAttributeMap.get(entity))) {
                entityIterator.remove();
                return 1;
            }
        }
        return 0;
    }

    /**
     * Remove all the occurrences in the list which have the same attribute to a given attribute.
     *
     * @param entities           Original entity list
     * @param entityAttributeMap Map from entity to its attribute
     * @param attribute          Attribute that entity matches to
     * @param <Entity>           Type of entity
     * @param <Attribute>        Type of attribute
     * @return number of entities being removed
     * @throws NullPointerException if given entity list, entity-attribute map or attribute is null
     */
    public static <Entity, Attribute> int removeManyByAttribute(List<Entity> entities, AbstractMap<Entity, Attribute> entityAttributeMap, Attribute attribute) {
        Objects.requireNonNull(entities, "Given entity list must not be null");
        Objects.requireNonNull(entityAttributeMap, "Given entity map must not be null");
        Objects.requireNonNull(attribute, "Given attribute must not be null");

        int removeCount = 0;
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            if (attribute.equals(entityAttributeMap.get(entity))) {
                entityIterator.remove();
                removeCount++;
            }
        }
        return removeCount;
    }
}
