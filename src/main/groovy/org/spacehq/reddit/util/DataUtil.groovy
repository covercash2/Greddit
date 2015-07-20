package org.spacehq.reddit.util

/**
 * A class containing static utilities relating to data types and containers.
 */
class DataUtil {
    /**
     * A map of Boxed primitive class names to primitive TYPE classes.
     */
    static
    final Map PRIMITIVE_TYPES = [(Byte.class.getName()): Byte.TYPE, (Short.class.getName()): Short.TYPE, (Integer.class.getName()): Integer.TYPE, (Long.class.getName()): Long.TYPE, (Float.class.getName()): Float.TYPE, (Double.class.getName()): Double.TYPE, (Character.class.getName()): Character.TYPE, (Boolean.class.getName()): Boolean.TYPE, (Void.class.getName()): Void.TYPE]

    /**
     * A list of number classes.
     */
    static
    final List NUMBER_TYPES = [Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class]

    /**
     * Fixes types in the given map to match the given class's field types, setting default values for primitives (-1, '', or false), enum values from strings, lists from single values, maps from fields of values, and objects from map values.
     * @param clazz Class to get field types from.
     * @param map Map to fix types in.
     * @return The fixed map.
     */
    static final Map fixTypes(Class clazz, Map map) {
        Map m = [:]
        map?.each { k, v ->
            Class c = clazz.getDeclaredField(k).type
            if(PRIMITIVE_TYPES[c.name] != null) {
                c = PRIMITIVE_TYPES[c.name]
            }

            Class vc = v.getClass()
            if(PRIMITIVE_TYPES[vc.name] != null) {
                vc = PRIMITIVE_TYPES[vc.name]
            }

            if(v == null) {
                if(c.primitive) {
                    if(c == Boolean.TYPE) {
                        v = false
                    } else if(c == Character.TYPE) {
                        v = ''
                    } else {
                        v = -1
                    }
                }
            } else if(!c.isAssignableFrom(vc) && !(NUMBER_TYPES.contains(c) && NUMBER_TYPES.contains(vc))) {
                if(c.primitive) {
                    if(c == Boolean.TYPE) {
                        v = false
                    } else if(c == Character.TYPE) {
                        v = ''
                    } else {
                        v = -1
                    }
                } else if(Enum.class.isAssignableFrom(c) && v instanceof String) {
                    v = Enum.valueOf(c, v.toUpperCase())
                } else if(List.class.isAssignableFrom(c)) {
                    v = [v]
                } else if(Map.class.isAssignableFrom(c)) {
                    vc.declaredFields.findAll { !it.synthetic }.collectEntries {
                        [(it.name): v."${it.name}"]
                    }
                } else if(Map.class.isAssignableFrom(vc)) {
                    v = c.newInstance(v)
                }
            }

            m[k] = v
        }

        return m
    }
}
