package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescNinePatchDrawable extends ClassDescElementDrawableRoot<NinePatchDrawable>
{
    public ClassDescNinePatchDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"nine-patch");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/Drawable.java#Drawable.createFromXmlInner%28android.content.res.Resources%2Corg.xmlpull.v1.XmlPullParser%2Candroid.util.AttributeSet%29
        // http://stackoverflow.com/questions/5079868/create-a-ninepatch-ninepatchdrawable-in-runtime

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        Context ctx = xmlInflaterContext.getContext();

        DOMAttr attrSrc = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "src");
        if (attrSrc == null) throw new ItsNatDroidException("Missing src attribute in element " + rootElem.getTagName());

        // No necesita escalar pues por definición es "flexible"
        Bitmap bitmap = getBitmap(attrSrc.getResourceDesc(),xmlInflaterContext.getBitmapDensityReference(),ctx,classMgr.getXMLInflaterRegistry());

        NinePatchDrawable drawable = DrawableUtil.createNinePatchDrawable(bitmap,ctx.getResources());
        return new ElementDrawableRoot(drawable);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;
        return isSrcAttribute(namespaceURI, name); // Se usa al construir el drawable
    }

    private static boolean isSrcAttribute(String namespaceURI,String name)
    {
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && name.equals("src");
    }

    @Override
    public Class<NinePatchDrawable> getDrawableOrElementDrawableClass()
    {
        return NinePatchDrawable.class;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));


        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "dither", true));
    }


}
