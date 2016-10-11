package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawable extends ClassDescDrawableContainerBased<LevelListDrawable> // ClassDescDrawableContainerBASE<LevelListDrawable>
{
    public ClassDescLevelListDrawable(ClassDescDrawableMgr classMgr,ClassDescDrawable<? super LevelListDrawable> parentClass)
    {
        super(classMgr, "level-list", parentClass);
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        LevelListDrawable drawable = new LevelListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            LevelListDrawableItem item = (LevelListDrawableItem)itemList.get(i);

            Drawable drawableItem = item.getDrawable();

            Integer minObj = item.getMinLevel();
            Integer maxObj = item.getMaxLevel();
            int min = minObj != null ? minObj : 0; // Según el código fuente
            int max = maxObj != null ? maxObj : 0; // Según el código fuente

            drawable.addLevel(min,max,drawableItem);

            setCallback(drawableItem,drawable); // Se puede ver en el código fuente si se sigue hasta addChild(Drawable dr)
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public void setCallback(Drawable childDrawable, LevelListDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }

    @Override
    public Class<LevelListDrawable> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawable.class;
    }

    protected void init()
    {
        super.init();

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));


        // yo creo que sobra este atributo
        // addAttrDescAN(new AttrDescReflecMethodBoolean(this, "oneshot", "setOneShot", false));
    }


}
