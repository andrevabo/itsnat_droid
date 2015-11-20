package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemStroke;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemStroke extends ClassDescElementDrawableChildNormal<GradientDrawableItemGradient>
{
    public ClassDescGradientDrawableItemStroke(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:stroke");
    }

    @Override
    public Class<GradientDrawableItemStroke> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemStroke.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemStroke(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "width", 0f));
        addAttrDesc(new AttrDescReflecMethodColor(this, "color", 0));
        addAttrDesc(new AttrDescReflecMethodDimensionFloat(this, "dashGap", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionFloat(this, "dashWidth", 0f));
    }

}
