package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecFieldSetDimensionIntFloor extends _DELETE_AttrDescViewReflecFieldSetDimensionInt
{
    public _DELETE_AttrDescViewReflecFieldSetDimensionIntFloor(ClassDescViewBased parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName,defaultValue);
    }

    @Override
    public int getDimensionInt(DOMAttr attr, Context ctx)
    {
        return getDimensionIntFloor(attr.getValue(), ctx);
    }
}