package com.kator.weightguard.ui.utils;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by prats on 5/11/18.
 */
public class FindUtils {
    public static <T> T findByProperty(Collection<T> list, Predicate<T> filter) {
        return list.stream().filter(filter).findFirst().orElse(null);
    }
}
