package domain.helper;

import com.uwetrottmann.tmdb2.entities.DiscoverFilter;

import java.lang.reflect.Field;

public class DiscoverFilterBuilder {
    /*
    * This is an hacky trick to be able to choose the value of private arg separator
    * It's a temporary solution see https://github.com/UweTrottmann/tmdb-java/pull/85
    */
    public static DiscoverFilter build(DiscoverFilter.Separator separator, Integer... items) {
        DiscoverFilter discoverFilter = new DiscoverFilter(items);
        try {
            Field field = discoverFilter.getClass().getDeclaredField("separator");
            field.setAccessible(true);
            field.set(discoverFilter, separator);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return discoverFilter;
    }
}
