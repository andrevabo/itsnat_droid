package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescClipDrawable extends ClassDescDrawableWrapper<ClipDrawable>
{
    // Para el atributo clipOrientation
    // No podemos usar OrientationUtil porque los valores numéricos SON DIFERENTES (1 y 2 en vez de 0 y 1), hay que joderse con la falta de homogeneidad
    private static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(2);
    static
    {
        nameValueMap.put("horizontal", ClipDrawable.HORIZONTAL /* 1 */);
        nameValueMap.put("vertical", ClipDrawable.VERTICAL /* 2 */);
    }

    public ClassDescClipDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"clip");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();

        xmlInflaterDrawable.processChildElements(rootElem, elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, xmlInflaterContext,childList);

        DOMAttr attrGravity = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "gravity");
        int gravity = attrGravity != null ? AttrDesc.parseMultipleName(attrGravity.getValue(), GravityUtil.nameValueMap) : Gravity.LEFT; // Valor concreto no puede ser un recurso

        DOMAttr attrClipOrientation = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "clipOrientation");
        int orientation = attrClipOrientation != null ? AttrDesc.<Integer>parseSingleName(attrClipOrientation.getValue(), nameValueMap) : ClipDrawable.HORIZONTAL; // Valor concreto no puede ser un recurso

        ClipDrawable drawable = new ClipDrawable(childDrawable,gravity,orientation);

        setCallback(childDrawable,drawable);

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public void setCallback(Drawable childDrawable, ClipDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }


    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("clipOrientation".equals(name) || "drawable".equals(name) || "gravity".equals(name));
        }
        return false;
    }


    @Override
    public Class<ClipDrawable> getDrawableOrElementDrawableClass()
    {
        return ClipDrawable.class;
    }

    protected void init()
    {
        super.init();

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));

    }
}
