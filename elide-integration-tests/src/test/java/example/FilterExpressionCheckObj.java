/*
 * Copyright 2016, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package example;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.core.filter.Operator;
import com.yahoo.elide.core.filter.Predicate;
import com.yahoo.elide.security.*;
import com.yahoo.elide.security.checks.prefab.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Model for filterExpressionCheckObj.
 */
@Entity
@Table(name = "filterExpressionCheckObj")
@Include(rootLevel = true)
@SharePermission(any = {Role.ALL.class})
@ReadPermission(any = {FilterExpressionCheckObj.CheckLE.class, Role.NONE.class})  //ReadPermission for object id <= 2
public class FilterExpressionCheckObj {
    private long id;
    private String name;

    private Collection<AnotherFilterExpressionCheckObj> listOfAnotherObjs = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //This field only display for id == User.id (which is 1 in IT)
    @ReadPermission(all = {CheckRestrictUser.class})
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "linkToParent")
    public Collection<AnotherFilterExpressionCheckObj> getListOfAnotherObjs() {
        return listOfAnotherObjs;
    }

    public void setListOfAnotherObjs(Collection<AnotherFilterExpressionCheckObj> listOfAnotherObjs) {
        this.listOfAnotherObjs = listOfAnotherObjs;
    }

    //Predicate that restrict resource's id to be the same as cuurent User's id.
    public static Predicate createUserPredicate(RequestScope requestScope, boolean setUserId, long setId) {
        List<Predicate.PathElement> pathList = new ArrayList<>();
        Predicate.PathElement path1 = new Predicate.PathElement(FilterExpressionCheckObj.class, "filterExpressionCheckObj", long.class, "id");
        pathList.add(path1);
        Operator op = Operator.IN;
        List<Object> value = new ArrayList<>();
        int userId = (int) requestScope.getUser().getOpaqueUser();
        if (setUserId) {
            value.add(setId);
        } else {
            value.add((long) userId);
        }
        return new Predicate(pathList, op, value);
    }

    public static class CheckRestrictUser extends FilterExpressionCheck {

        @Override
        public Predicate getFilterExpression(Class entityClass, RequestScope requestScope) {
            return createUserPredicate(requestScope, false, 1L);
        }

        public CheckRestrictUser() {

        }
    }

    public static class CheckLE extends FilterExpressionCheck {

        @Override
        public Predicate getFilterExpression(Class entityClass, RequestScope requestScope) {
            List<Predicate.PathElement> pathList = new ArrayList<>();
            Predicate.PathElement path1 = new Predicate.PathElement(FilterExpressionCheckObj.class, "filterExpressionCheckObj", long.class, "id");
            pathList.add(path1);
            Operator op = Operator.LE;
            List<Object> value = new ArrayList<>();
            value.add((long) 2);
            return new Predicate(pathList, op, value);
        }

        public CheckLE() {

        }
    }
}
