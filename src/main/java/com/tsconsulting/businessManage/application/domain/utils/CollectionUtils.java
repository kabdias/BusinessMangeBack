package com.tsconsulting.businessManage.application.domain.utils;

import java.util.Collection;
public class CollectionUtils {
    public static <T> boolean isNullOrEmpty(Collection<T> collectionObj){
        return collectionObj == null || collectionObj.isEmpty();
    }
}
