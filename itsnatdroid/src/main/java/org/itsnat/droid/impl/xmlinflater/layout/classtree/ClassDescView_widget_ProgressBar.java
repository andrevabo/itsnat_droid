package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_indeterminate;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_indeterminateBehavior;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_progressDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInterpolator;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ProgressBar extends ClassDescViewBased
{
    public ClassDescView_widget_ProgressBar(ClassDescViewMgr classMgr,ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.widget.ProgressBar",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        // android:animationResolution es la traca, se procesa por ejemplo en 4.0.3 pero NI RASTRO a partir de 4.1.1 aunque sigue estando en la documentación
        addAttrDescAN(new AttrDescView_widget_ProgressBar_indeterminate(this, "indeterminate", true));
        addAttrDescAN(new AttrDescView_widget_ProgressBar_indeterminateBehavior(this));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "indeterminateDrawable", "@null")); // Android tiene un drawable por defecto
        addAttrDescAN(new AttrDescReflecFieldSetInt(this, "indeterminateDuration", "mDuration", 4000));
        addAttrDescAN(new AttrDescReflecFieldSetBoolean(this, "indeterminateOnly", "mOnlyIndeterminate", false));
        // android:indeterminateTint es level 21
        // android:indeterminateTintMode es level 21
        addAttrDescAN(new AttrDescReflecMethodInterpolator(this,"interpolator","@android:anim/linear_interpolator")); // Yo creo que es el que usa por defecto Android en este caso
        addAttrDescAN(new AttrDescReflecMethodInt(this, "max", 100));
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "maxHeight", "mMaxHeight", 48));
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "maxWidth", "mMaxWidth", 48));
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "minHeight", "mMinHeight", 24));
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "minWidth", "mMinWidth", 24));
        // android:mirrorForRtl es de un Level superior a level 16 (creo que level 18)
        addAttrDescAN(new AttrDescReflecMethodInt(this, "progress", 0));
        // android:progressBackgroundTint es level 21
        // android:progressBackgroundTintMode es level 21
        addAttrDescAN(new AttrDescView_widget_ProgressBar_progressDrawable(this));
        // android:progressTint es level 21
        // android:progressTintMode es level 21
        addAttrDescAN(new AttrDescReflecMethodInt(this, "secondaryProgress", 0));
        // android:secondaryProgressTint es level 21
        // android:secondaryProgressTintMode es level 21

    }

}

