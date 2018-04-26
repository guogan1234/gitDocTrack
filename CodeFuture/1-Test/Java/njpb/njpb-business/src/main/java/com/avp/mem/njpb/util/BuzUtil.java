package com.avp.mem.njpb.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris feng on 2016/12/29.
 */
public class BuzUtil {
    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> result = new ArrayList<E>();
        if (iterable != null) {
            for (E e : iterable) {
                result.add(e);
            }
        }
        return result;
    }
}
